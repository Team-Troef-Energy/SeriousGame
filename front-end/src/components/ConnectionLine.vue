<template>
 <div class="connection-line" :style="containerStyle">
    <div class="led-strip" :class="{'leds-direction-up': isProduction, 'leds-direction-down': !isProduction, }">
      <div
        v-for="(led, index) in getLedsForLine"
        :key="index"
        class="led"
        :style="led.style"
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

      const lineLength = Math.sqrt(dx * dx + dy * dy);
      const ledSpacing = 55;
      const ledCount =  Math.max(1, Math.floor(lineLength / ledSpacing));

      const activeDuration = ledCount * 0.5;
      const totalDuration = activeDuration + 1;
      const animationDelay = activeDuration / ledCount;

      return Array.from({ length: ledCount }, (_, i) => {
        return {
          style: {
            animation: `pulse ${totalDuration}s infinite`,
            animationDelay: `${i * animationDelay}s`,
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

<style>
.connection-line {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.led-strip {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 1.5rem;
  width: 90%;
  background: #dddddd;
  border: 1px solid #000000;
  border-radius: 1rem;
  padding: 0rem 0.5rem;
}

.leds-direction-down {
  flex-direction: unset;
}

.leds-direction-up {
  flex-direction: row-reverse;
}

.led {
  height: 0.6rem;
  width: 0.6rem;
  margin: 0rem 1rem 0rem 1rem;
  border-radius: 1rem;
  animation: pulse 1.4s infinite;
}

.congestion-text-indicator {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}

@keyframes pulse {
  0%, 20% {
    opacity: 0.2;
  }
  30%, 70% {
    opacity: 1;
  }
  80%, 100% {
    opacity: 0.2;
  }
}
</style>