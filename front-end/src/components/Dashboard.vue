<template>
  <div class="dashboard-container">
    <div class="slot">
      <div>
        Huidige munten gebruik: {{ coinsUsed }}
      </div>
      <div class="underline">
        Max. aantal munten: {{ maxCoins }}
      </div>
    </div>
    <div class="slot">
      <div>
        Huidige CO2-uitstoot: {{ currentCO2.toFixed(2) }} kg
      </div>
      <div class="underline">
        Max. CO2-uitstoot: {{ MaxCO2.toFixed(2) }} kg
      </div>
    </div>
    <div class="slot">
      Totale energieconsumptie: {{ totalEnergyConsumption.toFixed(2) }} kWh
    </div>
    <div class="slot">
      Groene geproduceerde energiepercentage: {{ greenProducedEnergyPercentage.toFixed(2) }}%
    </div>
    <div class="slot">
      Start- en eindtijd: {{ objectiveStartTime }}h - {{ objectiveEndTime }}h
    </div>
    <div class="slot">
      Seizoen: {{ season }}
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'Dashboard',
  props: {
    coinsUsed: {
      type: Number,
      required: true,
    },
    maxCoins: {
      type: Number,
      required: true,
    },
    currentCO2: {
      type: Number,
      required: true,
    },
    MaxCO2: {
      type: Number,
      required: true,
    },
    totalEnergyConsumption: {
      type: Number,
      required: true,
    },
    greenProducedEnergyPercentage: {
      type: Number,
      required: true,
    },
    objectiveStartTime: {
      type: Number,
      required: true,
    },
    objectiveEndTime: {
      type: Number,
      required: true,
    },
    season: {
      type: String,
      required: true,
    },
  },
});
</script>

<style scoped>
.dashboard-container {
  height: 20vh;
  display: flex;
  flex-direction: row;
  background-color: rgb(82, 81, 81);
}

.slot {
  height: 100%;
  width: 100%;
  text-align: center;
  align-items: center;
  font-weight: bold;
  color: white;
}

.underline {
  text-decoration: underline;
}
</style>