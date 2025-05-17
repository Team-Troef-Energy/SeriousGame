<template>
  <div class="house">
    <img class="house"
    :src="'/house.png'"
    alt="House"
  />
    <img
      v-if="hasSolarPanels" class="solar-panels"
      src="/solar-panels.png"
      alt="Solar Panels"
    />
    <img
      v-if="hasElectricCar" class="electric-car"
      src="/electric-car.png"
      alt="Electric Car"
    /> 
    <img
      v-if="hasBatteries" class="batteries"
      src="/batteries.png"
      alt="Batteries"
    />
    <img
      v-if="hasHeatPump" class="heat-pump"
      src="/heat-pump.png"
      alt="Heat Pump"
    /> 
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
  width: 18rem;
  height: 15rem;
}

img .house {
  width: 100%;
  height: 100%;
}

.solar-panels {
  position: absolute;
  top: 15%;
  left: 46%;
  width: 2rem;
  height: 2rem;
  transform: rotate(27deg);
}

.batteries {
  position: absolute;
  top: 69%;
  left: 35%;
  width: 2rem;
  height: 2rem;
}

.electric-car {
  position: absolute;
  top: 85%;
  left: 50%;
  width: 4rem;
  height: 2rem;
}

.heat-pump {
  position: absolute;
  top: 63%;
  left: 72%;
  width: 2.5rem;
  height: 2.5rem;
  transform: rotate(2deg);
}
</style>