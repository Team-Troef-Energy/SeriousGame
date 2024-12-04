import { createRouter, createWebHistory } from 'vue-router';
import TestPage from "../pages/TestPage.vue";
import Playground from "../pages/Playground.vue";

const routes = [
    { path: '/', name: 'Home', component: TestPage },
    { path: '/playground', name: 'Playground', component: Playground },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;