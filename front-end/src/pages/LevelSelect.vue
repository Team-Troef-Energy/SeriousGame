<template>
  <div class="level-select">
    <LevelSelectButton v-for="level in levelCount" :key="level" :level="level" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from "vue";
import LevelSelectButton from "../components/LevelSelectButton.vue";
import { fetchCountLevels } from "../utils/api";

export default defineComponent({
  name: "Level",
  components: {
    LevelSelectButton,
  },
  setup() {
    const levelCount = ref(0);

    onMounted(async () => {
      levelCount.value = await fetchCountLevels();
    });

    return {
      levelCount,
    };
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
