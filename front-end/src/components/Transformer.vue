<template>
  <div class="transformer">
    <img class="transformer" src="/transformer.png" alt="Transformer" />
    <img v-if="hasBatteries" class="batteries" src="/battery.png" alt="Batteries" />
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
  width: 18rem;
  height: 9rem;
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