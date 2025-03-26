<template>
    <div class="component-holder container">
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
  
  <style>
  .holder {
    width: 100%;
    height: 100%;
    overflow: auto;
  }
  </style>