<template>
  <v-dialog v-model="isOpen" max-width="500px">
    <v-card class="popup-card">
      <!-- Titelbalk -->
      <v-card-title class="popup-title text-h5 d-flex justify-space-between">
        {{ title }}
        <v-btn
          icon
          class="close-btn outlined"
          style="background-color: white"
          @click="closeDialog"
          >❌</v-btn
        >
      </v-card-title>

      <!-- Inhoud -->
      <v-card-text>
        <v-container fluid>
          <!-- Energie Overzicht -->
          <div class="section energy-section">
            <v-row>
              <v-col cols="6"><strong>Energie productie:</strong></v-col>
              <v-col cols="6" class="text-end highlight"
                >{{ formattedEnergyProduction }} kWh</v-col
              >
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Energie consumptie:</strong></v-col>
              <v-col cols="6" class="text-end highlight"
                >{{ formattedEnergyConsumption }} kWh</v-col
              >
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Verschil:</strong></v-col>
              <v-col
                cols="6"
                class="text-end"
                :class="energyDifference < 0 ? 'negative' : 'highlight'"
              >
                {{ energyDifference.toFixed(2) }} kWh
              </v-col>
            </v-row>
          </div>

          <!-- Extra Details voor Huis -->
          <div v-if="type === 'huis'" class="section details-section mt-4">
            <v-row>
              <v-col cols="6"><strong>Aantal warmtepompen:</strong></v-col>
              <v-col cols="6" class="text-end highlight">{{ heatPumps }}</v-col>
            </v-row>
            <v-row>
              <v-col cols="6"><strong>Elektrische auto’s:</strong></v-col>
              <v-col cols="6" class="text-end highlight"
                >{{ electricCars }}</v-col
              >
            </v-row>
          </div>

          <!-- Zonnepanelen -->
          <div v-if="type !== 'transformator'" class="section solar-section mt-4">
            <v-row class="align-center">
              <v-col cols="4">
                <v-btn
                  class="popup-btn"
                  icon
                  @click="decreaseValue('solarPanels')"
                  >➖</v-btn
                >
                <v-btn
                  class="popup-btn"
                  icon
                  @click="increaseValue('solarPanels')"
                  >➕</v-btn
                >
              </v-col>
              <v-col cols="6" class="text-center"
                ><strong>Zonnepanelen</strong></v-col
              >
              <v-col cols="2" class="text-end highlight"
                >{{ solarPanels }}</v-col
              >
            </v-row>
          </div>

          <!-- Accu's -->
          <div class="section battery-section mt-4">
            <v-row class="align-center">
              <v-col cols="4">
                <v-btn
                  class="popup-btn"
                  icon
                  @click="decreaseValue('batteries')"
                  >➖</v-btn
                >
                <v-btn
                  class="popup-btn"
                  icon
                  @click="increaseValue('batteries')"
                  >➕</v-btn
                >
              </v-col>
              <v-col cols="6" class="text-center"
                ><strong>Accu’s</strong></v-col
              >
              <v-col cols="2" class="text-end highlight">{{ batteries }}</v-col>
            </v-row>
          </div>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
  import { defineComponent, computed } from 'vue';

  export default defineComponent({
    name: 'PopupComponent',
    props: {
      isOpen: {
        type: Boolean,
        required: true
      },
      title: {
        type: String,
        required: true
      },
      type: {
        type: String,
        required: true
      },
      energyProduction: {
        type: Number,
        required: true,
        default: 0
      },
      energyConsumption: {
        type: Number,
        required: true,
        default: 0
      },
      heatPumps: {
        type: Number,
        default: 0
      },
      electricCars: {
        type: Number,
        default: 0
      },
      solarPanels: {
        type: Number,
        default: 0
      },
      batteries: {
        type: Number,
        default: 0
      }
    },
    setup(props, { emit }) {
      // Computed properties
      const energyDifference = computed(
        () => props.energyProduction - props.energyConsumption
      );

      const formattedEnergyProduction = computed(() => {
        return props.energyProduction.toFixed(2);
      });

      const formattedEnergyConsumption = computed(() => {
        return props.energyConsumption.toFixed(2);
      });
      // Methods
      const increaseValue = (property: string) => {
        if (property === 'solarPanels') {
          emit('update:solarPanels', props.solarPanels + 1);
        } else if (property === 'batteries') {
          emit('update:batteries', props.batteries + 1);
        }
      };

      const decreaseValue = (property: string) => {
        if (property === 'solarPanels' && props.solarPanels > 0) {
          emit('update:solarPanels', props.solarPanels - 1);
        } else if (property === 'batteries' && props.batteries > 0) {
          emit('update:batteries', props.batteries - 1);
        }
      };

      const closeDialog = () => {
        emit('update:isOpen', false);
      };

      return {
        energyDifference,
        formattedEnergyProduction,
        formattedEnergyConsumption,
        increaseValue,
        decreaseValue,
        closeDialog,
      };
    },
  });
</script>

<style scoped>
  .popup-card {
    background-color: #f8f9fa; /* Neutrale achtergrond */
    color: #333;
    border-radius: 12px;
  }

  .popup-title {
    background-color: #0077b6; /* Donkerblauwe titelbalk */
    color: #fff;
  }

  .close-btn {
    background-color: #0077b6;
    color: #fff;
  }

  .section {
    padding: 12px;
    border-radius: 8px;
  }

  .energy-section {
    background-color: #e3f2fd; /* Lichtblauw */
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