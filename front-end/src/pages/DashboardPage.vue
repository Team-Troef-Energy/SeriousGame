<template>
    <LevelEditor />
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
</style>