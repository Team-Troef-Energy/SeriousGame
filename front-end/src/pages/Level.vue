<template>
  <div class="level container">
    <div class="level-container">
      <NavigateButton id="navigate-button" label="Verlaat level" to="/levelSelect" backgroundColor="#cc0000" />
      <div class="game-content">
        <div ref="gameCanvas" class="game-canvas">
          <div class="connection-line-container">
            <template v-for="(transformer, transformerIndex) in transformers">
              <ConnectionLine v-for="(house, houseIndex) in transformer.houses"
                :key="'connection-' + transformerIndex + '-' + houseIndex"
                :x1="(transformerPositions[transformerIndex] % 10) * 150 + 350"
                :y1="Math.floor(transformerPositions[transformerIndex] / 10) * 80 * getResolutionFactor() + 125"
                :x2="(housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] % 10) * 150 + 100"
                :y2="Math.floor(housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] / 10) * 80 * getResolutionFactor() + 60"
                :hasCongestion="house.hasCongestion" :is-production="house.current.direction === 'PRODUCTION'"
                :current="house.current.amount" :maxCurrent="house.maxCurrent" :maxHouseCurrent="getMaxHouseCurrent"
                @show-info-box="showInfoBox" @hide-info-box="hideInfoBox" />
            </template>
          </div>
          <template v-for="(transformer, transformerIndex) in transformers">
            <transformer v-for="(transformer, transformerIndex) in transformers" :key="'transformer-' + transformer.id"
              :style="{
                position: 'absolute',
                left: (transformerPositions[transformerIndex] % 10) * 150 + 220 + 'px',
                top: Math.floor(transformerPositions[transformerIndex] / 10) * 80 * getResolutionFactor() + 30 + 'px',
              }" @click="showTransformerDetails(transformer)" :hasBatteries="transformer.batteries.amount > 0" />
            <House v-for="(house, houseIndex) in transformer.houses"
              :key="'house-' + (houseIndex + transformers.slice(0, transformerIndex).reduce((acc, t) => acc + t.houses.length, 0))"
              :style="{
                position: 'absolute',
                left: (housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] % 10) * 150 + 'px',
                top: Math.floor(housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] / 10) * 80 * getResolutionFactor() + 'px',
              }" @click="showHouseDetails(house)" :hasElectricCar="house.hasElectricVehicle"
              :hasHeatPump="house.hasHeatpump" :hasSolarPanels="house.solarpanels > 0"
              :hasBatteries="house.batteries.amount > 0" :solarpanels="house.solarpanels"
              :batteries="house.batteries" />
          </template>
          <Dashboard :coinsUsed="dashboardData.coinsUsed" :maxCoins="dashboardData.maxCoins"
            :currentCO2="dashboardData.currentCO2" :MaxCO2="dashboardData.maxCO2"
            :totalEnergyConsumption="dashboardData.totalEnergyConsumption"
            :greenProducedEnergyPercentage="dashboardData.greenProducedEnergyPercentage"
            :objectiveStartTime="dashboardData.objectiveStartTime" :objectiveEndTime="dashboardData.objectiveEndTime"
            :season="dashboardData.season" />
        </div>
        <GameSideBar />
      </div>
      <div v-if="infoBoxVisible" :style="infoBoxStyle" class="infoBox" v-html="infoBoxContents"></div>
      <PopupComponent v-if="isPopupOpen" :isOpen="isPopupOpen" :popupProperties="popupProperties"
        :transformers=transformers @update:isOpen="isPopupOpen = $event" @submitChanges="submitChanges" />
      <Notification v-if="notificationStatus" :status="notificationStatus" :message="notificationMessage" />
    </div>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent, onMounted, ref, Ref } from "vue";
import { useRoute } from "vue-router";
import ConnectionLine from "../components/ConnectionLine.vue";
import Dashboard from "../components/Dashboard.vue";
import House from "../components/House.vue";
import NavigateButton from "../components/NavigateButton.vue";
import Notification from "../components/Notification.vue";
import PopupComponent from "../components/PopupComponent.vue";
import Transformer from "../components/Transformer.vue";
import GameSideBar from "../components/GameSideBar.vue";
import { PopupProperties } from "../objects/PopupProperties";
import { gameLevelService } from "../services/game/GameLevelService";
import { house, levelData, transformer } from "../types";

export default defineComponent({
  name: "Level",
  components: {
    Transformer,
    House,
    ConnectionLine,
    PopupComponent,
    NavigateButton,
    Dashboard,
    Notification,
    GameSideBar,
  },
  setup() {
    const route = useRoute();
    let levelNumber = route.params.levelNmr;
    let gameId = "";

    if (typeof levelNumber === "object") {
      levelNumber = levelNumber[0];
      console.error("multiple level numbers were passed");
    }
    const gameCanvas = ref<HTMLDivElement | null>(null);
    const transformerPositions = ref<number[]>([]);
    const housePositions = ref<number[]>([]);
    const transformers = ref<transformer[]>([]);
    const dashboardData = ref({
      coinsUsed: 0,
      maxCoins: 0,
      currentCO2: 0,
      maxCO2: 0,
      totalEnergyConsumption: 0,
      greenProducedEnergyPercentage: 0,
      objectiveStartTime: 0,
      objectiveEndTime: 0,
      season: "",
    });

    let solarPanelCost = 0;
    let batteryCost = 0;

    const popupProperties = ref<PopupProperties | undefined>(undefined);
    const isPopupOpen = ref(false);

    const notificationStatus = ref(false);
    const notificationMessage = ref("");

    const infoBoxVisible = ref(false);
    const infoBoxContents = ref("");
    const infoBoxStyle: Ref<CSSProperties> = ref({
      position: "absolute",
      top: "0px",
      left: "0px",
      backgroundColor: "#333",
      color: "#fff",
      padding: "10px",
      borderRadius: "5px",
      pointerEvents: "none",
    });

    const generatePositions = (count: number, start: number): number[] => {
      const positions: number[] = [];
      for (let i = 0; i < count; i++) {
        positions.push(start + i);
      }
      return positions;
    };

    const getCumulativeHouseIndex = (transformerIndex: number, houseIndex: number): number => {
      return houseIndex + transformers.value
        .slice(0, transformerIndex)
        .reduce((acc, t) => acc + t.houses.length, 0);
    };

    const getResolutionFactor = () => {
      const baseWidth = 1920;
      const baseHeight = 1080;

      const viewportWidth = window.innerWidth;
      const viewportHeight = window.innerHeight;

      const widthFactor = viewportWidth / baseWidth;
      const heightFactor = viewportHeight / baseHeight;

      const factor = Math.max(widthFactor, heightFactor);

      return factor;
    }

    const getMaxHouseCurrent = computed(() => {
      let maxCurrent = 0;
      transformers.value.forEach((transformer) => {
        transformer.houses.forEach((house) => {
          if (house.current.amount > maxCurrent) {
            maxCurrent = house.current.amount;
          }
        });
      });
      return maxCurrent;
    });

    const showHouseDetails = (house: house) => {
      isPopupOpen.value = true;
      popupProperties.value = new PopupProperties(house, solarPanelCost, batteryCost);
    };

    const showTransformerDetails = (transformer: transformer) => {
      isPopupOpen.value = true;
      popupProperties.value = new PopupProperties(transformer, solarPanelCost, batteryCost);
    };

    const processDashboardData = (data: levelData) => {
      const lastHourData = data.hours[data.hours.length - 1];
      let totalConsumption = 0;
      let totalGreenProduction = 0;
      let totalGreyProduction = 0;

      lastHourData.transformers.forEach((transformer: transformer) => {
        if (transformer.current.direction === "DEMAND") {
          totalGreyProduction += transformer.current.amount;
        }
        transformer.houses.forEach((house: house) => {
          totalConsumption += house.consumption;
          totalGreenProduction += house.production;
        });
      });

      const totalProduction = totalGreenProduction + totalGreyProduction;
      const greenProducedEnergyPercentage = totalProduction == 0 ? 0 : (totalGreenProduction / totalProduction) * 100;

      dashboardData.value = {
        coinsUsed: data.totalCosts,
        maxCoins: data.objective.maxCoins,
        currentCO2: data.totalCO2,
        maxCO2: data.objective.maxCO2,
        totalEnergyConsumption: totalConsumption,
        greenProducedEnergyPercentage: greenProducedEnergyPercentage,
        objectiveStartTime: data.startTime,
        objectiveEndTime: data.endTime,
        season: data.season,
      };
    };

    const submitChanges = async () => {
      try {
        const data = {
          transformers: transformers.value.map((transformer) => ({
            id: transformer.id,
            batteries: transformer.batteries.amount,
            houses: transformer.houses.map((house) => ({
              id: house.id,
              batteries: house.batteries.amount,
              solarpanels: house.solarpanels,
            })),
          })),
        };
        const response = await gameLevelService.fetchUpdateLevel(gameId, data);
        const lastHourData = response.hours[response.hours.length - 1];
        transformerPositions.value = generatePositions(lastHourData.transformers.length, 20);
        housePositions.value = generatePositions(
          lastHourData.transformers.reduce(
            (acc: number, transformer: any) => acc + transformer.houses.length,
            0
          ),
          50
        );
        transformers.value = lastHourData.transformers;

        processDashboardData(response);

        if (response.isCompleted === true) {
          notificationStatus.value = true;
          notificationMessage.value = "Level is behaald! 🥳";
        }
      } catch (error) {
        console.error("Failed to submit changes:", error);
      }
      isPopupOpen.value = false;
    };

    const showInfoBox = ({ x, y, contents }: { x: number; y: number; contents: any }) => {
      infoBoxVisible.value = true;
      infoBoxContents.value = contents;
      infoBoxStyle.value.top = `${y + 10}px`;
      infoBoxStyle.value.left = `${x + 10}px`;
    };

    const hideInfoBox = () => {
      infoBoxVisible.value = false;
    };

    onMounted(async () => {
      try {
        const data = await gameLevelService.fetchStartLevel(levelNumber);
        gameId = data.id;
        solarPanelCost = data.cost.solarPanelCost;
        batteryCost = data.cost.batteryCost;
        const lastHourData = data.hours[data.hours.length - 1];
        transformerPositions.value = generatePositions(lastHourData.transformers.length, 20);
        housePositions.value = generatePositions(
          lastHourData.transformers.reduce(
            (acc: number, transformer: any) => acc + transformer.houses.length,
            0
          ),
          50
        );
        transformers.value = lastHourData.transformers;
        processDashboardData(data);
      } catch (error) {
        console.error("Failed to fetch initial level data:", error);
      }
    });

    return {
      gameCanvas,
      transformerPositions,
      housePositions,
      transformers,
      dashboardData,
      isPopupOpen,
      popupProperties,
      getResolutionFactor,
      getMaxHouseCurrent,
      getCumulativeHouseIndex,
      showHouseDetails,
      showTransformerDetails,
      submitChanges,
      infoBoxVisible,
      infoBoxContents,
      infoBoxStyle,
      showInfoBox,
      hideInfoBox,
      notificationStatus,
      notificationMessage,
    };
  },
});
</script>

<style scoped>
.level {
  height: 94.3vh;
  min-width: 100%;
}

.level-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
}

.game-content {
  display: flex;
  flex: 1;
  width: 100%;
}

.game-canvas {
  flex: 1;
  display: flex;
  align-items: end;
  height: 100%;
  position: relative;
  background:
    linear-gradient(45deg, rgba(92, 179, 230, 0.5), rgba(115, 193, 119, 0.5)),
    url("/Cartoon_green_texture_grass.jpg");
  background-size: 200% 200%, 25%;
  background-position: 0% 50%, center;
  animation: gradientMove 6s infinite linear;
}

@keyframes gradientMove {
  0% {
    background-position: 0% 50%, center;
  }

  50% {
    background-position: 100% 50%, center;
  }

  100% {
    background-position: 0% 50%, center;
  }
}

.line-svg {
  width: 100%;
  height: 100%;
}

#submit-button {
  z-index: 1000;
}

#navigate-button {
  z-index: 1000;
  position: absolute;
  margin: 10px;
}

.infoBox {
  font-size: 14px;
  z-index: 1000;
  white-space: nowrap;
}
</style>