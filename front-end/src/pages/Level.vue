<template>
  <div class="level-container">
    <div class="sky"></div>
    <div class="ground">
      <div class="grid">
        <div v-for="n in 100" :key="n" class="grid-item">
          <Transformer v-if="isTransformerPosition(n)" />
          <House v-if="isHousePosition(n)" />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import Transformer from '../components/Transformer.vue';
import House from '../components/Transformer.vue'; //TODO: Change to House component when created

export default defineComponent({
  name: 'Level',
  components: {
    Transformer,
    House,
  },
  setup() {
    const transformerPositions = ref<number[]>([]);
    const housePositions = ref<number[]>([]);

    onMounted(async () => {
      const response = await fetch('/LevelDummyData.json');
      const data = await response.json();

      if (data.transformerCount === 1) {
        transformerPositions.value = [23];
      } else if (data.transformerCount === 2) {
        transformerPositions.value = [22, 24];
      }

      if (data.houseCount === 1) {
        housePositions.value = [39];
      } else if (data.houseCount === 2) {
        housePositions.value = [39, 43];
      }
    });

    const isTransformerPosition = (index: number) => {
      return transformerPositions.value.includes(index);
    };

    const isHousePosition = (index: number) => {
      return housePositions.value.includes(index);
    };

    return {
      isTransformerPosition,
      isHousePosition,
    };
  },
});
</script>

<style scoped>
.level-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.sky {
  background-color: #99e5ff;
  height: 20%;
}

.ground {
  background-color: #309a30;
  height: 80%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.grid {
  display: grid;
  grid-template-columns: repeat(9, 1fr);
  grid-template-rows: repeat(9, 1fr);
  width: 100%;
  height: 100%;
}

.grid-item {
  border: 1px solid rgba(0, 0, 0, 0.1);
}
</style>