<template>
    <pro-layout
        :locale="locale"
        v-bind="layoutConf"
        v-model:openKeys="state.openKeys"
        v-model:collapsed="state.collapsed"
        v-model:selectedKeys="state.selectedKeys"
    >
        <template #rightContentRender>
            <span v-if="store.user" style="color: #fff">您好，{{ store.user.username }}</span>
            <a-button type="link" @click="onLogout">退出</a-button>
        </template>

        <router-view />
    </pro-layout>
</template>

<script setup>
import { reactive } from 'vue';
import {useRouter} from 'vue-router';
import { getMenuData, clearMenuItem } from '@ant-design-vue/pro-layout';
import {useAuthStore} from "@shared/store/useAuthStore";

const store = useAuthStore();
const locale = (i18n) => i18n;
const router = useRouter();

const { menuData } = getMenuData(clearMenuItem(router.getRoutes()));

const state = reactive({
    collapsed: false, // default value
    openKeys: [],
    selectedKeys: ['/welcome'],
})
const layoutConf = reactive({
    title: 'Java Skeleton Admin',
    navTheme: 'light',
    headerTheme: 'dark',
    layout: 'mix',
    splitMenus: false,
    menuData,
});

const onLogout = () => {
    store.logout();
    router.push({name: "login"});
}

</script>
