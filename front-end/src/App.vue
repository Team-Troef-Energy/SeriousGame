<script setup lang="ts"></script>

<template>
  <div class="container">
    <header>
      <div class="header-grid">
        <img src="/troef-logo.png" alt="logo" />
        <h1>TROEF Energy - Serious game</h1>
      </div>
    </header>
    <main>
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
}

header {
  text-align: center;
  padding: 1em;
  background: linear-gradient(to right, #eeffff 80%, #e3d26b);
  font-size: 0.4rem;
  color: #000000;
}

main {
  flex: 1;
  overflow: auto;
  overflow-y: hidden !important;
}

.header-grid {
  display: grid;
  grid-template-columns: 50px 1fr 50px;
  align-items: center;
}

header img {
  height: 50px;
  grid-column: 1;
}

header h1 {
  margin: 0;
  font-size: 3em;
  color: #3b3b3b;
  font-weight: 600;
  grid-column: 2;
  text-align: center;
}
</style>