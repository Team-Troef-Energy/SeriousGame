<template>
    <div class="game-sidebar">
        <h2 class="sidebar-title">Game Sidebar</h2>
        <div class="sidebar-content">
            
            <img src="../../public/batteries.png" alt="Battery">

        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    name: "GameSideBar",
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
    linear-gradient(45deg, rgba(92, 179, 230, 0.5), rgba(115, 193, 119, 0.5)),
    url("/Cartoon_green_texture_grass.jpg");
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