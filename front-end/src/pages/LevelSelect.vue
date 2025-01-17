<template>
  <h1 class="page-header">Kies een level</h1>
  <div class="level-select">
    <LevelSelectButton v-for="level in levelCount" :key="level" :level="level" class="level-button" />
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
.page-header {
  text-align: center;
  position: absolute;
  top: 200px;
  width: 100%;
  color: white;
}

.level-select {
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  position: relative;
}

.level-select::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 2px;
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
  background: linear-gradient( rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7) ), url('/green-happy-background.png');
  background-size: cover;
  z-index: -2;
}
</style>