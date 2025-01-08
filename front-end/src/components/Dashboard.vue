<template>
  <div class="dashboard-container">
    <div class="slot">
      <slot name="1">1</slot>
    </div>
    <div class="slot">
      <slot name="2">2</slot>
    </div>
    <div class="slot">
      <slot name="3">3</slot>
    </div>
    <div class="slot">
      <slot name="4">4</slot>
    </div>
  </div>
</template>

<style scoped>
.dashboard-container {
  height: 20vh;
  width: 100vw;
  display: flex;
  flex-direction: row;
  background-color: azure;
}

.slot {
  height: 100%;
  width: 100%;
  text-align: center;
  align-items: center;
}
</style>
