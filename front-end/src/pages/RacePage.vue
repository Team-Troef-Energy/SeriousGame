<template>
    <div class="race-page container">
        <div class="header">
            <button class="btn-race-create" @click="createRace">Maak race</button>
        </div>
        <Teleport to="body">
            <RaceCreateModal :show="isModalVisible" @close="isModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import RaceCreateModal from '../components/race/RaceCreateModal.vue';

export default defineComponent({
    name: 'RacePage',
    components: { RaceCreateModal },
    setup() {
        let isModalVisible = ref(false)

        const createRace = async () => {
            isModalVisible.value = true;
        };

        return {
            isModalVisible,
            createRace,
        };
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

.header {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    height: 5rem;
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