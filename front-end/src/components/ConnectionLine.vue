<template>
  <div class="connection-line" :style="containerStyle">
    <div class="led-strip" :class="{ 'leds-direction-up': isProduction, 'leds-direction-down': !isProduction, }"
      @mouseover="showInfoBox" @mouseout="hideInfoBox">
      <span v-for="(led, index) in getLedsForLine" :key="index" class="led-wrapper" :style="led.style">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" :style="[
          isProduction ? { transform: 'rotate(180deg)' } : {}
        ]">
          <path
            d="M21 12L14 5V9H3.8C3.51997 9 3.37996 9 3.273 9.0545C3.17892 9.10243 3.10243 9.17892 3.0545 9.273C3 9.37996 3 9.51997 3 9.8V14.2C3 14.48 3 14.62 3.0545 14.727C3.10243 14.8211 3.17892 14.8976 3.273 14.9455C3.37996 15 3.51997 15 3.8 15H14V19L21 12Z"
            :stroke="isProduction ? '#27b74c' : 'gray'" :fill="isProduction ? '#27b74c' : 'gray'" stroke-width="2"
            stroke-linecap="round" stroke-linejoin="round" />
        </svg>
      </span>
    </div>
    <div v-if="hasCongestion" class="congestion-text-indicator">
      Congestie
    </div>
  </div>
</template>

<script lang="ts">
export default {
  props: {
    x1: { type: Number, required: true },
    y1: { type: Number, required: true },
    x2: { type: Number, required: true },
    y2: { type: Number, required: true },
    hasCongestion: { type: Boolean, default: false },
    maxCurrent: { type: Number, default: 0 },
    isProduction: { type: Boolean, default: false },
    current: { type: Number, default: 0 },
    maxHouseCurrent: { type: Number, default: 0 }
  },
  computed: {
    containerStyle(): Record<string, string> {
      const dx = this.x2 - this.x1;
      const dy = this.y2 - this.y1;
      const length = Math.sqrt(dx * dx + dy * dy);
      const angle = Math.atan2(dy, dx) * (180 / Math.PI);
      return {
        width: `${length}px`,
        transform: `rotate(${angle}deg)`,
        transformOrigin: "0 0",
        position: "absolute",
        top: `${this.y1}px`,
        left: `${this.x1}px`
      }
    },
    rotationTransform() {
      const marginX = 15;
      const marginY = 15;
      const dx = this.x2 - this.x1;
      const dy = this.y2 - this.y1;
      const angle = Math.atan2(dy, dx) * (180 / Math.PI);
      const cx = (this.x1 + this.x2) / 2 + marginX;
      const cy = (this.y1 + this.y2) / 2 - marginY;
      return `rotate(${angle}, ${cx}, ${cy})`;
    },
    infoBoxContents() {
      let contents = `Stroom: ${this.current.toPrecision(2)} kW`;
      if (this.hasCongestion) {
        contents = `Congestie: ${this.maxCurrent} kW<br>${contents}`;
      }
      return contents;
    },
    innerLineColor() {
      return this.hasCongestion ? "red" : "white";
    },
    maxLedOpacity(): number {
      return Math.min(1, this.current / this.maxHouseCurrent);
    },

    getLedsForLine() {
      const dx = this.x2 - this.x1;
      const dy = this.y2 - this.y1;
      const lineLength = Math.sqrt(dx * dx + dy * dy);
      const ledSpacing = 55;
      const ledCount = Math.max(1, Math.floor(lineLength / ledSpacing));

      const activeDuration = ledCount * 0.5;
      const totalDuration = activeDuration + 1;
      const animationDelay = activeDuration / ledCount;

      return Array.from({ length: ledCount }, (_, i) => {
        return {
          style: {
            display: 'inline-block',
            verticalAlign: 'middle',
            animation: `pulse ${totalDuration}s infinite`,
            animationDelay: `${i * animationDelay}s`,
            '--max-led-opacity': this.maxLedOpacity,
          },
        };
      })
    },
  },
  methods: {
    showInfoBox(event: any) {
      this.$emit("show-info-box", {
        x: event.clientX,
        y: event.clientY,
        contents: this.infoBoxContents,
      });
    },
    hideInfoBox() {
      this.$emit("hide-info-box");
    },
  },
};
</script>

<style>
.connection-line {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.led-strip {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  height: 1.5rem;
  width: 90%;
  background: #dddddd;
  border: 1px solid #000000;
  border-radius: 1rem;
  gap: 1rem;
}

.leds-direction-down {
  flex-direction: unset;
}

.leds-direction-up {
  flex-direction: row-reverse;
}

.led-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  width: 1.2rem;
  transition: opacity 0.2s;
}

svg {
  width: 1.5rem;
  height: 100%;
}

.congestion-text-indicator {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}

@keyframes pulse {

  0%,
  20% {
    opacity: 0.2;
  }

  30%,
  70% {
    opacity: var(--max-led-opacity);
  }

  80%,
  100% {
    opacity: 0.2;
  }
}
</style>