import { createRouter, createWebHistory } from "vue-router";
import Home from "../pages/Home.vue";
import Level from "../pages/Level.vue";
import LevelSelect from "../pages/LevelSelect.vue";
import Playground from "../pages/Playground.vue";
import RegisterPage from '../pages/RegisterPage.vue';

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/playground", name: "Playground", component: Playground },
  { path: "/level/:levelNmr", name: "Level", component: Level },
  { path: "/levelSelect", name: "levelSelect", component: LevelSelect },
  { path: "/register", name: 'RegisterPage', component: RegisterPage }
];

const router = createRouter({
  history: createWebHistory(), //TODO: make createWebHistory() work. The Vue configuration is correct so the problem probably lies in the server side configuration
  routes,
});

export default router;
