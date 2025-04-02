<template>
  <AuthProvider>
    <div class="root-element">
      <Header />
      <main>
        <RouterView />
      </main>
    </div>
  </AuthProvider>
</template>

<script setup lang="ts">
import Header from "./components/global/Header.vue";
import AuthProvider from "./context/AuthProvider";
</script>