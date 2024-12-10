import { createRouter, createWebHistory } from 'vue-router';
import TestPage from "../pages/TestPage.vue";
import Playground from "../pages/Playground.vue";
import Level from "../pages/Level.vue";

const routes = [
    { path: '/', name: 'Home', component: TestPage },
    { path: '/playground', name: 'Playground', component: Playground },
    { path: '/level', name: 'Level', component: Level },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;