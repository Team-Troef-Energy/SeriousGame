<template>
  <div class="house">
    <img class="house-image" src="/house.png" alt="House" />
    <div class="add-ons">
      <img
        v-for="index in solarpanels"
        :key="'solar-' + index"
        src="/solar-panels.png"
        alt="Solar Panel"
        class="solar-panel"
      />
      <img
        v-for="index in batteries.amount"
        :key="'battery-' + index"
        src="/batteries.png"
        alt="Battery"
        class="battery"
      />
    </div>
    <img
      v-if="hasElectricCar"
      class="electric-car"
      src="/electric-car.png"
      alt="Electric Car"
    />
    <img
      v-if="hasHeatPump"
      class="heat-pump"
      src="/heat-pump.png"
      alt="Heat Pump"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { Battery } from '../types';

export default defineComponent({
  props: {
    hasSolarPanels: {
      type: Boolean,
      default: false,
    },
    hasElectricCar: {
      type: Boolean,
      default: false,
    },
    hasBatteries: {
      type: Boolean,
      default: false,
    },
    hasHeatPump: {
      type: Boolean,
      default: false,
    },
    solarpanels: {
      type: Number,
      default: 0,
    },
    batteries: {
      type: Object as PropType<Battery>,
      default: () => ({ amount: 0 }),
    },
  },
});
</script>

<style scoped>
.house {
  position: relative;
  width: 13rem;
  height: 10rem;
}

.house-image {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1;
}

.add-ons {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  flex-wrap: wrap;
  z-index: 2;
}

.solar-panel,
.battery {
  width: 2rem;
  height: 2rem;
}

.solar-panel {
  transform: rotate(27deg);
}

.electric-car {
  position: absolute;
  top: 85%;
  left: 50%;
  width: 4rem;
  height: 2rem;
  transform: translate(-50%, -50%);
  z-index: 2;
}

.heat-pump {
  position: absolute;
  top: 63%;
  left: 72%;
  width: 2.5rem;
  height: 2.5rem;
  transform: rotate(2deg) translate(-50%, -50%);
  z-index: 2;
}
</style>