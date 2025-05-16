<template>
    <div class="race-level-editor-page container">
      <RaceLevelEditor :raceId="raceId"></RaceLevelEditor>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useRoute } from 'vue-router';
import RaceLevelEditor from '../../components/race/LevelEditor.vue';
import router from '../../router/Router';


export default defineComponent({
    name: 'RacePage',
    components: { RaceLevelEditor },
    setup() {
        const route = useRoute();
        let raceId = String(route.params.id);

        if (raceId != 'f47ac10b-58cc-4372-a567-0e02b2c3d479') {
            router.push('/');
        }

        return {
            raceId
        };
    }
});
</script>

<style scoped>
.race-level-editor-page {
    display: flex;
    flex-direction: column;
    height: 90vh;
}

RaceLevelEditor {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>