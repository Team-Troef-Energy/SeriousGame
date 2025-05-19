<template>
  <div class="house">
    <img class="house" :src="'/house.png'" alt="House" />
    <img v-if="hasSolarPanels" class="solar-panels" src="/solar-panels.png" alt="Solar Panels" />
    <img v-if="hasElectricCar" class="electric-car" src="/electric-car.png" alt="Electric Car" />
    <img v-if="hasBatteries" class="batteries" src="/batteries.png" alt="Batteries" />
    <img v-if="hasHeatPump" class="heat-pump" src="/heat-pump.png" alt="Heat Pump" />
  </div>
</template>

<script lang="ts">
export default {
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
  },
};
</script>

<style scoped>
.house {
  width: 13rem;
  height: 10rem;
}

img .house {
  width: 100%;
  height: 100%;
}

.solar-panels {
  position: absolute;
  top: 5%;
  left: 41%;
  width: 2.5rem;
  height: 2.5rem;
  transform: rotate(14deg);
}

.batteries {
    position: absolute;
    top: 80%;
    left: 21%;
    width: 3rem;
    height: 3rem;
}

.electric-car {
  position: absolute;
  top: 100%;
  left: 50%;
  width: 5rem;
  height: 3rem;
}

.heat-pump {
    position: absolute;
    top: 66%;
    left: 73%;
    width: 3rem;
    height: 3rem;
    transform: rotate(0deg);
}
</style>