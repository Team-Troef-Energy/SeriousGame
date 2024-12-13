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
        @increase="handleIncrease"
        @decrease="handleDecrease"
    />
    <button id="submit-button" @click="submitChanges">Submit Changes</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import Transformer from '../components/Transformer.vue';
import House from '../components/House.vue';
import ConnectionLine from '../components/ConnectionLine.vue';
import PopupComponent from '../components/PopupComponent.vue';
import { fetchStartLevel, fetchUpdateLevel } from '../utils/api';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'Level',
  components: {
    Transformer,
    House,
    ConnectionLine,
    PopupComponent,
  },
  setup() {
    const route = useRoute();
    const levelNumber = route.params.levelNmr;

    const gameCanvas = ref<HTMLDivElement | null>(null);
    const transformerPositions = ref<number[]>([]);
    const housePositions = ref<number[]>([]);
    const transformers = ref<{ id: number, houses: { id: number, batteries: number, solarpanels: number }[] }[]>([]);

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

    const showHouseDetails = (house: { id: number, batteries: number, solarpanels: number, production: number, consumption: number }) => {
      popupTitle.value = `Huis ${house.id}`;
      popupEnergyProduction.value = house.production;
      popupEnergyConsumption.value = house.consumption;
      popupHeatPumps.value = 0; // Example value
      popupElectricCars.value = 0; // Example value
      popupSolarPanels.value = house.solarpanels;
      popupBatteries.value = house.batteries;
      isPopupOpen.value = true;
    };

    const updateSolarPanels = (newValue: number) => {
      const house = transformers.value.flatMap(t => t.houses).find(h => h.id === parseInt(popupTitle.value.split(' ')[1]));
      if (house) {
        house.solarpanels = newValue;
      }
    };

    const handleIncrease = (property: string) => {
      if (property === 'solarPanels') {
        popupSolarPanels.value += 1;
        updateSolarPanels(popupSolarPanels.value);
      } else if (property === 'batteries') {
        popupBatteries.value += 1;
        // TODO: Update batteries logic here
      }
    };

    const handleDecrease = (property: string) => {
      if (property === 'solarPanels' && popupSolarPanels.value > 0) {
        popupSolarPanels.value -= 1;
        updateSolarPanels(popupSolarPanels.value);
      } else if (property === 'batteries' && popupBatteries.value > 0) {
        popupBatteries.value -= 1;
        // TODO: Update batteries logic here
      }
    };

    const submitChanges = async () => {
      try {
        const data = {
          transformers: transformers.value.map(transformer => ({
            id: transformer.id,
            houses: transformer.houses.map(house => ({
              id: house.id,
              batteries: house.batteries.amount,
              solarpanels: house.solarpanels
            }))
          }))
        };
        const response = await fetchUpdateLevel(levelNumber, data);
        console.log('Changes submitted:', response);
        const lastHourData = response.hours[response.hours.length - 1]; // Get the data for the final hour
        transformerPositions.value = generatePositions(lastHourData.transformers.length, 20);
        housePositions.value = generatePositions(lastHourData.transformers.reduce((acc, transformer) => acc + transformer.houses.length, 0), 50);
        transformers.value = lastHourData.transformers;
      } catch (error) {
        console.error('Failed to submit changes:', error);
      }
    };

    onMounted(async () => {
      try {
        const data = await fetchStartLevel(levelNumber);
        console.log('Initial level data:', data);
        const lastHourData = data.hours[data.hours.length - 1]; // Get the data for the final hour
        transformerPositions.value = generatePositions(lastHourData.transformers.length, 20);
        housePositions.value = generatePositions(lastHourData.transformers.reduce((acc, transformer) => acc + transformer.houses.length, 0), 50);
        transformers.value = lastHourData.transformers;
      } catch (error) {
        console.error('Failed to fetch initial level data:', error);
      }
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
      updateSolarPanels,
      handleIncrease,
      handleDecrease,
      submitChanges,
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

#submit-button {
  z-index: 1000;
}
</style>