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
            @click="showHouseDetails(house)"
        />
      </template>
    </div>
    <PopupComponent
        v-if="isPopupOpen"
        :isOpen="isPopupOpen"
        :title="popupTitle"
        :type="popupType"
        :energyProduction="popupEnergyProduction"
        :energyConsumption="popupEnergyConsumption"
        :heatPumps="popupHeatPumps"
        :electricCars="popupElectricCars"
        :solarPanels="popupSolarPanels"
        :batteries="popupBatteries"
        @update:isOpen="isPopupOpen = $event"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import Transformer from '../components/Transformer.vue';
import House from '../components/House.vue';
import ConnectionLine from '../components/ConnectionLine.vue';
import PopupComponent from '../components/PopupComponent.vue';

export default defineComponent({
  name: 'Level',
  components: {
    Transformer,
    House,
    ConnectionLine,
    PopupComponent,
  },
  setup() {
    const gameCanvas = ref<HTMLDivElement | null>(null);
    const transformerPositions = ref<number[]>([]);
    const housePositions = ref<number[]>([]);
    const transformers = ref<{ id: number, houses: { id: number, energyProduction: number, energyConsumption: number, heatPumps: number, electricCars: number, solarPanels: number, batteries: number }[] }[]>([]);

    const isPopupOpen = ref(false);
    const popupTitle = ref('');
    const popupType = ref('huis');
    const popupEnergyProduction = ref(0);
    const popupEnergyConsumption = ref(0);
    const popupHeatPumps = ref(0);
    const popupElectricCars = ref(0);
    const popupSolarPanels = ref(0);
    const popupBatteries = ref(0);

    const generatePositions = (count: number, start: number): number[] => {
      const positions = [];
      for (let i = 0; i < count; i++) {
        positions.push(start + i);
      }
      return positions;
    };

    const showHouseDetails = (house: { id: number, energyProduction: number, energyConsumption: number, heatPumps: number, electricCars: number, solarPanels: number, batteries: number }) => {
      popupTitle.value = `Huis ${house.id}`;
      popupEnergyProduction.value = house.energyProduction;
      popupEnergyConsumption.value = house.energyConsumption;
      popupHeatPumps.value = house.heatPumps;
      popupElectricCars.value = house.electricCars;
      popupSolarPanels.value = house.solarPanels;
      popupBatteries.value = house.batteries;
      isPopupOpen.value = true;
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
      isPopupOpen,
      popupTitle,
      popupType,
      popupEnergyProduction,
      popupEnergyConsumption,
      popupHeatPumps,
      popupElectricCars,
      popupSolarPanels,
      popupBatteries,
      showHouseDetails,
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
  background: url('/grass.jpg') no-repeat center center;
  background-size: cover;
}
</style>