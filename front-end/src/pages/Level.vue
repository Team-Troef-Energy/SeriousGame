<template>
  <div class="level container">
    <div class="level-container">

      <a :href="navigateBackUrl" id="navigate-button">
        <img src="/verlaat.png" alt="Something">
      </a>

      <div class="game-content">
        <div ref="gameCanvas" class="game-canvas">
          <div class="connection-line-container">
            <template v-for="(transformer, transformerIndex) in transformers" :key="'transformer-' + transformerIndex">
              <ConnectionLine v-for="(house, houseIndex) in transformer.houses"
                :key="'connection-' + transformerIndex + '-' + houseIndex" v-bind="coordinatesWithMargin(
                  // Transformer X
                  (transformerPositions[transformerIndex] % 10) * 150 + 800 + (houseIndex * 20), // <--- controls horizontal spacing
                  // Transformer Y
                  Math.floor(transformerPositions[transformerIndex] / 10) * 100 * getResolutionFactor() + (houseIndex * -5), // <--- controls vertical spacing
                  // House X
                  (housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] % 10) * 240 + 120, // <--- controls horizontal spacing
                  // House Y
                  Math.floor(housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] / 10) * 80 * getResolutionFactor() + 60, // <--- controls vertical spacing
                  60 // margin
                )" :hasCongestion="house.hasCongestion" :is-production="house.current.direction === 'PRODUCTION'"
                :current="house.current.amount" :maxCurrent="house.maxCurrent" :maxHouseCurrent="getMaxHouseCurrent"
                @show-info-box="showInfoBox" @hide-info-box="hideInfoBox" />
            </template>
          </div>
          <template v-for="(transformer, transformerIndex) in transformers" :key="'transformer-' + transformerIndex">
            <Transformer :style="{
              position: 'absolute',
              left: (transformerPositions[transformerIndex] % 10) * 150 + 720 + 'px',
              top: Math.floor(transformerPositions[transformerIndex] / 10) * 80 * getResolutionFactor() - 75 + 'px',
            }" @click="showTransformerDetails(transformer)" :hasBatteries="transformer.batteries.amount > 0" />
            <House v-for="(house, houseIndex) in transformer.houses"
              :key="'house-' + (houseIndex + transformers.slice(0, transformerIndex).reduce((acc, t) => acc + t.houses.length, 0))"
              :style="{
                position: 'absolute',
                left: (housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] % 10) * 220 + 50 + 'px',
                top: Math.floor(housePositions[getCumulativeHouseIndex(transformerIndex, houseIndex)] / 10) * 90 * getResolutionFactor() + 'px',
              }" @click="showHouseDetails(house)" @drop-item="handleDropItem($event, house)"
              @remove-item="handleRemoveItem($event, house)" :hasElectricCar="house.hasElectricVehicle"
              :hasHeatPump="house.hasHeatpump" :hasSolarPanels="house.solarpanels > 0"
              :hasBatteries="house.batteries.amount > 0" :solarpanels="house.solarpanels"
              :batteries="house.batteries" />
          </template>
          <Dashboard :coinsUsed="dashboardData.coinsUsed" :maxCoins="dashboardData.maxCoins"
            :currentCO2="dashboardData.currentCO2" :maxCO2="dashboardData.maxCO2"
            :totalEnergyConsumption="dashboardData.totalEnergyConsumption"
            :greenProducedEnergyPercentage="dashboardData.greenProducedEnergyPercentage"
            :objectiveStartTime="dashboardData.objectiveStartTime" :objectiveEndTime="dashboardData.objectiveEndTime"
            :season="dashboardData.season" />
        </div>
        <GameSideBar :solarPanelCost="solarPanelCost" :batteryCost="batteryCost" @drag-item="handleDragItem" />
      </div>
      <div v-if="infoBoxVisible" :style="infoBoxStyle" class="infoBox" v-html="infoBoxContents"></div>
      <PopupComponent v-if="isPopupOpen" :isOpen="isPopupOpen" :popupProperties="popupProperties"
        :transformers="transformers" @update:isOpen="isPopupOpen = $event" @submitChanges="submitChanges" />
      <Notification v-if="notificationStatus" :status="notificationStatus" :message="notificationMessage" />

      <button class="chat-toggle-button" @click="toggleChatWindow">
        <img src="/openchat.png" alt="Open chat">
      </button>

      <div v-if="chatWindowOpen" class="chat-window">
        <div class="chat-messages">
          <div v-for="(message, index) in messages" :key="index"
            :class="['message', message.sender === 'user' ? 'user-message' : 'bot-message']">
            {{ message.text }}
          </div>
        </div>
        <div class="chat-input-container">
          <label for="chat-bot-input" class="sr-only">Praat met de chatbot</label>
          <input v-model="chatbotInput" id="chat-bot-input" placeholder="Typ je bericht..."
            @keydown.enter.prevent="handleChatBotInput" />
          <button @click="handleChatBotInput" class="send-button">
            Verstuur
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent, onMounted, ref, Ref } from "vue";
import { useRoute } from "vue-router";
import ConnectionLine from "../components/ConnectionLine.vue";
import Dashboard from "../components/Dashboard.vue";
import GameSideBar from "../components/GameSideBar.vue";
import House from "../components/House.vue";
import NavigateButton from "../components/NavigateButton.vue";
import Notification from "../components/Notification.vue";
import PopupComponent from "../components/PopupComponent.vue";
import Transformer from "../components/Transformer.vue";
import { PopupProperties } from "../objects/PopupProperties";
import { gameLevelService } from "../services/game/GameLevelService";
import { pythonService } from "../services/PythonService";
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
    let gameId = Number(route.params.levelNmr);

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

    const solarPanelCost = ref(0);
    const batteryCost = ref(0);

    const popupProperties = ref<PopupProperties | undefined>(undefined);
    const isPopupOpen = ref(false);

    const notificationStatus = ref(false);
    const notificationMessage = ref("");

    const infoBoxVisible = ref(false);
    const infoBoxContents = ref("");
    const chatbotInput = ref("");
    const chatWindowOpen = ref(false);
    const messages = ref([
      { text: 'Welkom bij de chatbot!', sender: 'bot' }
    ]);
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

    const toggleChatWindow = async () => {
      chatWindowOpen.value = !chatWindowOpen.value;
    };

    const handleChatBotInput = async () => {
      const data = {
        transformers: transformers.value.map((transformer) => ({
          id: transformer.id,
          batteries: transformer.batteries.amount,
          houses: transformer.houses
        })),
        inputMessage: chatbotInput.value,
        dashboard: dashboardData.value,
        location_request: "level"
      };

      messages.value.push({
        text: chatbotInput.value,
        sender: 'user'
      });

      chatbotInput.value = '';

      await pythonService.fetchMessage(data).then((response) => {
        messages.value.push({
          text: `${response.response}`,
          sender: 'bot'
        });
      }).catch((error) => {
        console.error(error);
      });
    };

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
      const factorCorrection = 0.15;
      const baseWidth = 1920;
      const baseHeight = 1080;

      const viewportWidth = window.innerWidth;
      const viewportHeight = window.innerHeight;

      const widthFactor = viewportWidth / baseWidth;
      const heightFactor = viewportHeight / baseHeight;

      const factor = Math.max(widthFactor, heightFactor);

      return factor - factorCorrection;
    };

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
      popupProperties.value = new PopupProperties(house, solarPanelCost.value, batteryCost.value);
    };

    const showTransformerDetails = (transformer: transformer) => {
      isPopupOpen.value = true;
      popupProperties.value = new PopupProperties(transformer, solarPanelCost.value, batteryCost.value);
    };

    const handleDragItem = (itemType: string) => {
      // Optional: Add UI feedback or prepare state for dragging
      console.log(`Dragging item: ${itemType}`);
    };

    const handleDropItem = async (itemType: string, house: house) => {
      const coinsUsed = dashboardData.value.coinsUsed;
      const maxCoins = dashboardData.value.maxCoins;
      const itemCost = itemType === 'solarPanels' ? solarPanelCost.value : batteryCost.value;

      if (coinsUsed + itemCost > maxCoins) {
        notificationStatus.value = true;
        notificationMessage.value = "Niet genoeg coins!";
        return;
      }

      if (itemType === 'solarPanels') {
        house.solarpanels += 1;
      } else if (itemType === 'batteries') {
        house.batteries.amount += 1;
      } else if (itemType === 'electricCar') {
        house.hasElectricVehicle = true;
      } else if (itemType === 'heatPump') {
        house.hasHeatpump = true;
      }

      await submitChanges();
    };

    const handleRemoveItem = async ({ itemType, index }: { itemType: string; index?: number }, house: house) => {
      if (itemType === 'solarPanels' && house.solarpanels > 0 && index !== undefined) {
        house.solarpanels = Math.max(0, house.solarpanels - 1);
      } else if (itemType === 'batteries' && house.batteries.amount > 0 && index !== undefined) {
        house.batteries.amount = Math.max(0, house.batteries.amount - 1);
      } else if (itemType === 'electricCar') {
        house.hasElectricVehicle = false;
      } else if (itemType === 'heatPump') {
        house.hasHeatpump = false;
      } else {
        console.error("Unknown item type or invalid removal:", itemType);
        return;
      }

      await submitChanges();
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
      const greenProducedEnergyPercentage = totalProduction === 0 ? 0 : (totalGreenProduction / totalProduction) * 100;

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
        const data: any = {
          transformers: transformers.value.map((transformer) => ({
            id: transformer.id,
            batteries: transformer.batteries.amount,
            houses: transformer.houses.map((house) => ({
              id: house.id,
              batteries: house.batteries.amount,
              solarpanels: house.solarpanels,
              hasElectricVehicle: house.hasElectricVehicle,
              hasHeatpump: house.hasHeatpump,
            })),
          }))
        };
        const response = await gameLevelService.fetchUpdateLevel(gameId, data);
        response.hours[response.hours.length - 1].transformers.forEach((transformer: any) => {
          transformer.houses.sort((a: any, b: any) => a.id - b.id);
        });
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
        notificationStatus.value = true;
        notificationMessage.value = "Fout bij het opslaan van wijzigingen.";
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
        const data = await gameLevelService.fetchStartLevel(gameId);
        gameId = data.id;
        solarPanelCost.value = data.cost.solarPanelCost;
        batteryCost.value = data.cost.batteryCost;
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

    const coordinatesWithMargin = (x1: number, y1: number, x2: number, y2: number, margin: number) => {
      const dx = x2 - x1;
      const dy = y2 - y1;
      const length = Math.sqrt(dx * dx + dy * dy);
      if (length === 0) return { x1, y1, x2, y2 };
      const unitX = dx / length;
      const unitY = dy / length;
      return {
        x1: x1 + unitX * margin,
        y1: y1 + unitY * margin,
        x2: x2 - unitX * margin,
        y2: y2 - unitY * margin,
      };
    };

    const navigateBackUrl = computed(() => {
      const referral = route.query.referral as string;
      return referral ? `/${referral}` : "/levelSelect";
    });

    return {
      messages,
      chatWindowOpen,
      toggleChatWindow,
      chatbotInput,
      handleChatBotInput,
      gameCanvas,
      solarPanelCost,
      batteryCost,
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
      handleDragItem,
      handleDropItem,
      handleRemoveItem,
      submitChanges,
      infoBoxVisible,
      infoBoxContents,
      infoBoxStyle,
      showInfoBox,
      hideInfoBox,
      notificationStatus,
      notificationMessage,
      coordinatesWithMargin,
      navigateBackUrl,
    };
  },
});
</script>

<style scoped>
.chat-toggle-button {
  position: absolute;
  right: calc(1% + 250px);
  margin-top: -5px;
  border: none;
  cursor: pointer;
  z-index: 1000;
}

.chat-toggle-button img {
  width: 120px;
}

.chat-window {
  position: absolute;
  right: calc(1% + 250px);
  top: 57px;
  width: 400px;
  height: 400px;
  background: linear-gradient(rgba(120, 120, 120, 0.85), rgba(120, 120, 120, 0.85));
  border: 2px solid #4a4a4a;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.chat-messages {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}

.message {
  max-width: 70%;
  padding: 8px 12px;
  border-radius: 12px;
  font-size: 14px;
  line-height: 1.4;
  margin: 10px 0px 10px;
}

.user-message {
  background: linear-gradient(rgba(82, 82, 82, 0.85), rgba(82, 82, 82, 0.85));
  margin-left: auto;
  border-bottom-right-radius: 4px;
}

.bot-message {
  background-color: #8a8a8a;
  align-self: flex-start;
  border-bottom-left-radius: 4px;
}

.chat-input-container {
  display: flex;
  align-items: center;
  padding: 10px;
  background: linear-gradient(rgba(120, 120, 120, 0.85), rgba(120, 120, 120, 0.85));
  border-top: 2px solid #4a4a4a;
}

.chat-input-container input {
  flex: 1;
  padding: 8px;
  border: 2px solid #4a4a4a;
  border-radius: 4px;
  margin-right: 0px;
}

.chat-input-container input::placeholder {
  color: #FFF;
}

.send-button {
  padding: 8px 16px;
  background: linear-gradient(rgba(120, 120, 120, 0.85), rgba(120, 120, 120, 0.85));
  color: white;
  border: 2px solid #4a4a4a;
  cursor: pointer;
}

.send-button:hover {
  background-color: #218838;
}

.sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}

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
  background: linear-gradient(rgba(75, 74, 74, 0.282)), url("/background.png");
  background-size: cover, cover;
  background-blend-mode: normal;
  overflow-x: auto;
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

#navigate-button img {
  z-index: 1000;
  width: 110px;
  position: absolute;
  margin: 0 10px 10px 10px;
  cursor: pointer;
}

.infoBox {
  font-size: 14px;
  z-index: 1000;
  white-space: nowrap;
}
</style>