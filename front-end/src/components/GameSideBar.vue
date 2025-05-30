<template>
    <div class="game-sidebar">
      <div class="sidebar-content">
        <DraggableItem
          itemType="solarPanels"
          imageSrc="/solarpanel.png"
          label="Zonnepaneel"
          :cost="solarPanelCost"
        />
        <DraggableItem
          itemType="batteries"
          imageSrc="/battery.png"
          label="Batterij"
          :cost="batteryCost"
        />
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent } from "vue";
  import DraggableItem from "./DragableItem.vue";
  
  export default defineComponent({
    name: "GameSideBar",
    components: {
      DraggableItem,
    },
    props: {
      solarPanelCost: {
        type: Number,
        required: true,
      },
      batteryCost: {
        type: Number,
        required: true,
      },
    },
    setup() {
      return {};
    },
  });
  </script>
  
  <style scoped>
  .game-sidebar {
    width: 250px;
    height: 100%;
    background:
          linear-gradient(rgba(56, 56, 56, 0.423)),
          url("/sidebar.png");
    background-size: cover, cover;
    background-blend-mode: normal;
    border-left: 2px solid #4a4a4a;
    padding: 20px 0;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    z-index: 1000;
  }
  
  .sidebar-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
  }

  .sidebar-content img {
    width: 100px;
    margin-bottom: 40px;
}
</style>