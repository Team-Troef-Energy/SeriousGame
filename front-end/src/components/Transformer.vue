<template>
  <div class="transformer">
    <img class="transformer" src="/transformer.png" alt="Transformer" />
    <img v-if="hasBatteries" class="batteries" src="/batteries.png" alt="Batteries" />
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
  ;
  height: 9rem;
}

img .transformer {
  width: 100%;
  height: 100%;
}

.batteries {
  position: absolute;
  top: 55%;
  left: 75%;
  width: 2rem;
  height: 2rem;
}
</style>