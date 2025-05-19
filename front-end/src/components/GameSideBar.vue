<template>
    <div class="game-sidebar">
      <h2 class="sidebar-title">Game Sidebar</h2>
      <div class="sidebar-content">
        <DraggableItem
          itemType="solarPanels"
          imageSrc="/solar-panels.png"
          label="Solar Panels"
          :cost="solarPanelCost"
        />
        <DraggableItem
          itemType="batteries"
          imageSrc="/batteries.png"
          label="Batteries"
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
        linear-gradient(rgba(120, 120, 120, 0.85), rgba(120, 120, 120, 0.85)),
        url("/game.jpg");
    background-size: cover, cover;
    background-blend-mode: normal;
    border-left: 2px solid #4a4a4a;
    padding: 20px 0;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    z-index: 1000;
}
  
  .sidebar-title {
    font-size: 1.5rem;
    color: #fff;
    padding-bottom: 20px;
    margin-bottom: 20px;
    text-align: center;
    width: 100%;
    border-bottom: 2px solid #4a4a4a;
  }
  
  .sidebar-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    margin-top: 20px;
  }

  .sidebar-content img {
    width: 100px;
  }
  </style>