<template>
  <div>
    <svg :width="width" :height="height" xmlns="http://www.w3.org/2000/svg" style="position: absolute; top: 0; left: 0;">
      <defs>
        <marker id="start-arrowhead" markerWidth="10" markerHeight="7" refX="0" refY="3.5" orient="auto">
          <polygon points="0 3.5, 10 0, 10 7" fill="black" />
        </marker>
        <marker id="end-arrowhead" markerWidth="10" markerHeight="7" refX="10" refY="3.5" orient="auto">
          <polygon points="0 0, 10 3.5, 0 7" fill="black" />
        </marker>
      </defs>
      <text class="congestion-text-indicator" v-if="hasCongestion" :x="(x1 + x2) / 2" :y="(y1 + y2) / 2" :transform="rotationTransform" fill="red">
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
          :marker-end="current > 0 && !isProduction ? 'url(#end-arrowhead)' : ''"
      />
      <line
          :x1="x1"
          :y1="y1"
          :x2="x2"
          :y2="y2"
          :stroke="innerLineColor"
          stroke-width="2"
      />
      <!-- Invisible line with margin for easier hovering -->
      <line class="infoBox-trigger"
            :x1="x1"
            :y1="y1"
            :x2="x2"
            :y2="y2"
            stroke="transparent"
            stroke-width="30"
            @mouseover="showInfoBox"
            @mouseout="hideInfoBox"
      />
    </svg>
    <div v-if="infoBoxVisible" :style="infoBoxStyle" class="infoBox">
      {{ infoBoxContents }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      infoBoxVisible: false,
      infoBoxStyle: {
        position: 'absolute',
        top: '0px',
        left: '0px',
        backgroundColor: '#333',
        color: '#fff',
        padding: '5px',
        borderRadius: '3px',
        pointerEvents: 'none',
      },
    };
  },
  props: {
    x1: {
      type: Number,
      required: true,
    },
    y1: {
      type: Number,
      required: true,
    },
    x2: {
      type: Number,
      required: true,
    },
    y2: {
      type: Number,
      required: true,
    },
    width: {
      type: Number,
      default: 1000,
    },
    height: {
      type: Number,
      default: 1000,
    },
    hasCongestion: {
      type: Boolean,
      default: false,
    },
    maxCurrent: {
      type: Number,
      default: 0,
    },
    isProduction: {
      type: Boolean,
      default: false,
    },
    current: {
      type: Number,
      default: 1,
    }
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
      return `Congestie: ${this.maxCurrent}kW`;
    },
    innerLineColor() {
      return this.hasCongestion ? 'red' : 'white';
    },
  },
  methods: {
    showInfoBox(event) {
      this.infoBoxVisible = true;
      this.infoBoxStyle.top = `${event.clientY + 10}px`;
      this.infoBoxStyle.left = `${event.clientX + 10}px`;
    },
    hideInfoBox() {
      this.infoBoxVisible = false;
    },
  },
};
</script>

<style scoped>
.infoBox-trigger {
  cursor: help;
  z-index: 1000;
}

.infoBox {
  font-size: 14px;
  z-index: 1000;
}
</style>