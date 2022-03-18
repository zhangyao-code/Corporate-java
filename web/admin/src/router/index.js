import { createRouter, createWebHistory } from 'vue-router'
import BasicLayout from "@/layouts/BasicLayout";
import BlankLayout from "@/layouts/BlankLayout";

const routes = [
  {
    path: '/',
    name: 'home',
    meta: {title: 'Home'},
    component: BasicLayout,
    redirect: '/welcome',
    children: [
      {
        path: '/welcome',
        name: 'welcome',
        meta: { title: '欢迎' },
        component: () => import('../views/HomeView.vue'),
      },
      {
        path: '/user',
        name: 'user',
        meta: { title: '用户' },
        component: BlankLayout,
        redirect: '/user/index',
        children: [
          {
            path: '/user/index',
            name: 'user_index',
            meta: { title: '用户' },
            component: () => import('../views/UserPage.vue'),
          },
          {
            path: '/user/role',
            name: 'user_role',
            meta: { title: '角色' },
            component: () => import('../views/HomeView.vue'),
          },
          {
            path: '/user/login_log',
            name: 'user_login_log',
            meta: { title: '登录日志' },
            component: () => import('../views/HomeView.vue'),
          },
        ]
      },
      {
        path: '/system',
        name: 'system',
        meta: { title: '系统' },
        component: BlankLayout,
        redirect: '/system/index',
        children: [
          {
            path: '/system/index',
            name: 'system_index',
            meta: { title: '系统日志' },
            component: () => import('../views/HomeView.vue'),
          },
        ]
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    meta: {title: '登录', requiredAuth: false},
    component: () => import('../views/LoginPage.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router