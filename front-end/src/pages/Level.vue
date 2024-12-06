<template>
  <div class="level-container">
    <div ref="gameCanvas" class="game-canvas">
      <template v-for="transformer in transformers">
        <ConnectionLine
            v-for="house in transformer.houses"
            :key="'connection-' + house.id"
            :x1="(transformerPositions[transformer.id - 1] % 10) * 150 + 100"
            :y1="Math.floor(transformerPositions[transformer.id - 1] / 10) * 80 + 125"
            :x2="(housePositions[house.id - 1] % 10) * 150 + 100"
            :y2="Math.floor(housePositions[house.id - 1] / 10) * 80 + 130"
        />
      </template>
      <Transformer
          v-for="(pos, index) in transformerPositions"
          :key="'transformer-' + index"
          :style="{ position: 'absolute', left: (pos % 10) * 150 + 'px', top: Math.floor(pos / 10) * 80 + 'px' }"
      />
      <template v-for="transformer in transformers">
        <House
            v-for="house in transformer.houses"
            :key="'house-' + house.id"
            :style="{ position: 'absolute', left: (housePositions[house.id - 1] % 10) * 150 + 'px', top: Math.floor(housePositions[house.id - 1] / 10) * 80 + 'px' }"
        />
      </template>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import Transformer from '../components/Transformer.vue';
import House from '../components/House.vue';
import ConnectionLine from '../components/ConnectionLine.vue';

export default defineComponent({
  name: 'Level',
  components: {
    Transformer,
    House,
    ConnectionLine,
  },
  setup() {
    const gameCanvas = ref<HTMLDivElement | null>(null);
    const transformerPositions = ref<number[]>([]);
    const housePositions = ref<number[]>([]);
    const transformers = ref<{ id: number, houses: { id: number }[] }[]>([]);

    const generatePositions = (count: number, start: number): number[] => {
      const positions = [];
      for (let i = 0; i < count; i++) {
        positions.push(start + i);
      }
      return positions;
    };

    onMounted(async () => {
      const response = await fetch('/LevelDummyData.json');
      const data = await response.json();

      transformerPositions.value = generatePositions(data.transformers.length, 20);
      housePositions.value = generatePositions(data.transformers.reduce((acc, transformer) => acc + transformer.houses.length, 0), 50);
      transformers.value = data.transformers;
    });

    return {
      gameCanvas,
      transformerPositions,
      housePositions,
      transformers,
    };
  },
});
</script>

<style scoped>
.level-container {
  height: 100vh;
  width: 50vw;
  display: flex;
  flex-direction: column;
  position: relative;
}

.game-canvas {
  width: 100%;
  height: 100%;
  position: relative;
  background: url('./grass.jpg') no-repeat center center;
  background-size: cover;
}
</style>