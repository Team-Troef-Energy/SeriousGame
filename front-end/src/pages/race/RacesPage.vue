<template>
    <div class="races-page container">
        <div class="header">
            <div class="empty-div"></div>
            <button class="btn-race-join" @click="navigateTo(`/race/join`)">Deelnemen aan race</button>
            <button class="btn-race-create" @click="createRaceModal">Maak race</button>
        </div>
        <div class="content">
            <RaceBadge v-for="race in races" :key="race.id" :name="race.name" :id="race.id"></RaceBadge>
        </div>
        <Teleport to="body">
            <RaceCreateModal :show="isRaceModalVisible" @close="isRaceModalVisible = false"
                @race-create="handleCreateRace" />
        </Teleport>
        <Teleport to="body">
            <TextModal :show="isTextModalVisible" :content="textModalContent" @close="isTextModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted, ref } from 'vue';
import TextModal from '../../components/global/modals/TextModal.vue';
import RaceBadge from '../../components/race/RaceBadge.vue';
import RaceCreateModal from '../../components/race/RaceCreateModal.vue';
import { AuthContext } from '../../context/AuthProvider';
import router from '../../router/Router';
import { raceService } from '../../services/game/RaceService';
import { createRaceDTO } from '../../types/dto/CreateRaceDTO';
import { textModal } from '../../types/global/modals/TextModal';
import { race } from '../../types/race/Race';

export default defineComponent({
    name: 'RacesPage',
    components: { RaceCreateModal, TextModal, RaceBadge },
    setup() {
        let isRaceModalVisible = ref(false)

        const createRaceModal = async () => {
            isRaceModalVisible.value = true;
        };

        let isTextModalVisible = ref(false)

        let textModalContent = ref<textModal>({
            header: 'Alert',
            body: 'Nothing to show'
        });

        const showModal = (header: string, body: string) => {
            textModalContent.value.header = header;
            textModalContent.value.body = body;
            isTextModalVisible.value = true;
        };

        const authState = inject(AuthContext);
        const { user }: any = authState;

        let races = ref<race[]>([]);

        const fetchRaces = async () => {
            races.value = await raceService.fetchRacesByEmail(user.value.email)
        }

        const handleCreateRace = async (raceName: string) => {
            raceService.createRace({ name: raceName, userEmail: user.value.email } as createRaceDTO).then(() => {
                fetchRaces()
            }).catch((error) => {
                console.error(error);
                showModal('Fout', 'Er is een fout opgetreden bij het aanmaken van de race');
            });
        }

        const navigateTo = (location: string) => {
            router.push(location);
        };


        onMounted(async () => {
            if (user)
                await fetchRaces();
        });

        return {
            isRaceModalVisible,
            isTextModalVisible,
            textModalContent,
            races,
            createRaceModal,
            handleCreateRace,
            navigateTo,
        };
    }
});
</script>

<style scoped>
.races-page {
    display: flex;
    flex-direction: column;
    height: 90vh;
}

.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0.55rem 5% 0rem 5%;
    width: 100%;
    height: 5rem;
    gap: 1rem;
    flex: 1;
}

.content {
    display: flex;
    justify-content: center;
    padding: 2rem 0rem 2rem 0rem;
    flex-wrap: wrap;
    row-gap: 1rem;
    column-gap: 3rem;
    flex: 10;
}

button {
    width: 8rem;
    height: 5rem;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: solid 1px rgba(0, 0, 0, .1);
    background-color: #fff;
    color: #000;
    cursor: pointer;
}

button:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}

.empty-div {
    width: 7.5rem;
}

.btn-race-join {
    width: 14rem;
}

@media (min-width: 640px) {
    .header {
        gap: 0rem;
    }

    button {
        height: 3rem;
    }

    .btn-race-join {
        width: 12rem;
    }
}
</style>