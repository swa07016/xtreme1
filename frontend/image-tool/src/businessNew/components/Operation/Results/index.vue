<template>
  <Collapse>
    <template #header>
      <Header title="Instance" />
    </template>
    <div class="operation-instance" ref="domRef">
      <ResultList />
      <div v-if="resultState.list.length === 0" style="padding: 4px 10px"> No Data </div>
    </div>
  </Collapse>
</template>

<script setup lang="ts">
  import useResults from './useResults';
  import { useResultsProvide } from './context';

  import Collapse from '../../Collapse/index.vue';
  import Header from './Header.vue';
  import ResultList from './ResultList.vue';
  import useObjectItem from './useObjectItem';
  import useClassItem from './useClassItem';

  const { resultState, domRef, onUpdateList } = useResults();
  const itemHandler = useObjectItem();
  const classHandler = useClassItem();

  useResultsProvide({
    resultState,
    itemHandler,
    classHandler,
    onUpdateList,
  });
</script>

<style lang="less">
  .operation-instance {
    position: relative;
    padding: 8px 7px 10px;
    overflow-x: hidden;
    overflow-y: overlay;
    height: 100%;
    text-align: left;
    .operation-instance-collapse {
      position: relative;
      height: 100%;
      user-select: none;
    }
    .extra-tool {
      display: flex;
      padding-right: 10px;
      align-items: center;
      float: right;
      height: 100%;
      height: 36px;
      pointer-events: visible;

      .tool-icon {
        margin-left: 7px;
        font-size: 14px;

        &:hover {
          color: #57ccef;
        }
      }
    }
  }
</style>
