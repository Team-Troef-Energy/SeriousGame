<template>
 <div class="connection-line" :style="containerStyle">
    <div class="led-strip" :class="{'leds-direction-down': isProduction, 'leds-direction-up': !isProduction}">
      <div
        v-for="(_, index) in getLedsForLine"
        :key="index"
        class="led"
        @mouseover="showInfoBox"
        @mouseout="hideInfoBox">
      </div>
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
  },
  data() {
    return {
      offset: 0, // Offset to control LED movement
    }},
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
        left: `${this.x1}px`,
        zIndex: "10", // Ensure it's on top
      }},
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
    getLedsForLine() {
      const dx = this.x2 - this.x1;
      const dy = this.y2 - this.y1;
      const length = Math.sqrt(dx * dx + dy * dy);
      const ledSpacing = 20; // Spacing between LEDs in pixels
      const ledCount =  Math.max(1, Math.floor(length / ledSpacing)); // Calculate number of LEDs based on length
      const direction = this.isProduction ? 1 : -1; // Direction based on production

      return Array.from({ length: ledCount }, (_, i) => {
        const adjustedIndex = (i + this.offset * direction) % ledCount;
        const normalizedIndex = adjustedIndex < 0 ? ledCount + adjustedIndex : adjustedIndex;
        return {
          style: {
            animation: `pulse 1.4s infinite`,
            animationDelay: `${normalizedIndex * 0.2}s`,
            backgroundColor: this.hasCongestion ? "red" : "green",
          },
        };
      })},
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

<style scoped>
.connection-line {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.led-strip {
  height: 20px;
  background: #111;
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  padding: 0 5px;
}

.leds-top-down {
  flex-direction: row;
}

.leds-bottom-up {
  flex-direction: row-reverse;
}

.led {
  width: 20px;
  height: 100%;
  border-radius: 10px;
  animation: pulse 1.4s infinite;
}

.congestion-text-indicator {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}

@keyframes pulse {
  0%, 100% {
    opacity: 0.2;
    box-shadow: none;
  }
  50% {
    opacity: 1;
    box-shadow: 0 0 10px currentColor, 0 0 20px currentColor;
  }
}
</style>