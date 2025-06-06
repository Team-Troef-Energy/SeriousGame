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
      CO2-uistoot: 
      <div class="slot-content">
        <img class="cloud-icon" src="/cloud.png" alt="Cloud">
        {{ currentCO2.toFixed(2) }} kg - {{ maxCO2.toFixed(2) }} kg
      </div>
    </div>
    <div class="slot">
      Totale energieconsumptie: 
      <div class="slot-content">
        <img class="energy-icon" src="/lightning.png" alt="Icon">
        {{ totalEnergyConsumption.toFixed(2) }} kWh
      </div>
    </div>
    <div class="slot">
      Groen geproduceerde energie: 
      <div class="slot-content">
        <img class="energy-icon" src="/green_electricity.png" alt="Icon">
        {{ greenProducedEnergyPercentage.toFixed(2) }}%
      </div>
    </div>
    <div class="slot">
      Start- en eindtijd:
      <div class="slot-content">
        <img src="/clock.png" alt="Clock" class="clock-icon">
        {{ objectiveStartTime }}:00 - {{ objectiveEndTime }}:00
      </div>
    </div>
    <div class="slot">
      Seizoen: 
      <div class="slot-content">
        <img class="season-icon" :src="`/${getSeasonIcon(season)}`" alt="Icon">
        {{ translateSeason(season) }}
      </div>
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
  methods: {
    translateSeason(season: string): string {
      switch (season.toLowerCase()) {
        case 'summer': return 'Zomer';
        case 'winter': return 'Winter';
        case 'spring': return 'Lente';
        case 'fall': return 'Herfst';
        default: return season;
      }
    },
    getSeasonIcon(season: string): string {
      switch (season.toLowerCase()) {
        case 'summer': return 'sun.png';
        case 'winter': return 'snowflake.png';
        case 'spring': return 'leaf.png';
        case 'fall': return 'orange_leaf.png';
        default: return season;
      }
    }
  }
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

.season-icon {
  width: 20px;
  height: 20px;
}

.cloud-icon {
  height: 20px;
  width: 22px;
}
  
.clock-icon {
  width: 20px;
  height: 20px;
}

.energy-icon {
  width: 24px;
  height: 24px;
}

.coin {
  width: 20px;
  margin-bottom: 1px;
}
</style>