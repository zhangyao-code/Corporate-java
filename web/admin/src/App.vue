<script setup>
import {ref} from 'vue';
import { RouterView } from 'vue-router'
import { ConfigProvider as AConfigProvider } from 'ant-design-vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';

dayjs.locale('zh-cn');

const locale = ref(zhCN);

const getPopupContainer = () => {
    return document.body
}

</script>

<template>
    <a-config-provider :locale="locale" :get-popup-container="getPopupContainer">
        <RouterView />
    </a-config-provider>
</template>

<style lang="less">
#app {
    height: 100%;
}
</style>
