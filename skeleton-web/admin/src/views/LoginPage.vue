<template>
    <div class="login-page">
        <a-card title="登录" :bordered="false" style="width: 400px">
            <template #extra><span style="color: #999;">Java Skeleton Admin</span></template>
            <a-form
                :model="formState"
                name="login"
                autocomplete="off"
                @finish="onFinish"
                @finishFailed="onFinishFailed"
            >
                <a-form-item
                    name="username"
                    :rules="[{ required: true, message: 'Please input your username!' }]"
                >
                    <a-input v-model:value="formState.username">
                        <template #prefix>
                            <UserOutlined  />
                        </template>
                    </a-input>
                </a-form-item>
                <a-form-item
                    name="password"
                    :rules="[{ required: true, message: 'Please input your password!' }]"
                >
                    <a-input-password v-model:value="formState.password">
                        <template #prefix>
                            <LockOutlined class="site-form-item-icon" />
                        </template>
                    </a-input-password>
                </a-form-item>

                <a-form-item>
                    <a-button type="primary" block html-type="submit" :disabled="formState.username === '' || formState.password === ''">登录</a-button>
                </a-form-item>
            </a-form>
        </a-card>
    </div>
</template>

<script setup>

import {reactive } from 'vue';
import { message } from 'ant-design-vue';
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import {useAuthStore} from "@shared/store/useAuthStore";
import {authApi} from "@/api/auth";
import {useRouter} from 'vue-router';

const store = useAuthStore();
const router = useRouter();

const formState = reactive({
    username: '',
    password: '',
});

const onFinish = async (params) => {
    try {
        const loginedUser = await authApi.login(params);
        store.login(loginedUser);
    } catch (err) {
        console.error(err);
        message.error(err.message);
    }
    await router.push({name: 'welcome'});
};

const onFinishFailed = (error) => {
    console.log("onFinishFailed", error);
}

</script>

<style lang="less">
.login-page {
    background: #f3f3f3;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center
}
</style>
