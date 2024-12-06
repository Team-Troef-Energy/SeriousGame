<template>
  <v-dialog v-model="isOpen" max-width="500px">
    <v-card>
      <v-card-title class="text-h5 d-flex justify-space-between">
        {{ title }}
        <v-btn icon @click="closeDialog"><v-icon>mdi-close</v-icon></v-btn>
      </v-card-title>

      <v-card-text>
        <div>
          <p><strong>Energie productie:</strong> {{ energyProduction.toFixed(2) }} kWh</p>
          <p><strong>Energie consumptie:</strong> {{ energyConsumption.toFixed(2) }} kWh</p>
          <p><strong>Ontvangen van grid:</strong> {{ receivedFromGrid.toFixed(2) }} kWh</p>
        </div>

        <div v-if="type === 'huis'" class="mt-4">
          <p><strong>Aantal warmtepompen:</strong> {{ heatPumps }}</p>
          <p><strong>Elektrische auto’s:</strong> {{ electricCars }}</p>
        </div>

        <div v-if="type === 'huis'" class="mt-4">
          <v-btn icon @click="decreaseValue('solarPanels')">➖</v-btn>
          <span>Zonnepanelen: {{ solarPanels }}</span>
          <v-btn icon @click="increaseValue('solarPanels')">➕</v-btn>
        </div>

        <div class="mt-4">
          <v-btn icon @click="decreaseValue('batteries')">➖</v-btn>
          <span>Accu’s: {{ batteries }}</span>
          <v-btn icon @click="increaseValue('batteries')">➕</v-btn>
        </div>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="closeDialog">Sluiten</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { defineComponent, toRefs } from 'vue';

export default defineComponent({
  name: 'PopupComponent',
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
      validator: (value: string) => ['huis', 'transformator'].includes(value),
    },
    energyProduction: {
      type: Number,
      required: true,
    },
    energyConsumption: {
      type: Number,
      required: true,
    },
    receivedFromGrid: {
      type: Number,
      required: true,
    },
    heatPumps: {
      type: Number,
      default: 0,
    },
    electricCars: {
      type: Number,
      default: 0,
    },
    solarPanels: {
      type: Number,
      default: 0,
    },
    batteries: {
      type: Number,
      default: 0,
    },
  },
  emits: ['update:solarPanels', 'update:batteries', 'close'],
  setup(props, { emit }) {
    const { solarPanels, batteries } = toRefs(props);

    const increaseValue = (property: string) => {
      if (property === 'solarPanels') {
        emit('update:solarPanels', solarPanels.value + 1);
      } else if (property === 'batteries') {
        emit('update:batteries', batteries.value + 1);
      }
    };

    const decreaseValue = (property: string) => {
      if (property === 'solarPanels' && solarPanels.value > 0) {
        emit('update:solarPanels', solarPanels.value - 1);
      } else if (property === 'batteries' && batteries.value > 0) {
        emit('update:batteries', batteries.value - 1);
      }
    };

    const closeDialog = () => {
      emit('close');
    };

    return {
      ...toRefs(props),
      increaseValue,
      decreaseValue,
      closeDialog,
    };
  },
});
</script>

<style scoped>
.mt-4 {
  margin-top: 16px;
}
</style>
