<template>
  <div class="level-container">
    <NavigateButton id="navigate-button" label="Verlaat level" to="/levelSelect" backgroundColor="#cc0000" />
    <div ref="gameCanvas" class="game-canvas">
      <template v-for="transformer in transformers">
        <ConnectionLine
            v-for="house in transformer.houses"
            :key="'connection-' + house.id"
            :x1="(transformerPositions[transformer.id - 1] % 10) * 150 + 100"
            :y1="Math.floor(transformerPositions[transformer.id - 1] / 10) * 80 + 125"
            :x2="(housePositions[house.id - 1] % 10) * 150 + 100"
            :y2="Math.floor(housePositions[house.id - 1] / 10) * 80 + 130"
            :hasCongestion="house.hasCongestion"
            :maxCurrent="house.maxCurrent"
        />
      </template>
      <template v-for="transformer in transformers">
        <transformer
            v-for="transformer in transformers"
            :key="'transformer-' + transformer.id"
            :style="{ position: 'absolute', left: (transformerPositions[transformer.id - 1] % 10) * 150 + 'px', top: Math.floor(transformerPositions[transformer.id - 1] / 10) * 80 + 'px' }"
            @click="showTransformerDetails(transformer)"
            :hasBatteries="transformer.batteries.amount > 0"
        />
        <House
            v-for="house in transformer.houses"
            :key="'house-' + house.id"
            :style="{ position: 'absolute', left: (housePositions[house.id - 1] % 10) * 150 + 'px', top: Math.floor(housePositions[house.id - 1] / 10) * 80 + 'px' }"
            @click="showHouseDetails(house)"
            :hasElectricCar="house.hasElectricVehicle"
            :hasHeatPump="house.hasHeatpump"
            :hasSolarPanels="house.solarpanels > 0"
            :hasBatteries="house.batteries.amount > 0"
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
        :heatPump="popupHeatPump"
        :electricVehicle="popupElectricVehicle"
        :solarPanels="popupSolarPanels"
        :batteries="popupBatteries"
        :batteryCharge="popupBatteryCharge"
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
import NavigateButton from "../components/NavigateButton.vue";
import { fetchStartLevel, fetchUpdateLevel } from '../utils/api';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'Level',
  components: {
    Transformer,
    House,
    ConnectionLine,
    PopupComponent,
    NavigateButton,
  },
  setup() {
    const route = useRoute();
    const levelNumber = route.params.levelNmr;

    const gameCanvas = ref<HTMLDivElement | null>(null);
    const transformerPositions = ref<number[]>([]);
    const housePositions = ref<number[]>([]);
    const transformers = ref<{ id: number, batteries: { amount: number, totalCharge: number }, houses: { id: number, batteries: { amount: number, currentCharge: number }, solarpanels: number, hasCongestion: boolean, maxCurrent: number, hasElectricVehicle: boolean, hasHeatpump: boolean }[] }[]>([]);

    const isPopupOpen = ref(false);
    const popupTitle = ref('');
    const popupType = ref('huis');
    const popupEnergyProduction = ref(0);
    const popupEnergyConsumption = ref(0);
    const popupHeatPump = ref(false);
    const popupElectricVehicle = ref(false);
    const popupSolarPanels = ref(0);
    const popupBatteries = ref(0);
    const popupBatteryCharge = ref(0);

    const generatePositions = (count: number, start: number): number[] => {
      const positions = [];
      for (let i = 0; i < count; i++) {
        positions.push(start + i);
      }
      return positions;
    };

    const showHouseDetails = (house: { id: number, batteries: { amount: number, totalCharge: number }, solarpanels: number, production: number, consumption: number, hasHeatpump: boolean, hasElectricVehicle: boolean }) => {
      popupTitle.value = `Huis ${house.id}`;
      popupType.value = 'huis';
      popupEnergyProduction.value = house.production;
      popupEnergyConsumption.value = house.consumption;
      popupHeatPump.value = house.hasHeatpump;
      popupElectricVehicle.value = house.hasElectricVehicle;
      popupSolarPanels.value = house.solarpanels;
      popupBatteries.value = house.batteries.amount;
      popupBatteryCharge.value = house.batteries.totalCharge;
      isPopupOpen.value = true;
    };

    const showTransformerDetails = (transformer: { id: number, current: { amount: number, direction: string }, batteries: { amount: number, totalCharge: number } }) => {
      popupTitle.value = `Transformator ${transformer.id}`;
      popupType.value = 'transformator';
      popupEnergyProduction.value = transformer.current.direction === 'PRODUCTION' ? transformer.current.amount : 0;
      popupEnergyConsumption.value = transformer.current.direction === 'DEMAND' ? transformer.current.amount : 0;
      popupBatteries.value = transformer.batteries.amount;
      popupBatteryCharge.value = transformer.batteries.totalCharge;
      isPopupOpen.value = true;
    };

    const updateSolarPanels = (newValue: number) => {
      const house = transformers.value.flatMap(t => t.houses).find(h => h.id === parseInt(popupTitle.value.split(' ')[1]));
      if (house) {
        house.solarpanels = newValue;
      }
    };

    const updateBatteries = (newValue: number) => {
      if (popupType.value === 'huis') {
        const house = transformers.value.flatMap(t => t.houses).find(h => h.id === parseInt(popupTitle.value.split(' ')[1]));
        if (house) {
          house.batteries.amount = newValue;
        }
      } else if (popupType.value === 'transformator') {
        const transformer = transformers.value.find(t => t.id === parseInt(popupTitle.value.split(' ')[1]));
        if (transformer) {
          transformer.batteries.amount = newValue;
        }
      }
    };

    const handleIncrease = (property: string) => {
      if (property === 'solarPanels') {
        popupSolarPanels.value += 1;
        updateSolarPanels(popupSolarPanels.value);
      } else if (property === 'batteries') {
        popupBatteries.value += 1;
        updateBatteries(popupBatteries.value);
      }
    };

    const handleDecrease = (property: string) => {
      if (property === 'solarPanels' && popupSolarPanels.value > 0) {
        popupSolarPanels.value -= 1;
        updateSolarPanels(popupSolarPanels.value);
      } else if (property === 'batteries' && popupBatteries.value > 0) {
        popupBatteries.value -= 1;
        updateBatteries(popupBatteries.value);
      }
    };

    const submitChanges = async () => {
      try {
        const data = {
          transformers: transformers.value.map(transformer => ({
            id: transformer.id,
            batteries: transformer.batteries.amount,
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
      popupHeatPump,
      popupElectricVehicle,
      popupSolarPanels,
      popupBatteries,
      popupBatteryCharge,
      showHouseDetails,
      showTransformerDetails,
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
  width: 100vw;
  height: 100vh;
  position: relative;
  background: url('/Cartoon_green_texture_grass.jpg') repeat center center;
  background-size: 25%, 25%;
}

#submit-button {
  z-index: 1000;
}

#navigate-button {
  z-index: 1000;
  position: absolute;
  margin: 10px;
}
</style>