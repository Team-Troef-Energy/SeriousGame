<template>
    <div class="race-level-editor-page container">
        <RaceBackButtonHeader :location="`/race/${raceId}`"></RaceBackButtonHeader>
        <div class="content">
            <RaceLevelEditor :raceId="raceId"></RaceLevelEditor>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useRoute } from 'vue-router';
import RaceLevelEditor from '../../components/race/LevelEditor.vue';
import RaceBackButtonHeader from '../../components/race/RaceBackButtonHeader.vue';
import router from '../../router/Router';

export default defineComponent({
    name: 'RacePage',
    components: { RaceBackButtonHeader, RaceLevelEditor },
    setup() {
        const route = useRoute();
        let raceId = String(route.params.id);

        if (raceId != 'f47ac10b-58cc-4372-a567-0e02b2c3d479') {
            router.push('/');
        }

        const navigateTo = (location: string) => {
            router.push(location);
        };

        return {
            raceId,
            navigateTo
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

.content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}
</style>