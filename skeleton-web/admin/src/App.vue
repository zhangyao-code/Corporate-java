<script setup>
import { RouterView } from 'vue-router'
import { ConfigProvider as AConfigProvider } from 'ant-design-vue'

const getPopupContainer = () => {
    return document.body
}

</script>

<template>
    <a-config-provider :get-popup-container="getPopupContainer">
        <RouterView />
    </a-config-provider>
</template>

<style lang="less">
#app {
    height: 100%;
}
</style>
