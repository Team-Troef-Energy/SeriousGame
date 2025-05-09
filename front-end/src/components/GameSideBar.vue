<template>
    <div class="game-sidebar">
        <h2 class="sidebar-title">Game Sidebar</h2>
        <div class="sidebar-content">
            <p>Example</p>
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
    padding: 20px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    z-index: 1000;
}

.sidebar-title {
    font-size: 1.5rem;
    color: #fff;
    margin-bottom: 20px;
    text-align: center;
}

.sidebar-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 10px;
}
</style>