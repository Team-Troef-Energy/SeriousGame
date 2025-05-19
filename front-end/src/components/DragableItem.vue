<template>
    <div class="draggable-item"
      :draggable="true"
      @dragstart="onDragStart">
      <img :src="imageSrc"
        :alt="itemType"
        class="item-image" />
      <span>{{ label }}</span>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent } from "vue";
  
  export default defineComponent({
    name: "DraggableItem",
    props: {
      itemType: {
        type: String,
        required: true,
      },
      imageSrc: {
        type: String,
        required: true,
      },
      label: {
        type: String,
        required: true,
      },
      cost: {
        type: Number,
        required: true,
      },
    },
    methods: {
      onDragStart(event: DragEvent) {
        event.dataTransfer?.setData('text/plain', this.itemType);
      },
    },
  });
  </script>
  
  <style scoped>
  .draggable-item {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.5rem;
    background-color: rgba(255, 255, 255, 0.8);
    border: 1px solid #4a4a4a;
    border-radius: 4px;
    cursor: grab;
    width: 200px;
    color: #333;
  }
  
  .draggable-item:hover {
    background-color: rgba(255, 255, 255, 1);
  }
  
  .item-image {
    width: 50px;
    height: 50px;
  }
  
  span {
    font-size: 0.9rem;
    font-weight: bold;
  }
  </style>