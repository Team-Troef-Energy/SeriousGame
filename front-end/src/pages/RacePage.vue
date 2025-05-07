<template>
    <div class="race-page container">
        <div class="header">
            <button class="btn-race-create" @click="createRace">Maak race</button>
        </div>
        <Teleport to="body">
            <TextModal :show="isModalVisible" :content="modalContent" @close="isModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import TextModal from '../components/global/TextModal.vue';
import RaceCreateForm from '../components/race/RaceCreateForm.vue';
import { textModal } from '../types/textModal';

export default defineComponent({
    name: 'RacePage',
    components: { TextModal },
    setup() {
        let isModalVisible = ref(false)

        let modalContent = ref<textModal>({
            header: 'Alert',
            body: 'Nothing to show'
        });

        const createRace = () => {
            showModal('Maak race aan', RaceCreateForm);
        }

        const showModal = (header: any, body: any) => {
            modalContent.value.header = header;
            modalContent.value.body = body;
            isModalVisible.value = true;
        };


        return {
            isModalVisible,
            modalContent,
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