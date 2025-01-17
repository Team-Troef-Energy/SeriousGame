<template>
  <svg :width="width" :height="height" viewBox="0 0 200 250" xmlns="http://www.w3.org/2000/svg">
    <!-- Transformer Base -->
    <rect x="50" y="80" width="100" height="120" fill="#708090" stroke="#2F4F4F" stroke-width="2" />

    <!-- Cooling Fins -->
    <line x1="60" y1="90" x2="60" y2="190" stroke="#2F4F4F" stroke-width="2" />
    <line x1="70" y1="90" x2="70" y2="190" stroke="#2F4F4F" stroke-width="2" />
    <line x1="80" y1="90" x2="80" y2="190" stroke="#2F4F4F" stroke-width="2" />
    <line x1="120" y1="90" x2="120" y2="190" stroke="#2F4F4F" stroke-width="2" />
    <line x1="130" y1="90" x2="130" y2="190" stroke="#2F4F4F" stroke-width="2" />
    <line x1="140" y1="90" x2="140" y2="190" stroke="#2F4F4F" stroke-width="2" />

    <!-- Top Cover -->
    <rect x="45" y="60" width="110" height="20" fill="#A9A9A9" stroke="#2F4F4F" stroke-width="2" />

    <!-- Insulators -->
    <g>
      <circle cx="70" cy="50" r="10" fill="#A52A2A" stroke="#8B0000" stroke-width="2" />
      <circle cx="100" cy="50" r="10" fill="#A52A2A" stroke="#8B0000" stroke-width="2" />
      <circle cx="130" cy="50" r="10" fill="#A52A2A" stroke="#8B0000" stroke-width="2" />
      <line x1="70" y1="50" x2="70" y2="60" stroke="#8B0000" stroke-width="2" />
      <line x1="100" y1="50" x2="100" y2="60" stroke="#8B0000" stroke-width="2" />
      <line x1="130" y1="50" x2="130" y2="60" stroke="#8B0000" stroke-width="2" />
    </g>

    <!-- Batteries -->
    <g v-if="hasBatteries">
      <!-- Battery Shapes -->
      <rect
        x="133"
        y="127"
        width="4"
        height="20"
        fill="#FFD700"
        stroke="#DAA520"
        stroke-width="1" />
      <rect
        x="130"
        y="130"
        width="10"
        height="20"
        fill="#FFD700"
        stroke="#DAA520"
        stroke-width="1" />
      <!-- Battery Icons -->
      <line x1="133" y1="135" x2="137" y2="135" stroke="#000" stroke-width="1" />
      <line x1="135" y1="137" x2="135" y2="133" stroke="#000" stroke-width="1" />
      <line x1="133" y1="145" x2="137" y2="145" stroke="#000" stroke-width="1" />
    </g>
  </svg>
</template>

<script lang="ts">
export default {
  props: {
    width: {
      type: Number,
      default: 100,
    },
    height: {
      type: Number,
      default: 125,
    },
    hasBatteries: {
      type: Boolean,
      default: false,
    },
  },
};
</script>
