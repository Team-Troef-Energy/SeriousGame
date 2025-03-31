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
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 100%;
}

.level-select::before {
  content: '';
  position: absolute;
  top: 30%;
  bottom: 10%;
  left: 50%;
  width: 2px;
  background-color: #2196f3;
  z-index: -1;
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
    position: absolute;
    white-space: nowrap;
    width: 25rem;
  }

  .level-select {
    flex-direction: row;
    height: 5rem;
  }

  .level-select::before {
    top: 53%;
    bottom: 0;
    left: 0;
    right: 0;
    width: unset;
    height: 2px;
  }
}
</style>