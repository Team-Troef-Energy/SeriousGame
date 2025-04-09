<template>
    <div class="dashboard-page container">
        <LevelEditor />
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import LevelEditor from '../components/level-editor/LevelEditor.vue';

export default defineComponent({
    name: 'DashboardPage',
    components: {
        LevelEditor
    }
});
</script>

<style scoped>
.dashboard-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 90vh;
}

.dashboard-page::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('/green-happy-background.png');
    background-size: cover;
    z-index: -2;
}
</style>