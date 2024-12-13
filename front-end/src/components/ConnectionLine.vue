<template>
  <svg :width="width" :height="height" xmlns="http://www.w3.org/2000/svg" style="position: absolute; top: 0; left: 0;">
    <line :x1="x1" :y1="y1" :x2="x2" :y2="y2" stroke="black" stroke-width="2" />
    <text v-if="hasCongestion" :x="(x1 + x2) / 2" :y="(y1 + y2) / 2" :transform="rotationTransform" fill="red">
      Congestie: {{ maxCurrent }}kW
    </text>
  </svg>
</template>

<script>
export default {
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
  },
  computed: {
    rotationTransform() {
      const dx = this.x2 - this.x1;
      const dy = this.y2 - this.y1;
      const angle = Math.atan2(dy, dx) * (180 / Math.PI);
      const cx = (this.x1 + this.x2) / 2;
      const cy = (this.y1 + this.y2) / 2;
      return `rotate(${angle}, ${cx}, ${cy})`;
    },
  },
};
</script>