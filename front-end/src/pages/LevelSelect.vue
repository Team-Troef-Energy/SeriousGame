<template>
  <div class="level container">
    <h1 class="page-header">Kies een level</h1>
    <div class="level-select">
      <LevelSelectButton v-for="level in levels" :key="level.levelNumber" :level="level.levelNumber" class="level-button" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import LevelSelectButton from "../components/LevelSelectButton.vue";
import { levelTemplate } from "../types/levelTemplate/LevelTemplate";
import { fetchAllLevels } from "../utils/api";

export default defineComponent({
  name: "Level",
  components: {
    LevelSelectButton,
  },
  setup() {
    let levels = ref<levelTemplate[]>([]);

    onMounted(async () => {
      levels.value = await fetchAllLevels();
    });

    return {
      levels,
    };
  },
});
</script>

<style scoped>
.level {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.page-header {
  text-align: center;
  top: 200px;
  width: 100%;
  color: white;
}

.level-select {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 95%;
  max-height: 45%;
  margin: 1rem;
  padding: 1rem;
  gap: 3rem;
  overflow: auto;
}

.level-select::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('/green-happy-background.png');
  background-size: cover;
  z-index: -2;
}

@media (min-width: 768px) {

  .page-header {
    white-space: nowrap;
    width: 25rem;
    margin-bottom: 5rem;
  }

  .level-select {
    flex-direction: row;
    justify-content: center;
    flex-wrap: wrap;
    gap: 5rem;
  }
}
</style>