import { createApp } from "vue";
import { createMemoryHistory, createRouter } from "vue-router";

import TestPage from "./pages/TestPage.vue";
import "./style.css";
import App from "./App.vue";
import Router from "./router/Router.ts";

const vuetify = createVuetify({
    components,
    directives,
})

createApp(App).use(router).use(vuetify).mount('#app')
