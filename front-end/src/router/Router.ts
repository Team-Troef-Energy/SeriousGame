import { createRouter, createWebHistory } from "vue-router";
import DashboardPage from "../pages/DashboardPage.vue";
import Home from "../pages/Home.vue";
import Level from "../pages/Level.vue";
import LevelSelect from "../pages/LevelSelect.vue";
import LoginPage from "../pages/LoginPage.vue";
import RegisterPage from '../pages/RegisterPage.vue';
import TermsPage from "../pages/TermsPage.vue";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/level/:levelNmr", name: "Level", component: Level },
  { path: "/levelSelect", name: "levelSelect", component: LevelSelect },
  { path: "/register", name: 'RegisterPage', component: RegisterPage },
  { path: "/terms", name: 'TermsPage', component: TermsPage },
  { path: "/login", name: 'LoginPage', component: LoginPage },
  { path: "/dashboard", name: 'DashboardPage', component: DashboardPage }
];

const router = createRouter({
  history: createWebHistory(), //TODO: make createWebHistory() work. The Vue configuration is correct so the problem probably lies in the server side configuration
  routes,
});

export default router;
