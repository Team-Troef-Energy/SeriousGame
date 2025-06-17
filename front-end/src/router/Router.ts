import { createRouter, createWebHistory } from "vue-router";
import DashboardPage from "../pages/account/DashboardPage.vue";
import LoginPage from "../pages/account/LoginPage.vue";
import RegisterPage from "../pages/account/RegisterPage.vue";
import TermsPage from "../pages/account/TermsPage.vue";
import LevelPage from "../pages/game/LevelPage.vue";
import LevelSelectPage from "../pages/game/LevelSelectPage.vue";
import TutorialPage from "../pages/game/TutorialPage.vue";
import HomePage from "../pages/HomePage.vue";
import RaceHostingPage from "../pages/race/RaceHostingPage.vue";
import RaceLevelEditorPage from "../pages/race/RaceLevelEditorPage.vue";
import RaceLevelSelectPage from "../pages/race/RaceLevelSelectPage.vue";
import RacePage from "../pages/race/RacePage.vue";
import RaceSessionPage from "../pages/race/RaceSessionPage.vue";
import RacesPage from "../pages/race/RacesPage.vue";
import { databaseService } from "../services/firebase/DatabaseService";

const routes = [
  { path: "/", name: "Home", component: HomePage },
  { path: "/level/:levelNmr", name: "Level", component: LevelPage },
  { path: "/levelSelect", name: "levelSelect", component: LevelSelectPage },
  { path: "/register", name: "RegisterPage", component: RegisterPage },
  { path: "/terms", name: "TermsPage", component: TermsPage },
  { path: "/tutorial", name: "TutorialPage", component: TutorialPage },
  { path: "/login", name: "LoginPage", component: LoginPage },
  { path: "/dashboard", name: "DashboardPage", component: DashboardPage },
  { path: "/race", name: "RacePage", component: RacesPage },
  { path: '/race/:id', component: RacePage },
  { path: '/race/:id/level-editor', component: RaceLevelEditorPage },
  { path: '/race/:id/levels', component: RaceLevelSelectPage },
  { path: '/race/:raceId/hosting/:sessionId', component: RaceHostingPage },
  { path: '/race/session', component: RaceSessionPage },
];

const AuthenticatedUserPaths = [
  "/race",
  "/race/:id",
  "/race/:id/level-editor",
  "/race/:id/levels",
  "/race/:raceId/hosting/:sessionId"
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const role = await databaseService.getCurrentUserRole();

  if (to.name === "DashboardPage" && role !== "admin") {
    return next({ name: "Home" }); // redirect non-admins to home
  }

  const isAuthenticatedUserRoute = AuthenticatedUserPaths.some(path =>
    to.matched.some(record => record.path === path)
  );
  
  if (isAuthenticatedUserRoute && role === "non-authenticated-user") {
    return next({ name: "LoginPage" });
  }

  next();
});

export default router;