import { createApp } from "vue";
import { createMemoryHistory, createRouter } from "vue-router";

import TestPage from "./pages/TestPage.vue";
import "./style.css";
import App from "./App.vue";

const routes = [
  { path: "/", component: App },
  { path: "/test", component: TestPage },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});

createApp(App).use(router).mount("#app");
