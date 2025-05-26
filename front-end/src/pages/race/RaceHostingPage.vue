<template>
    <div class="race-hosting-page container">
        <div class="header">
            <button class="btn-name">
                <p>Race naam:</p>
                <p>{{ raceName }}</p>
            </button>
            <button class="btn-code"> Code: {{ sessionCode }}</button>
            <button class="btn-stop-hosting" @click="stopHosting">Stop hosten</button>
        </div>
        <div class="content"></div>
         <div class="footer"></div>
        <Teleport to="body">
            <TextModal :show="isTextModalVisible" :content="textModalContent" @close="isTextModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import router from '../../router/Router';
import { raceService } from '../../services/game/RaceService';
import TextModal from '../../components/global/modals/TextModal.vue';
import { textModal } from '../../types/global/modals/TextModal';

export default defineComponent({
    name: 'RaceHostingPage',
    components: { TextModal },
    setup() {
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

        const route = useRoute();
        const raceId = Number(route.params.id);
        const sessionCode = route.params.code;
        let raceName = ref<string>('');

        const navigateTo = (location: string) => {
            router.push(location);
        };


        const stopHosting = () => {
            navigateTo(`/race/${raceId}/`);
        }

        onMounted(async () => {
            raceService.fetchRaceById(raceId)
                .then((response) => {
                    raceName.value = response.name;
                })
                .catch((error) => {
                    showModal('Error', 'Er is een fout opgetreden bij het ophalen van de race');
                    console.error(error);
                })
        });

        return {
            isTextModalVisible,
            textModalContent,
            raceId,
            sessionCode,
            raceName,
            stopHosting
        };
    }
});
</script>

<style scoped>
.race-hosting-page {
    display: flex;
    flex-direction: column;
    height: 90vh;
}

.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0rem 5% 0rem 5%;
    width: 100%;
    flex: 1.5;
    border-bottom: rgba(0, 0, 0, .1) solid 1px;
}

.content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}

.footer {
    width: 100%;
    flex: 1.5;
    margin-bottom: 1.5rem;
    border-bottom: rgba(0, 0, 0, .1) solid 1px;
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

.btn-name {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 15rem;
    text-align: center;
    overflow-x: auto;
    gap: 1rem;
}

.btn-name p {
    text-align: center;
}

.btn-code {
    height: 4rem;
}

.button:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}
</style>