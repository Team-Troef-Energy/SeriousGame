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
</style>