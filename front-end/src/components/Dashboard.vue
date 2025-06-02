<template>
  <div class="dashboard-container">
    <div class="slot">
      <h3 class="slot-title">Beschikbare munten:</h3>
      <div class="slot-content">
        <img src="/coin.png" class="coin" alt="Coin">
        <span>{{ coinsUsed }}/{{ maxCoins }}</span>
      </div>
    </div>
    <div class="slot">
      <div>
        Huidige CO2-uitstoot: {{ currentCO2.toFixed(2) }} kg
      </div>
      <div>
        Max. CO2-uitstoot: {{ maxCO2.toFixed(2) }} kg
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
    maxCO2: {
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
  width: 100%;
  background-color: #39210a67;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 4px 10px;
}

.slot {
  letter-spacing: -.03px;
  color: white;
  font-size: 12px;
  font-family: "Press Start 2P";
  display: flex;
  align-items: center;
  flex-direction: column;
}

.slot-content {
  display: flex;
  align-items: center;
  gap: 6px;
}

.coin {
  width: 20px;
  margin-bottom: 1px;
}
</style>