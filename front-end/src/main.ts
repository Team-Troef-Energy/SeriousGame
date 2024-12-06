import { createApp } from "vue";
import { createMemoryHistory, createRouter } from "vue-router";

import TestPage from "./pages/TestPage.vue";
import "./style.css";
import App from "./App.vue";

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const routes = [
  { path: "/", component: App },
  { path: "/test", component: TestPage },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});

const vuetify = createVuetify({
  components,
  directives,
})

createApp(App).use(router).use(vuetify).mount('#app')