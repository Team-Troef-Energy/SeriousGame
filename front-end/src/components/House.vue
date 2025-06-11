<template>
  <div
    class="house"
    @dragover.prevent="handleDragOver"
    @dragenter.prevent="handleDragEnter"
    @dragleave.prevent="handleDragLeave"
    @drop.prevent="handleDrop"
  >
    <img class="house-image" src="/house.png" alt="House" />
    <div class="add-ons">
      <img
        v-for="index in solarpanels"
        :key="'solar-' + index"
        src="/solarpanel.png"
        alt="Solar Panel"
        class="solar-panel"
      />
      <img
        v-for="index in batteries.amount"
        :key="'battery-' + index"
        src="/battery.png"
        alt="Battery"
        class="battery"
      />
    </div>
    <img
      v-if="hasElectricCar"
      class="electric-car"
      src="/car.png"
      alt="Auto"
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
import { defineComponent, PropType } from "vue";

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
      type: Object as PropType<{ amount: number }>,
      default: () => ({ amount: 0 }),
    },
  },
  emits: ["drop-item"],
  setup(props, { emit }) {

    props;
    
    const handleDragOver = (event: DragEvent) => {
      event.preventDefault();
      if (event.dataTransfer) {
        event.dataTransfer.dropEffect = "move";
      }
    };

    const handleDragEnter = (event: any) => {
      event.target.classList.add("drop-target");
    };

    const handleDragLeave = (event: any) => {
      event.target.classList.remove("drop-target");
    };

    const handleDrop = (event: any) => {
      event.target.classList.remove("drop-target");
      const itemType = event.dataTransfer?.getData("text/plain");
      if (itemType === "solarPanels" || itemType === "batteries") {
        emit("drop-item", itemType);
      }
    };

    return {
      handleDragOver,
      handleDragEnter,
      handleDragLeave,
      handleDrop,
    };
  },
});
</script>

<style scoped>
.house {
  position: relative;
  width: 13rem;
  height: 10rem;
  transition: background-color 0.2s;
}

.house.drop-target {
  background-color: rgba(0, 255, 0, 0.2);
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

.battery {
  transform: rotate(14deg);
}

.electric-car {
  position: absolute;
  top: 100%;
  left: 50%;
  height: 5rem;
  transform: translate(-50%, -50%);
  z-index: 1;
}

.heat-pump {
  position: absolute;
  top: 66%;
  left: 53%;
  width: 5rem;
  transform: rotate(0deg);
  z-index: 2;
}
</style>