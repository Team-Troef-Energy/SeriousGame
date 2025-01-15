import { createRouter, createWebHistory } from "vue-router";
import Playground from "../pages/Playground.vue";
import Level from "../pages/Level.vue";
import LevelSelect from "../pages/LevelSelect.vue";
import Home from "../pages/Home.vue";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/playground", name: "Playground", component: Playground },
  { path: "/level/:levelNmr", name: "Level", component: Level },
  { path: "/levelSelect", name: "levelSelect", component: LevelSelect },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
