<template>
    <div class="races-page container">
        <div class="header">
            <button class="btn-race-create" @click="createRaceModal">Maak race</button>
        </div>
        <div class="content">
            <RaceBadge v-for="race in races" :key="race.id" :name="race.name" :id="race.id"></RaceBadge>
        </div>
        <Teleport to="body">
            <RaceCreateModal :show="isModalVisible" @close="isModalVisible = false" @race-create="handleCreateRace" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import RaceCreateModal from '../../components/race/RaceCreateModal.vue';
import RaceBadge from '../../components/race/RaceBadge.vue'

export default defineComponent({
    name: 'RacesPage',
    components: { RaceCreateModal, RaceBadge },
    setup() {
        let isModalVisible = ref(false)

        const createRaceModal = async () => {
            isModalVisible.value = true;
        };

        let races = ref([
            {
                id: "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                name: "test"
            }
        ]);

        const handleCreateRace = async (raceName: string) => {
            races.value.push({
                id: crypto.randomUUID(),
                name: raceName
            });
        }

        return {
            isModalVisible,
            races,
            createRaceModal,
            handleCreateRace
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
    justify-content: flex-end;
    width: 100%;
    height: 5rem;
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
    height: 3rem;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: solid 1px rgba(0, 0, 0, .1);
    background-color: #fff;
    color: #000;
    cursor: pointer;
}

.btn-race-create {
    margin-right: 5%;
}

.btn-race-create:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}
</style>