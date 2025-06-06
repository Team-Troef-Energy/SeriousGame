<template>
    <div class="race-level-editor-page container">
        <RaceBackButtonHeader :location="`/race/${raceId}`"></RaceBackButtonHeader>
        <div class="content">
            <RaceLevelEditor :raceId="raceId"></RaceLevelEditor>
        </div>
        <Teleport to="body">
            <TextModal :show="isTextModalVisible" :content="textModalContent" @close="isTextModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import TextModal from '../../components/global/modals/TextModal.vue';
import { useTextModal } from '../../components/global/modals/UseTextModal';
import RaceLevelEditor from '../../components/race/LevelEditor.vue';
import RaceBackButtonHeader from '../../components/race/RaceBackButtonHeader.vue';
import router from '../../router/Router';
import { raceService } from '../../services/game/RaceService';
import { useRaceAccessGuard } from './RaceAccessGuard';

export default defineComponent({
    name: 'RacePage',
    components: { RaceBackButtonHeader, RaceLevelEditor, TextModal },
    setup() {
        useRaceAccessGuard();
        const { isTextModalVisible, textModalContent, showModal } = useTextModal();

        const route = useRoute();
        let raceId = Number(route.params.id);

        const navigateTo = (location: string) => {
            router.push(location);
        };

        onMounted(async () => {
            raceService.fetchRaceById(raceId)
                .catch((error) => {
                    showModal('Error', 'Er is een fout opgetreden bij het openen van de level-editor van de race');
                    console.error(error);
                })
        });

        return {
            isTextModalVisible,
            textModalContent,
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
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}
</style>