<template>
  <svg :width="width" :height="height" xmlns="http://www.w3.org/2000/svg" style="position: absolute; top: 0; left: 0;">
    <line :x1="x1" :y1="y1" :x2="x2" :y2="y2" stroke="black" stroke-width="2" />
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
  },
};
</script>