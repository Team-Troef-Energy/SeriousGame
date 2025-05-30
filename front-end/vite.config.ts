import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // Map /config.json naar config.local.json voor lokale ontwikkeling
      '/config.json': path.resolve(__dirname, 'front-end/config/config.local.json'),
    },
  },
  server: {
    fs: {
      // Zorg ervoor dat bestanden buiten de root toegankelijk zijn
      allow: ['.'],
    },
  },
});