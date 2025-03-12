import { createApp } from "vue";
import "./app.css";
import App from "./App.vue";
import Router from "./router/Router.ts";

// Vuetify
import { createVuetify } from 'vuetify';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import 'vuetify/styles';

const vuetify = createVuetify({
    components,
    directives,
})

createApp(App).use(Router).use(vuetify).mount('#app')
