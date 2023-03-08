package ai.basic.x1.usecase;

import ai.basic.x1.adapter.port.dao.ModelDatasetResultDAO;
import ai.basic.x1.adapter.port.dao.ModelRunRecordDAO;
import ai.basic.x1.adapter.port.dao.mybatis.extension.ExtendLambdaQueryWrapper;
import ai.basic.x1.adapter.port.dao.mybatis.model.ModelDatasetResult;
import ai.basic.x1.adapter.port.dao.mybatis.model.ModelRunRecord;
import ai.basic.x1.adapter.port.dao.redis.ModelSerialNoCountDAO;
import ai.basic.x1.adapter.port.dao.redis.ModelSerialNoIncrDAO;
import ai.basic.x1.entity.DatasetBO;
import ai.basic.x1.entity.ModelRunRecordBO;
import ai.basic.x1.entity.enums.RunRecordQueryBO;
import ai.basic.x1.entity.enums.SortEnum;
import ai.basic.x1.util.DefaultConverter;
import ai.basic.x1.util.Page;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;


/**
 * @author fyb
 * @version 1.0
 */
@Slf4j
public class ModelRunRecordUseCase {

    @Autowired
    private ModelRunRecordDAO modelRunRecordDAO;

    @Autowired
    private ModelDatasetResultDAO modelDatasetResultDAO;

    @Autowired
    private ModelSerialNoCountDAO modelSerialNoCountDAO;

    @Autowired
    private ModelSerialNoIncrDAO modelSerialNoIncrDAO;

    public List<ModelRunRecordBO> findAllModelRunRecord(ModelRunRecordBO bo) {
        LambdaQueryWrapper<ModelRunRecord> modelRunRecordLambdaQueryWrapper = Wrappers.lambdaQuery();
        modelRunRecordLambdaQueryWrapper.eq(ObjectUtil.isNotNull(bo.getModelId()), ModelRunRecord::getModelId, bo.getModelId());
        modelRunRecordLambdaQueryWrapper.eq(ObjectUtil.isNotNull(bo.getDatasetId()), ModelRunRecord::getDatasetId, bo.getDatasetId());
        var modelRunRecordList = modelRunRecordDAO.getBaseMapper().selectList(modelRunRecordLambdaQueryWrapper);
        var result = DefaultConverter.convert(modelRunRecordList, ModelRunRecordBO.class);
        this.setProgressBar(result);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ModelRunRecordBO> findModelRunRecordByPage(RunRecordQueryBO bo, Integer pageNo, Integer pageSize) {
        var lambdaQueryWrapper = new ExtendLambdaQueryWrapper<ModelRunRecord>();
        lambdaQueryWrapper.eq(ModelRunRecord::getModelId, bo.getModelId());
        lambdaQueryWrapper.in(CollUtil.isNotEmpty(bo.getDatasetIds()),ModelRunRecord::getDatasetId, bo.getDatasetIds());
        lambdaQueryWrapper.eq(ObjectUtil.isNotNull(bo.getRunRecordType()),ModelRunRecord::getRunRecordType, bo.getRunRecordType());
        lambdaQueryWrapper.eq(ObjectUtil.isNotNull(bo.getStatus()),ModelRunRecord::getStatus, bo.getStatus());
        lambdaQueryWrapper.orderBy(true, SortEnum.ASC.equals(bo.getAscOrDesc()), ModelRunRecord::getCreatedAt);
        var modelRunRecordPage = modelRunRecordDAO.getBaseMapper().
                selectListWithDatasetNotDeleted(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNo, pageSize),lambdaQueryWrapper);
        var recordBOPage = DefaultConverter.convert(modelRunRecordPage, ModelRunRecordBO.class);
        this.setProgressBar(recordBOPage.getList());
        return recordBOPage;
    }

    public List<DatasetBO> findModelRunFilterDatasetName(String datasetName) {
        var datasetList = modelRunRecordDAO.getBaseMapper().findModelRunFilterDatasetName(datasetName);
        return DefaultConverter.convert(datasetList, DatasetBO.class);
    }


    private void setProgressBar(List<ModelRunRecordBO> modelRunRecords) {
        if (CollUtil.isNotEmpty(modelRunRecords)) {
            modelRunRecords.forEach(modelRunRecord -> {
                switch (modelRunRecord.getStatus()) {
                    case STARTED:
                        modelRunRecord.setCompletionRate(new BigDecimal(0));
                        break;
                    case SUCCESS:
                    case SUCCESS_WITH_ERROR:
                        modelRunRecord.setCompletionRate(new BigDecimal(1));
                        break;
                    case RUNNING:
                        try {
                            var total = modelSerialNoCountDAO.getCount(modelRunRecord.getModelSerialNo());
                            var progress = modelSerialNoIncrDAO.getCount(modelRunRecord.getModelSerialNo());
                            if (total != null && progress != null && progress > 0) {
                                var rate = new BigDecimal(progress).divide(new BigDecimal(total), 2, RoundingMode.HALF_UP);
                                modelRunRecord.setCompletionRate(rate);
                            }
                        } catch (Exception e) {
                            log.error("ModelRunRecord setProgressBar fail: " + e);
                        }
                        break;
                    default:
                }
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        ModelRunRecord modelRunRecord = modelRunRecordDAO.getById(id);
        if (modelRunRecord == null) {
            return;
        }
        modelRunRecordDAO.removeById(id);
        var modelRunRecordId = modelRunRecord.getId();

        modelDatasetResultDAO.remove(new LambdaUpdateWrapper<ModelDatasetResult>()
                .eq(ModelDatasetResult::getModelSerialNo, modelRunRecord.getModelSerialNo())
                .eq(ModelDatasetResult::getRunRecordId, modelRunRecordId)
        );
    }

    @Transactional(readOnly = true)
    public List<ModelRunRecord> findByIds(List<Long> ids) {
        return modelRunRecordDAO.listByIds(ids);
    }
}
