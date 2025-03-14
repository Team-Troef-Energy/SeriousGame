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
              <v-col cols="6"
                ><strong>{{ energyProductionLabel }}:</strong></v-col
              >
              <v-col cols="6" class="text-end highlight">{{ formattedEnergyProduction }} kWh</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"
                ><strong>{{ energyConsumptionLabel }}:</strong></v-col
              >
              <v-col cols="6" class="text-end highlight"
                >{{ formattedEnergyConsumption }} kWh</v-col
              >
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Verschil:</strong></v-col>
              <v-col
                cols="6"
                class="text-end"
                :class="energyDifference < 0 ? 'negative' : 'highlight'">
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
              <v-col cols="6" class="text-center"
                ><strong>Zonnepanelen</strong> (💰{{ solarPanelCost }})</v-col
              >
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
              <v-col cols="6" class="text-center"
                ><strong>Accu’s</strong> (💰{{ batteries.cost }})</v-col
              >
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
import { computed, defineComponent, PropType } from "vue";
import { batteries } from "../types";

export default defineComponent({
  name: "PopupComponent",
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      required: true,
    },
    energyProduction: {
      type: Number,
      required: true,
      default: 0,
    },
    energyConsumption: {
      type: Number,
      required: true,
      default: 0,
    },
    heatPump: {
      type: Boolean,
      default: false,
    },
    electricVehicle: {
      type: Boolean,
      default: false,
    },
    solarPanels: {
      type: Number,
      default: 0,
    },
    batteries: {
      type: Object as PropType<batteries>,
      default: 0,
    },
    totalPowerCost: {
      type: Number,
      default: 0,
    },
    solarPanelCost: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const energyDifference = computed(() => props.energyProduction - props.energyConsumption);

    const formattedEnergyProduction = computed(() => props.energyProduction.toFixed(2));
    const formattedEnergyConsumption = computed(() => props.energyConsumption.toFixed(2));
    const formattedBatteryCharge = computed(() => (props.batteries.totalCharge ?? 0).toFixed(2));
    const formattedTotalPowerCost = computed(() => props.totalPowerCost.toFixed(4));

    const heatPumpDisplay = computed(() => (props.heatPump ? "✔️" : "❌"));
    const electricVehicleDisplay = computed(() => (props.electricVehicle ? "✔️" : "❌"));

    const energyProductionLabel = computed(() =>
      props.type === "transformator" ? "Energie om terug te leveren aan net" : "Energie productie"
    );
    const energyConsumptionLabel = computed(() =>
      props.type === "transformator" ? "Energie aan huizen vanuit net" : "Energie consumptie"
    );

    const increaseValue = (property: string) => {
      emit("increase", property);
    };

    const decreaseValue = (property: string) => {
      emit("decrease", property);
    };

    const closeDialog = () => {
      emit("update:isOpen", false);
      emit("cancelChanges");
    };

    const submitChanges = () => {
      emit("submitChanges");
    };

    return {
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
  background-color: #f8f9fa; /* Neutrale achtergrond */
  color: #333;
  border-radius: 12px;
}

.popup-title {
  background-color: #0077b6; /* Donkerblauwe titelbalk */
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
  background-color: #e3f2fd; /* Lichtblauw */
}

.cost-section {
  background-color: #f5e7f6;
}

.details-section {
  background-color: #fff3e0; /* Lichtoranje */
}

.solar-section {
  background-color: #f1f8e9; /* Lichtgroen */
}

.battery-section {
  background-color: #ede7f6; /* Lichtpaars */
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
  color: #0077b6; /* Positief: Blauw */
}

.negative {
  color: #d32f2f; /* Negatief: Rood */
}
</style>
