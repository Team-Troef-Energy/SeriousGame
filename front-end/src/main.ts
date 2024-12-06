import { createApp } from "vue";
import { createMemoryHistory, createRouter } from "vue-router";

import TestPage from "./pages/TestPage.vue";
import "./style.css";
import App from "./App.vue";
import LevelSelectPage from "./pages/LevelSelectPage.vue";

const routes = [
  { path: "/", component: App },
  { path: "/test", component: LevelSelectPage },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});

createApp(App).use(router).mount("#app");
