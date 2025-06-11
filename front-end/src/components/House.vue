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
        v-for="(panel, index) in solarpanels"
        :key="'solar-' + index"
        src="/solarpanel.png"
        alt="Solar Panel"
        class="solar-panel"
        :style="{
          left: (1 + (index % 4) * 3) + 'rem',
          top: (index < 4 ? '1rem' : '4rem'),
          right: ((panel))
        }"
        draggable="true"
        @dragstart="handleDragStart('solarPanels', index)"
      />
      <img
        v-for="(battery, index) in batteries.amount"
        :key="'battery-' + index"
        src="/battery.png"
        alt="Battery"
        class="battery"
        :style="{ left: (1 + index * 3) + 'rem', bottom: '1rem', top: (battery) }"
        draggable="true"
        @dragstart="handleDragStart('batteries', index)"
      />
    </div>
    <img
      v-if="hasElectricCar"
      class="electric-car"
      src="/car.png"
      alt="Auto"
      draggable="true"
      @dragstart="handleDragStart('electricCar')"
    />
    <img
      v-if="hasHeatPump"
      class="heat-pump"
      src="/heat-pump.png"
      alt="Heat Pump"
      draggable="true"
      @dragstart="handleDragStart('heatPump')"
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
  emits: ["drop-item", "remove-item"],
  setup(props, { emit }) {
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

    const handleDragStart = (itemType: string, index?: number) => {
      const x: any = event;
      x?.dataTransfer?.setData("text/plain", itemType);
      emit("remove-item", { itemType, index });
    };

    return {
      handleDragOver,
      handleDragEnter,
      handleDragLeave,
      handleDrop,
      handleDragStart,
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
  z-index: 2;
}

.solar-panel {
  width: 3rem;
  height: 3rem;
  position: absolute;
  cursor: move;
}

.battery {
  width: 2rem;
  height: 2rem;
  position: absolute;
  cursor: move;
}

.electric-car {
  position: absolute;
  top: 100%;
  left: 50%;
  height: 5rem;
  transform: translate(-50%, -50%);
  z-index: 1;
  cursor: move;
}

.heat-pump {
  position: absolute;
  top: 80%;
  left: 75%;
  width: 4rem;
  z-index: 2;
  cursor: move;
}
</style>