<template>
    <div class="component-holder">
      <div class="holder">
        <slot></slot>
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent } from "vue";
  
  export default defineComponent({
    name: "ComponentHolder",
  });
  </script>
  
  <style scoped>
  .holder {
    display:flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 100%;
    overflow: auto;
    gap: 1rem;
    padding: 0.5rem;
  }
  </style>