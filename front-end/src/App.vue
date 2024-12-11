<script setup lang="ts"></script>

<template>
  <div class="container">
    <header>
      <div class="header-grid">
        <img src="../public/troef-logo.png" alt="logo" />
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
  background-color: #ffffff;
  border-bottom: 1px solid #000000;
  font-size: 0.4rem;
  color: #000000;
}

main {
  flex: 1;
  overflow: auto;
}

.header-grid {
  display: grid;
  grid-template-columns: 50px 1fr;
  align-items: center;
}

header img {
  height: 50px;
}
</style>
