import { createRouter, createWebHistory } from "vue-router";
import DashboardPage from "../pages/DashboardPage.vue";
import Home from "../pages/Home.vue";
import Level from "../pages/Level.vue";
import LevelSelect from "../pages/LevelSelect.vue";
import LoginPage from "../pages/LoginPage.vue";
import RegisterPage from "../pages/RegisterPage.vue";
import TermsPage from "../pages/TermsPage.vue";
import TutorialPage from "../pages/TutorialPage.vue";
import { firebaseService } from "../services/firebase/FirebaseService";
import { databaseService } from "../services/firebase/DatabaseService";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/level/:levelNmr", name: "Level", component: Level },
  { path: "/levelSelect", name: "levelSelect", component: LevelSelect },
  { path: "/register", name: "RegisterPage", component: RegisterPage },
  { path: "/terms", name: "TermsPage", component: TermsPage },
  { path: "/tutorial", name: "TutorialPage", component: TutorialPage },
  { path: "/login", name: "LoginPage", component: LoginPage },
  { path: "/dashboard", name: "DashboardPage", component: DashboardPage },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Utility to get user role
async function getCurrentUserRole(): Promise<string> {
  return new Promise((resolve, reject) => {
    firebaseService.auth.onAuthStateChanged(async (user: any) => {
      if (user) {
        try {
          const userWithRole: any = await databaseService.getUserByEmail(user.email);
          resolve(userWithRole.role);
        } catch (error) {
          reject(error);
        }
      } else {
        resolve("user");
      }
    });
  });
}

router.beforeEach(async (to, from, next) => {
  const role = await getCurrentUserRole();

  if (to.name === "DashboardPage" && role !== "admin") {
    return next({ name: "Home" }); // redirect non-admins to home
  }

  next();
});

export default router;