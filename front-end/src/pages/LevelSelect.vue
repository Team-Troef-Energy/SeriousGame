<template>
  <div class="level-select">
    <LevelSelectButton level="1" />
    <LevelSelectButton level="2" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import LevelSelectButton from "../components/LevelSelectButton.vue";

export default defineComponent({
  name: "Level",
  components: {
    LevelSelectButton,
  },
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
