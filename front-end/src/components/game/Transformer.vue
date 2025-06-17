<template>
  <div class="transformer">
    <img class="transformer" src="/images/game/transformer.png" alt="Transformer" />
    <img v-if="hasBatteries" class="batteries" src="/images/game/battery.png" alt="Batteries" />
  </div>
</template>

<script lang="ts">
export default {
  props: {
    hasBatteries: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>
.transformer {
  height: 13rem;
}

img .transformer {
  width: 100%;
}

.batteries {
  position: absolute;
  top: 55%;
  left: 75%;
  width: 3rem;
  z-index: 1;
}
</style>