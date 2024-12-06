<template>
  <div class="level-select">
    <div>test</div>
    <div>test2</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from "vue";

export default defineComponent({
  name: "Level",
  components: {},
});
</script>

<style scoped>
.level-select {
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
</style>
