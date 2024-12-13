<template>
<svg
  :width="width"
  :height="height"
  viewBox="0 0 210 200"
  xmlns="http://www.w3.org/2000/svg"
>
  <!-- Base House -->
  <rect x="50" y="100" width="100" height="60" fill="#D2691E" stroke="#8B4513" stroke-width="2" />
  <polygon points="50,100 100,50 150,100" fill="#A52A2A" stroke="#8B0000" stroke-width="2" />

  <!-- Door -->
  <rect x="90" y="130" width="20" height="30" fill="#8B4513" stroke="#654321" stroke-width="1.5" />
  <circle cx="105" cy="145" r="2" fill="#000" />

  <!-- Windows -->
  <rect x="60" y="110" width="20" height="15" fill="#87CEEB" stroke="#4682B4" stroke-width="1" />
  <line x1="70" y1="110" x2="70" y2="125" stroke="#4682B4" stroke-width="1" />
  <line x1="60" y1="117.5" x2="80" y2="117.5" stroke="#4682B4" stroke-width="1" />

  <rect x="120" y="110" width="20" height="15" fill="#87CEEB" stroke="#4682B4" stroke-width="1" />
  <line x1="130" y1="110" x2="130" y2="125" stroke="#4682B4" stroke-width="1" />
  <line x1="120" y1="117.5" x2="140" y2="117.5" stroke="#4682B4" stroke-width="1" />

  <!-- Solar Panels -->
  <g v-if="hasSolarPanels">
    <rect x="110" y="-40" width="50" height="4" fill="#333" stroke="#222" stroke-width="1" transform="rotate(45)"/>
  </g>

  <!-- Electric Car -->
  <g v-if="hasElectricCar">
    <rect x="160" y="130" width="40" height="20" fill="#4682B4" stroke="#36648B" stroke-width="1.5" />
    <circle cx="165" cy="150" r="5" fill="#000" stroke="#333" stroke-width="1" />
    <circle cx="195" cy="150" r="5" fill="#000" stroke="#333" stroke-width="1" />
    <rect x="168" y="135" width="8" height="4" fill="#FFF" /> <!-- Headlight -->
  </g>

  <!-- Batteries -->
  <g v-if="hasBatteries">
    <!-- Battery Shapes -->
    <rect x="133" y="127" width="4" height="20" fill="#FFD700" stroke="#DAA520" stroke-width="1" />
    <rect x="130" y="130" width="10" height="20" fill="#FFD700" stroke="#DAA520" stroke-width="1" />
    <!-- Battery Icons -->
    <line x1="133" y1="135" x2="137" y2="135" stroke="#000" stroke-width="1" />
    <line x1="135" y1="137" x2="135" y2="133" stroke="#000" stroke-width="1" />
    <line x1="133" y1="145" x2="137" y2="145" stroke="#000" stroke-width="1" />
  </g>

  <!-- Heat Pump -->
  <g v-if="hasHeatPump">
    <rect x="50" y="140" width="30" height="20" fill="#C0C0C0" stroke="#8c8c8c" stroke-width="1.5" />
    <rect x="55" y="145" width="20" height="10" fill="#B0C4DE" stroke="#A9A9A9" stroke-width="1" />
    <circle cx="65" cy="150" r="5" fill="#FFF" stroke="#A9A9A9" stroke-width="1" />
    <line x1="65" y1="146" x2="65" y2="154" stroke="#8c8c8c" stroke-width="1" />
    <line x1="62" y1="148" x2="68" y2="152" stroke="#8c8c8c" stroke-width="1" />
    <line x1="68" y1="148" x2="62" y2="152" stroke="#8c8c8c" stroke-width="1" />
  </g>
</svg>
</template>

<script>
export default {
  props: {
    width: {
      type: Number,
      default: 200,
    },
    height: {
      type: Number,
      default: 200,
    },
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