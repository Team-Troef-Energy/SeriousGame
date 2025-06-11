<template>
  <v-dialog v-model="isOpen" max-width="500px">
    <v-card class="popup-card">
      <!-- Titelbalk -->
      <v-card-title class="popup-title text-h5">
        <v-row class="justify-space-between">
          <v-col class="d-flex align-center">
            <v-icon icon="mdi-information" size="24" class="mr-2"></v-icon>
            <h1 class="header-title">{{ properties.title }}</h1>
          </v-col>
          <v-col class="d-flex justify-end">
            <v-btn class="header-btn" variant="tonal" @click="closeDialog">
              Sluiten
              <v-icon icon="mdi-close" end></v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-card-title>

      <!-- Inhoud -->
      <v-card-text class="main-contents">
        <v-container fluid>
          <!-- Energie Overzicht -->
          <div class="section energy-section">
            <v-row>
              <v-col cols="6"><strong>{{ energyProductionLabel }}:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ formattedEnergyProduction }} kWh</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"><strong>{{ energyConsumptionLabel }}:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ formattedEnergyConsumption }} kWh</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Verschil:</strong></v-col>
              <v-col cols="6" class="text-end" :class="energyDifference < 0 ? 'negative' : 'highlight'">
                {{ energyDifference.toFixed(2) }} kWh
              </v-col>
            </v-row>
          </div>

          <!-- Kosten details voor huis -->
          <div v-if="properties.type === 'huis'" class="section cost-section mt-4">
            <v-row>
              <v-col cols="6"><strong>Totale stroom kosten:</strong></v-col>
              <v-col cols="6" class="text-end highlight">€ {{ formattedTotalPowerCost }}</v-col>
            </v-row>
          </div>

          <!-- Extra Details voor Huis -->
          <div v-if="properties.type === 'huis'" class="section details-section mt-4">
            <v-row>
              <v-col cols="6"><strong>Warmtepomp:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ heatPumpDisplay }}</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Elektrische auto:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ electricVehicleDisplay }}</v-col>
            </v-row>
          </div>

          <!-- Zonnepanelen -->
          <div v-if="properties.type !== 'transformator'" class="section solar-section mt-4">
            <v-row class="align-center">
              <v-col cols="6" class="text-left"><strong>Zonnepanelen</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ properties.solarPanels }}</v-col>
            </v-row>
          </div>

          <!-- Accu's -->
          <div class="section battery-section mt-4">
            <v-row class="align-center">
              <v-col cols="6" class="text-left"><strong>Accu's</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ properties.batteries.amount }}</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Totale accu lading:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ formattedBatteryCharge }} kWh</v-col>
            </v-row>
          </div>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, PropType, ref } from "vue";
import { PopupProperties } from "../objects/PopupProperties";
import { house, transformer } from "../types";

export default defineComponent({
  name: "PopupComponent",
  props: {
    isOpen: {
      type: Boolean,
    },
    popupProperties: {
      type: Object as PropType<PopupProperties>,
      required: false,
    },
    transformers: {
      type: Array as PropType<transformer[]>,
      required: true,
    },
  },
  setup(props, { emit }) {
    const transformers = ref<transformer[]>(props.transformers);
    const properties = ref<PopupProperties>({
      title: "", type: "", energyProduction: 0, energyConsumption: 0, heatPump: false, electricVehicle: false, solarPanels: 0, batteries: { amount: 0, totalCharge: 0, cost: 0 }, totalPowerCost: 0, solarPanelCost: 0,
      isOpen: false
    });

    const energyDifference = computed(() => properties.value.energyProduction - properties.value.energyConsumption);

    const formattedEnergyProduction = computed(() => properties.value.energyProduction.toFixed(2));
    const formattedEnergyConsumption = computed(() => properties.value.energyConsumption.toFixed(2));
    const formattedBatteryCharge = computed(() => (properties.value.batteries.totalCharge ?? 0).toFixed(2));
    const formattedTotalPowerCost = computed(() => (properties.value.totalPowerCost ?? 0).toFixed(4));

    const heatPumpDisplay = computed(() => (properties.value.heatPump ? "✔️" : "❌"));
    const electricVehicleDisplay = computed(() => (properties.value.electricVehicle ? "✔️" : "❌"));

    const energyProductionLabel = computed(() =>
      properties.value.type === "transformator" ? "Energie om terug te leveren aan net" : "Energie productie"
    );
    const energyConsumptionLabel = computed(() =>
      properties.value.type === "transformator" ? "Energie aan huizen vanuit net" : "Energie consumptie"
    );

    let popupTitleValue = parseInt(properties.value.title.split(" ")[1]);
    let solarPanelsAmount: number | null = null;
    let batteriesAmount: number | null = null;

    const findHouse = (houseId: number) => {
      return transformers.value
        .map((t: transformer) => t.houses)  // Extract houses arrays
        .reduce((acc, houses) => acc.concat(houses), []) // Flatten them
        .find((h: house) => h.id === houseId);

    };

    const findTransformer = (transformerId: number) => {
      return transformers.value.find((t: transformer) => t.id === transformerId);
    };

    const increaseValue = (property: string) => {
      popupTitleValue = parseInt(properties.value.title.split(" ")[1]);
      const house = findHouse(popupTitleValue);
      const transformer = findTransformer(popupTitleValue);

      correctLocalSavedNumbers();

      if (solarPanelsAmount === null || batteriesAmount === null) {
        return;
      }

      const canIncreaseSolarPanels = (house: house) => house && solarPanelsAmount !== null && solarPanelsAmount < house.maxSolarPanelCount;
      const canIncreaseBatteries = (maxCount: number) => batteriesAmount !== null && batteriesAmount < maxCount;

      if (property === "solarPanels" && house && canIncreaseSolarPanels(house)) {
        solarPanelsAmount += 1;
        properties.value.solarPanels = (properties.value.solarPanels ?? 0) + 1;
        return;
      }

      if (property === "batteries") {
        if (properties.value.type === "huis" && house && canIncreaseBatteries(house.maxBatteryCount)) {
          batteriesAmount += 1;
          properties.value.batteries.amount += 1;
        } else if (properties.value.type === "transformator" && transformer && canIncreaseBatteries(transformer.maxBatteryCount)) {
          batteriesAmount += 1;
          properties.value.batteries.amount += 1;
        }
      }
    };

    const correctLocalSavedNumbers = () => {
      popupTitleValue = parseInt(properties.value.title.split(" ")[1]);
      const house = findHouse(popupTitleValue);
      const transformer = findTransformer(popupTitleValue);
      solarPanelsAmount = solarPanelsAmount === null ? house?.solarpanels ?? 0 : solarPanelsAmount;

      properties.value.type == "huis" ?
        batteriesAmount = batteriesAmount === null ? house?.batteries.amount ?? 0 : batteriesAmount :
        batteriesAmount = batteriesAmount === null ? transformer?.batteries.amount ?? 0 : batteriesAmount;
    }

    const decreaseValue = (property: string) => {
      correctLocalSavedNumbers();

      if (solarPanelsAmount === null || batteriesAmount === null) {
        return;
      }

      if (property === "solarPanels" && solarPanelsAmount > 0) {
        solarPanelsAmount -= 1;
        properties.value.solarPanels = (properties.value.solarPanels ?? 0) - 1;
      } else if (property === "batteries" && batteriesAmount > 0) {
        batteriesAmount -= 1;
        properties.value.batteries.amount -= 1;
      }
    };

    const closeDialog = () => {
      emit("update:isOpen", false);
    };

    const submitChanges = () => {
      if (properties.value.type === "huis") {
        const house = findHouse(popupTitleValue);
        if (house) {
          house.solarpanels = properties.value.solarPanels ?? 0;
          house.batteries.amount = properties.value.batteries.amount;
        }
      } else if (properties.value.type === "transformator") {
        const transformer = findTransformer(popupTitleValue);
        if (transformer) {
          transformer.batteries.amount = properties.value.batteries.amount;
        }
      }
      emit("submitChanges");
    };


    const createPopup = (givenProperties: PopupProperties | undefined) => {
      if (!givenProperties) {
        return;
      }
      properties.value = JSON.parse(JSON.stringify(givenProperties));
    };

    onMounted(() => {
      createPopup(props.popupProperties);
    });

    return {
      properties,
      energyDifference,
      formattedEnergyProduction,
      formattedEnergyConsumption,
      formattedBatteryCharge,
      formattedTotalPowerCost,
      heatPumpDisplay,
      electricVehicleDisplay,
      energyConsumptionLabel,
      energyProductionLabel,
      increaseValue,
      decreaseValue,
      closeDialog,
      submitChanges,
    };
  },
});
</script>

<style scoped>
.header-container {
  display: flex;
  align-items: center;
  margin: 2px;
}

.header-title {
  font-size: 1.5rem;
  font-weight: 500;
  margin: 10px;
}

.popup-card {
  background-color: #f8f9fa;
  /* Neutrale achtergrond */
  color: #333;
  border-radius: 12px;
}

.popup-title {
  background-color: #0077b6;
  /* Donkerblauwe titelbalk */
  color: #fff;
}

.header-btn {
  color: #fff;
  padding: 8px 16px;
  border-radius: 5%;
  margin: 10px;
  width: auto;
  white-space: nowrap;
}

.section {
  padding: 12px;
  border-radius: 8px;
  margin: 0;
}

.main-contents {
  padding: 0;
}

.energy-section {
  background-color: #e3f2fd;
  /* Lichtblauw */
}

.cost-section {
  background-color: #f5e7f6;
}

.details-section {
  background-color: #fff3e0;
  /* Lichtoranje */
}

.solar-section {
  background-color: #f1f8e9;
  /* Lichtgroen */
}

.battery-section {
  background-color: #ede7f6;
  /* Lichtpaars */
}

.popup-btn {
  background-color: #f1f3f5;
  color: #0077b6;
  border-radius: 50%;
}

.popup-btn:hover {
  background-color: #0077b6;
  color: #fff;
}

.highlight {
  color: #0077b6;
  /* Positief: Blauw */
}

.negative {
  color: #d32f2f;
  /* Negatief: Rood */
}
</style>
