<template>
  <div>
    <Header/>
    <main>
      <RouterView />
    </main>
  </div>
</template>

<script setup lang="ts">
import Header from "./components/global/Header.vue";
</script>