<template>
  <v-dialog v-model="isOpen" max-width="500px">
    <v-card class="popup-card">
      <!-- Titelbalk -->
      <v-card-title class="popup-title text-h5">
        <v-row class="justify-space-between">
          <v-col class="d-flex align-center">
            <v-icon icon="mdi-information" size="24" class="mr-2"></v-icon>
            <h1 class="header-title">{{ title }}</h1>
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
          <div v-if="type === 'huis'" class="section cost-section mt-4">
            <v-row>
              <v-col cols="6"><strong>Totale stroom kosten:</strong></v-col>
              <v-col cols="6" class="text-end highlight">€ {{ formattedTotalPowerCost }}</v-col>
            </v-row>
          </div>

          <!-- Extra Details voor Huis -->
          <div v-if="type === 'huis'" class="section details-section mt-4">
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
          <div v-if="type !== 'transformator'" class="section solar-section mt-4">
            <v-row class="align-center">
              <v-col cols="4">
                <v-btn class="popup-btn" icon @click="decreaseValue('solarPanels')">➖</v-btn>
                <v-btn class="popup-btn" icon @click="increaseValue('solarPanels')">➕</v-btn>
              </v-col>
              <v-col cols="6" class="text-center"><strong>Zonnepanelen</strong> (💰{{ solarPanelCost }})</v-col>
              <v-col cols="2" class="text-end highlight">{{ solarPanels }}</v-col>
            </v-row>
          </div>

          <!-- Accu's -->
          <div class="section battery-section mt-4">
            <v-row class="align-center">
              <v-col cols="4">
                <v-btn class="popup-btn" icon @click="decreaseValue('batteries')">➖</v-btn>
                <v-btn class="popup-btn" icon @click="increaseValue('batteries')">➕</v-btn>
              </v-col>
              <v-col cols="6" class="text-center"><strong>Accu’s</strong> (💰{{ batteries.cost }})</v-col>
              <v-col cols="2" class="text-end highlight">{{ batteries.amount }}</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Totale accu lading:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ formattedBatteryCharge }} kWh</v-col>
            </v-row>
          </div>
        </v-container>
      </v-card-text>

      <div class="card-footer">
        <v-col cols="12" class="d-flex justify-center">
          <v-btn class="header-btn" color="red" @click="closeDialog">
            Annuleren
            <v-icon icon="mdi-cancel" end></v-icon>
          </v-btn>
          <v-btn class="header-btn" color="green" @click="submitChanges">
            Toepassen
            <v-icon icon="mdi-checkbox-marked-circle" end></v-icon>
          </v-btn>
        </v-col>
      </div>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, PropType, ref } from "vue";
import { PopupProperties } from "../objects/PopupProperties";
import { batteries, house, transformer } from "../types";

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
    const title = ref("");
    const type = ref("");
    const energyProduction = ref(0);
    const energyConsumption = ref(0);
    const batteries = ref<batteries>({ amount: 0, totalCharge: 0, cost: 0 });
    const heatPump = ref(false);
    const electricVehicle = ref(false);
    const solarPanels = ref(0);
    const totalPowerCost = ref(0);
    const solarPanelCost = ref(0);

    const energyDifference = computed(() => energyProduction.value - energyConsumption.value);

    const formattedEnergyProduction = computed(() => energyProduction.value.toFixed(2));
    const formattedEnergyConsumption = computed(() => energyConsumption.value.toFixed(2));
    const formattedBatteryCharge = computed(() => (batteries.value.totalCharge ?? 0).toFixed(2));
    const formattedTotalPowerCost = computed(() => totalPowerCost.value.toFixed(4));

    const heatPumpDisplay = computed(() => (heatPump ? "✔️" : "❌"));
    const electricVehicleDisplay = computed(() => (electricVehicle ? "✔️" : "❌"));

    const energyProductionLabel = computed(() =>
      type.value === "transformator" ? "Energie om terug te leveren aan net" : "Energie productie"
    );
    const energyConsumptionLabel = computed(() =>
      type.value === "transformator" ? "Energie aan huizen vanuit net" : "Energie consumptie"
    );

    let popupTitleValue = parseInt(title.value.split(" ")[1]);
    let solarPanelsAmount: number | null = null;
    let batteriesAmount: number | null = null;

    const findHouse = (houseId: number) => {
      return transformers.value.flatMap((t: transformer) => t.houses).find((h: house) => h.id === houseId);
    };

    const findTransformer = (transformerId: number) => {
      return transformers.value.find((t: transformer) => t.id === transformerId);
    };

    const increaseValue = (property: string) => {
      popupTitleValue = parseInt(title.value.split(" ")[1]);
      const house = findHouse(popupTitleValue);
      const transformer = findTransformer(popupTitleValue);
      correctLocalSavedNumbers();

      if (solarPanelsAmount === null || batteriesAmount === null) {
        return;
      }

      switch (property) {
        case "solarPanels":
          if (house && solarPanelsAmount < house.maxSolarPanelCount) {
            solarPanelsAmount += 1;
            solarPanels.value += 1;
          }
          break;

        case "batteries":
          switch (type.value) {
            case "huis":
              if (house && batteriesAmount < house.maxBatteryCount) {
                batteriesAmount += 1;
                batteries.value.amount += 1;
              }
              break;

            case "transformator":
              if (transformer && batteriesAmount < transformer.maxBatteryCount) {
                batteriesAmount += 1;
                batteries.value.amount += 1;
              }
              break;

            default:
              break;
          }
          break;

        default:
          break;
      }
    };

    const correctLocalSavedNumbers = () => {
      popupTitleValue = parseInt(title.value.split(" ")[1]);
      const house = findHouse(popupTitleValue);
      const transformer = findTransformer(popupTitleValue);
      solarPanelsAmount = solarPanelsAmount === null ? house?.solarpanels ?? 0 : solarPanelsAmount;

      type.value == "huis" ?
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
        solarPanels.value -= 1;
      } else if (property === "batteries" && batteriesAmount > 0) {
        batteriesAmount -= 1;
        batteries.value.amount -= 1;
      }
    };

    const closeDialog = () => {
      emit("update:isOpen", false);
    };

    const submitChanges = () => {
      if (type.value === "huis") {
        const house = findHouse(popupTitleValue);
        if (house) {
          house.solarpanels = solarPanels.value;
          house.batteries.amount = batteries.value.amount;
        }
      } else if (type.value === "transformator") {
        const transformer = findTransformer(popupTitleValue);
        if (transformer) {
          transformer.batteries.amount = batteries.value.amount;
        }
      }
      emit("submitChanges");
    };


    const createPopup = (properties: PopupProperties | undefined) => {
      if (!properties) {
        return;
      }
      title.value = properties.title;
      type.value = properties.type;
      energyProduction.value = properties.energyProduction;
      energyConsumption.value = properties.energyConsumption;
      heatPump.value = properties.heatPump ?? false;
      electricVehicle.value = properties.electricVehicle ?? false;
      solarPanels.value = properties.solarPanels ?? 0;
      batteries.value = properties.batteries;
      totalPowerCost.value = properties.totalPowerCost ?? 0;
      solarPanelCost.value = properties.solarPanelCost ?? 0;
      batteries.value.cost = properties.batteries?.cost ?? 0;

    };

    onMounted(() => {
      createPopup(props.popupProperties);
    });

    return {
      title,
      type,
      energyProduction,
      energyConsumption,
      batteries,
      heatPump,
      electricVehicle,
      solarPanels,
      totalPowerCost,
      solarPanelCost,
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
