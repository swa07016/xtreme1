package ai.basic.x1.usecase;

import ai.basic.x1.adapter.port.dao.*;
import ai.basic.x1.adapter.port.dao.mybatis.model.*;
import ai.basic.x1.adapter.port.dao.redis.ModelSerialNoCountDAO;
import ai.basic.x1.adapter.port.dao.redis.ModelSerialNoIncrDAO;
import ai.basic.x1.entity.*;
import ai.basic.x1.entity.enums.DatasetTypeEnum;
import ai.basic.x1.entity.enums.ModelCodeEnum;
import ai.basic.x1.entity.enums.ModelDatasetTypeEnum;
import ai.basic.x1.entity.enums.RunStatusEnum;
import ai.basic.x1.usecase.exception.UsecaseCode;
import ai.basic.x1.usecase.exception.UsecaseException;
import ai.basic.x1.util.Constants;
import ai.basic.x1.util.DefaultConverter;
import ai.basic.x1.util.Page;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.ttl.TtlRunnable;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ai.basic.x1.entity.enums.ModelDatasetTypeEnum.LIDAR;

/**
 * @author chenchao
 * @date 2022/8/26
 */
@Slf4j
public class ModelUseCase {


    @Autowired
    private ModelDAO modelDAO;

    @Autowired
    private ModelClassDAO modelClassDAO;

    @Autowired
    private DatasetDAO datasetDAO;

    @Autowired
    private ModelRunRecordDAO modelRunRecordDAO;
    @Autowired
    private ModelDatasetResultDAO modelDatasetResultDAO;

    @Autowired
    private ModelSerialNoCountDAO modelSerialNoCountDAO;
    @Autowired
    private ModelSerialNoIncrDAO modelSerialNoIncrDAO;

    @Autowired
    private DataInfoUseCase dataInfoUseCase;
    @Autowired
    private RedisTemplate<String, Object> streamRedisTemplate;

    private static final ExecutorService executorService = ThreadUtil.newExecutor(5);


    public void add(ModelBO modelBO) {
        modelDAO.save(DefaultConverter.convert(modelBO, Model.class));
    }

    public void update(ModelBO modelBO) {
        modelDAO.updateById(DefaultConverter.convert(modelBO, Model.class));
    }

    public void configurationModelClass(Long modelId, List<ModelClassBO> modelClassBOList) {
        var modelClassLambdaQueryWrapper = Wrappers.lambdaQuery(ModelClass.class);
        modelClassLambdaQueryWrapper.eq(ModelClass::getModelId, modelId);
        modelClassDAO.remove(modelClassLambdaQueryWrapper);
        modelClassBOList.forEach(modelClassBO -> modelClassBO.setModelId(modelId));
        modelClassDAO.saveBatch(DefaultConverter.convert(modelClassBOList, ModelClass.class));
    }


    public Page<ModelBO> findByPage(Integer pageNo, Integer pageSize, ModelDatasetTypeEnum datasetType) {
        LambdaQueryWrapper<Model> modelLambdaQueryWrapper = Wrappers.lambdaQuery();
        modelLambdaQueryWrapper.orderByAsc(Model::getName);
        if (ObjectUtil.isNotNull(datasetType)) {
            modelLambdaQueryWrapper.eq(Model::getDatasetType, datasetType);
        }
        var modelPage = modelDAO.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNo, pageSize), modelLambdaQueryWrapper);
        return DefaultConverter.convert(modelPage, ModelBO.class);
    }

    public ModelBO findByModelCode(ModelCodeEnum modelCode) {
        var model = modelDAO.getOne(new LambdaQueryWrapper<Model>().eq(Model::getModelCode,
                modelCode));
        return DefaultConverter.convert(model, ModelBO.class);
    }

    public ModelBO findById(Long id) {
        var model = modelDAO.getById(id);
        var lambdaQueryWrapper = Wrappers.lambdaQuery(ModelClass.class);
        lambdaQueryWrapper.eq(ModelClass::getModelId, id);
        var modelClassList = modelClassDAO.list(lambdaQueryWrapper);
        if (ObjectUtil.isNotNull(model)) {
            var modelBO = DefaultConverter.convert(model, ModelBO.class);
            modelBO.setClasses(DefaultConverter.convert(modelClassList, ModelClassBO.class));
            return modelBO;
        }
        return null;
    }

    public void delete(Long id) {
        modelDAO.removeById(id);
        var modelClassLambdaQueryWrapper = Wrappers.lambdaQuery(ModelClass.class);
        modelClassLambdaQueryWrapper.eq(ModelClass::getModelId, id);
        modelClassDAO.remove(modelClassLambdaQueryWrapper);
        var modelRunRecordLambdaQueryWrapper = Wrappers.lambdaQuery(ModelRunRecord.class);
        modelRunRecordLambdaQueryWrapper.eq(ModelRunRecord::getModelId, id);
        modelRunRecordDAO.remove(modelRunRecordLambdaQueryWrapper);
        executorService.execute(() -> {
            var modelDatasetResultLambdaQueryWrapper = Wrappers.lambdaQuery(ModelDatasetResult.class);
            modelDatasetResultLambdaQueryWrapper.eq(ModelDatasetResult::getModelId, id);
            modelDatasetResultDAO.remove(modelDatasetResultLambdaQueryWrapper);
        });
    }

    @Transactional(rollbackFor = Throwable.class)
    public void modelRun(ModelRunBO modelRunBO) {
        var dataset = datasetDAO.getById(modelRunBO.getDatasetId());
        if (ObjectUtil.isNull(dataset)) {
            throw new UsecaseException(UsecaseCode.DATASET__NOT_EXIST);
        }
        var modelRunFilterDataBO = modelRunBO.getDataFilterParam();
        var totalDataNum = dataInfoUseCase.findModelRunDataCount(modelRunFilterDataBO, modelRunBO.getDatasetId(), modelRunBO.getModelId());
        totalDataNum = (long) Math.ceil(BigDecimal.valueOf(totalDataNum).multiply(BigDecimal.valueOf(modelRunFilterDataBO.getDataCountRatio())).divide(BigDecimal.valueOf(100)).doubleValue());
        if (totalDataNum == 0) {
            throw new UsecaseException(UsecaseCode.DATASET_DATA_SCENARIO_NOT_FOUND);
        }
        ModelBO modelBO = getModelById(modelRunBO.getModelId());
        if (ObjectUtil.isNull(modelBO)) {
            throw new UsecaseException(UsecaseCode.DATASET__MODEL_NOT_EXIST);
        }
        var dataIds = dataInfoUseCase.findModelRunDataIds(modelRunBO.getDataFilterParam(),
                modelRunBO.getDatasetId(), modelRunBO.getModelId(), totalDataNum);
        checkDatasetType(dataset.getType(), modelBO.getDatasetType());
        ModelRunRecord modelRunRecord = ModelRunRecord.builder()
                .modelId(modelRunBO.getModelId())
                .modelVersion(modelBO.getVersion())
                .modelSerialNo(IdUtil.getSnowflakeNextId())
                .dataFilterParam(DefaultConverter.convert(modelRunBO.getDataFilterParam(), ModelRunFilterData.class))
                .runNo(DateUtil.format(OffsetDateTime.now().toLocalDateTime(), DatePattern.PURE_DATETIME_PATTERN))
                .datasetId(dataset.getId())
                .status(RunStatusEnum.STARTED)
                .resultFilterParam(modelRunBO.getResultFilterParam()).build();
        modelRunRecordDAO.save(modelRunRecord);
        this.sendModelMessageAsync(modelRunRecord, modelBO, totalDataNum, dataIds);
    }

    private void checkDatasetType(DatasetTypeEnum datasetType, ModelDatasetTypeEnum modelDatasetType) {
        if (LIDAR.equals(modelDatasetType)) {
            if (ObjectUtil.notEqual(DatasetTypeEnum.LIDAR_BASIC, datasetType) && ObjectUtil.notEqual(DatasetTypeEnum.LIDAR_FUSION, datasetType)) {
                throw new UsecaseException(UsecaseCode.UNKNOWN, "model's dataset type is not match dataset type");
            }
        } else if (!modelDatasetType.name().equals(datasetType.name())) {
            throw new UsecaseException(UsecaseCode.UNKNOWN, "model's dataset type is not match dataset type");
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public void reRun(ModelRunRecordBO modelRunRecordBO, LoggedUserBO loggedUserBO) {
        ModelRunRecord modelRunRecord = modelRunRecordDAO.getById(modelRunRecordBO.getId());
        if (ObjectUtil.isNull(modelRunRecord)) {
            throw new UsecaseException(UsecaseCode.DATASET__MODEL_RUN_RECORD_NOT_EXIST);
        }
        var queryWrapper = Wrappers.lambdaQuery(ModelDatasetResult.class);
        queryWrapper.eq(ModelDatasetResult::getRunRecordId, modelRunRecordBO.getId());
        var totalDataNum = modelDatasetResultDAO.count(queryWrapper);
        if (totalDataNum == 0) {
            throw new UsecaseException(UsecaseCode.DATASET_DATA_SCENARIO_NOT_FOUND);
        }
        ModelBO modelBO = getModelById(modelRunRecord.getModelId());
        if (ObjectUtil.isNull(modelBO)) {
            throw new UsecaseException(UsecaseCode.DATASET__MODEL_NOT_EXIST);
        }
        boolean updateResult = modelRunRecordDAO.update(ModelRunRecord.builder().build(), Wrappers.lambdaUpdate(ModelRunRecord.class)
                .set(ModelRunRecord::getStatus, RunStatusEnum.STARTED)
                .eq(ModelRunRecord::getId, modelRunRecordBO.getId())
                .in(ModelRunRecord::getStatus, RunStatusEnum.FAILURE, RunStatusEnum.SUCCESS_WITH_ERROR)
        );
        if (updateResult) {
            queryWrapper.select(ModelDatasetResult::getDataId);
            var modelDatasetResultList = modelDatasetResultDAO.list(queryWrapper);
            modelDatasetResultDAO.remove(new LambdaUpdateWrapper<ModelDatasetResult>()
                    .eq(ModelDatasetResult::getModelSerialNo, modelRunRecord.getModelSerialNo())
            );
            sendModelMessageAsync(modelRunRecord, modelBO, totalDataNum, modelDatasetResultList.stream().map(ModelDatasetResult::getDataId).collect(Collectors.toList()));
        } else {
            throw new UsecaseException(UsecaseCode.DATASET__MODEL_RERUN_ERROR);
        }
    }

    public ModelBO getModelById(Long modelId) {
        LambdaQueryWrapper<Model> modelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        modelLambdaQueryWrapper.eq(Model::getId, modelId);
        Model model = modelDAO.getOne(modelLambdaQueryWrapper, Boolean.FALSE);
        return DefaultConverter.convert(model, ModelBO.class);
    }

    public Map<String, ModelClass> getModelClassMapByModelId(Long modelId) {
        var modelClassLambdaQueryWrapper = Wrappers.lambdaQuery(ModelClass.class);
        modelClassLambdaQueryWrapper.eq(ModelClass::getModelId, modelId);
        var modelClassList = modelClassDAO.list(modelClassLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(modelClassList)) {
            return modelClassList.stream().collect(Collectors.toMap(ModelClass::getCode, v -> v));
        }
        return Maps.newHashMap();
    }

    public RecordId sendDataModelMessageToMQ(ModelMessageBO modelMessageBO) {
        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .in(Constants.DATA_MODEL_RUN_STREAM_KEY)
                .ofObject(JSONUtil.toJsonStr(modelMessageBO))
                .withId(RecordId.autoGenerate());
        return streamRedisTemplate.opsForStream().add(record);
    }

    public RecordId sendDatasetModelMessageToMQ(ModelMessageBO modelMessageBO) {
        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .in(Constants.DATASET_MODEL_RUN_STREAM_KEY)
                .ofObject(JSONUtil.toJsonStr(modelMessageBO))
                .withId(RecordId.autoGenerate());
        return streamRedisTemplate.opsForStream().add(record);
    }

    private void sendModelMessageAsync(ModelRunRecord modelRunRecord, ModelBO modelBO, long totalDataNum, List<Long> dataIds) {
        Assert.notNull(modelRunRecord, "modelRunRecord is null");
        log.info("start send model message. datasetId: {}, runRecodeId: {}",
                modelRunRecord.getDatasetId(), modelRunRecord.getId());
        try {
            executorService.execute(Objects.requireNonNull(TtlRunnable.get(() -> {
                try {
                    // Cumulative number of messages sent.
                    AtomicInteger sendSuccessNum = new AtomicInteger();
                    // Accumulated number of inserted records.
                    AtomicInteger insertRecordNum = new AtomicInteger(0);
                    var modelSerialNo = modelRunRecord.getModelSerialNo();
                    log.info("executor start. datasetId: {}, runRecodeId: {}",
                            modelRunRecord.getDatasetId(), modelRunRecord.getId());
                    modelSerialNoIncrDAO.removeModelSerialNo(modelRunRecord.getModelSerialNo());
                    modelSerialNoCountDAO.setCount(modelRunRecord.getModelSerialNo(),
                            (int) totalDataNum);

                    var dataIdList = CollUtil.split(dataIds, 1000);
                    dataIdList.forEach(dataIdSubList -> {
                        if (isNotExistModelRunRecord(modelRunRecord)) {
                            log.error("model {} runId {} is delete.", modelBO.getModelCode(), modelRunRecord.getRunNo());
                            return;
                        }
                        var dataInfoBOList = dataInfoUseCase.listByIds(dataIdSubList, true);
                        insertRecordNum.addAndGet(batchSaveModelDatasetMessage(dataInfoBOList,
                                modelRunRecord));
                        log.info("model {} runId {} cumulative insert num {}",
                                modelBO.getModelCode(),
                                modelRunRecord.getRunNo(), insertRecordNum);
                        sendSuccessNum.addAndGet(sendModelDatasetMessage(
                                convertMessageList(dataInfoBOList, modelRunRecord, modelBO)
                        ));
                        modelSerialNoCountDAO.setCount(modelSerialNo, sendSuccessNum.get());
                        log.info("model {} runId {} cumulative send num {}", modelBO.getModelCode(),
                                modelRunRecord.getRunNo(), sendSuccessNum);
                        log.info("model {} runId {} finish.", modelBO.getModelCode(),
                                modelRunRecord.getRunNo());
                    });
                } catch (Exception e) {
                    log.info("model {} runId {} fail. Exception:{}",
                            modelBO.getModelCode(),
                            modelRunRecord.getRunNo(), e);
                }
            })));
        } catch (RejectedExecutionException ex) {
            throw new UsecaseException(UsecaseCode.UNKNOWN,
                    "The system is busy, please try again later");
        }
    }

    private boolean isNotExistModelRunRecord(ModelRunRecord modelRunRecord) {
        var query = new LambdaQueryWrapper<ModelRunRecord>();
        query.eq(ModelRunRecord::getId, modelRunRecord.getId());
        return !modelRunRecordDAO.getBaseMapper().exists(query);
    }

    private int batchSaveModelDatasetMessage(List<DataInfoBO> dataInfoList, ModelRunRecord modelRunRecord) {
        var records = convertModelDatasetResultList(dataInfoList, modelRunRecord);
        if (CollUtil.isNotEmpty(records)) {
            modelDatasetResultDAO.insertBatch(records);
        }
        return CollUtil.isEmpty(records) ? 0 : records.size();
    }

    private List<ModelDatasetResult> convertModelDatasetResultList(List<DataInfoBO> dataInfoList, ModelRunRecord modelRunRecord) {
        var modelDatasetResults = new ArrayList<ModelDatasetResult>(dataInfoList.size());
        dataInfoList.forEach(data -> {
            var modelDatasetResult = ModelDatasetResult.builder()
                    .datasetId(modelRunRecord.getDatasetId())
                    .modelId(modelRunRecord.getModelId())
                    .modelVersion(modelRunRecord.getModelVersion())
                    .modelSerialNo(modelRunRecord.getModelSerialNo())
                    .runNo(modelRunRecord.getRunNo())
                    .runRecordId(modelRunRecord.getId())
                    .dataId(data.getId())
                    .isSuccess(Boolean.TRUE)
                    .resultFilterParam(modelRunRecord.getResultFilterParam())
                    .build();
            modelDatasetResults.add(modelDatasetResult);
        });
        return modelDatasetResults;
    }

    private List<ModelMessageBO> convertMessageList(List<DataInfoBO> dataInfoList,
                                                    ModelRunRecord modelRunRecord,
                                                    ModelBO modelBO) {
        if (CollUtil.isEmpty(dataInfoList)) {
            return new ArrayList<>(1);
        }
        var messages = new ArrayList<ModelMessageBO>(dataInfoList.size());
        dataInfoList.forEach(data -> {
            var message = ModelMessageBO.builder()
                    .datasetId(modelRunRecord.getDatasetId())
                    .modelId(modelRunRecord.getModelId())
                    .modelVersion(modelRunRecord.getModelVersion())
                    .modelSerialNo(modelRunRecord.getModelSerialNo())
                    .modelCode(modelBO.getModelCode())
                    .createdBy(modelRunRecord.getCreatedBy())
                    .resultFilterParam(JSONUtil.parseObj(modelRunRecord.getResultFilterParam()))
                    .dataId(data.getId())
                    .dataInfo(DefaultConverter.convert(data, DataInfoBO.class))
                    .url(modelBO.getUrl())
                    .build();
            messages.add(message);
        });
        return messages;
    }

    private int sendModelDatasetMessage(List<ModelMessageBO> modelMessageList) {
        if (CollUtil.isEmpty(modelMessageList)) {
            return 0;
        }
        AtomicInteger sendNum = new AtomicInteger(0);
        modelMessageList.forEach(modelMessageBO -> {
            this.sendDatasetModelMessageToMQ(modelMessageBO);
            sendNum.getAndIncrement();
        });
        return sendNum.get();
    }
}
