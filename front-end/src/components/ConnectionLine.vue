<template>
  <g>
    <defs>
      <marker
        id="start-arrowhead"
        markerWidth="5"
        markerHeight="7"
        refX="-5"
        refY="2"
        orient="auto">
        <polygon points="0 2, 5 0, 5 4" fill="black" />
      </marker>
      <marker id="end-arrowhead" markerWidth="5" markerHeight="7" refX="10" refY="2" orient="auto">
        <polygon points="0 0, 5 2, 0 4" fill="black" />
      </marker>
    </defs>
    <text
      class="congestion-text-indicator"
      v-if="hasCongestion"
      :x="(x1 + x2) / 2"
      :y="(y1 + y2) / 2"
      :transform="rotationTransform"
      fill="red">
      Congestie
    </text>
    <!-- Line with inner and outer colors -->
    <line
      :x1="x1"
      :y1="y1"
      :x2="x2"
      :y2="y2"
      stroke="black"
      stroke-width="6"
      :marker-start="current > 0 && isProduction ? 'url(#start-arrowhead)' : ''"
      :marker-end="current > 0 && !isProduction ? 'url(#end-arrowhead)' : ''" />
    <line :x1="x1" :y1="y1" :x2="x2" :y2="y2" :stroke="innerLineColor" stroke-width="2" />
    <!-- Invisible line with margin for easier hovering -->
    <line
      class="infoBox-trigger"
      :x1="x1"
      :y1="y1"
      :x2="x2"
      :y2="y2"
      stroke="transparent"
      stroke-width="30"
      @mouseover="showInfoBox"
      @mouseout="hideInfoBox" />
  </g>
</template>

<script lang="ts">
import { CSSProperties } from "vue";

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
  },
  methods: {
    showInfoBox(event) {
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
.infoBox-trigger {
  cursor: help;
  z-index: 1000;
}
</style>
