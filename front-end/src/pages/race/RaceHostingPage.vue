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
        <div class="content">
            <div class="users">
                <p>Deelnemers</p>
                <div class="users-list">
                    <RaceUser v-for="user in users" :key="user.username" :username="user.username"></RaceUser>
                </div>
            </div>
        </div>
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
import RaceUser from '../../components/race/RaceUser.vue';
import { raceUser } from '../../types/RaceUser';

export default defineComponent({
    name: 'RaceHostingPage',
    components: { TextModal, RaceUser },
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
        let users = ref<raceUser[]>([]);

        users.value = [
            { username: 'Jonathan' },
            { username: 'Wessel' },
            { username: 'Dirk' },
            { username: 'Daan' },
            { username: 'Jens' },
            { username: 'Tristan' },
        ];

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
            users,
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
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    border-bottom: rgba(0, 0, 0, .1) solid 1px;
    padding: 1rem 5% 1rem 5%;
    width: 100%;
    gap: 1rem;
    flex: 1.5;
}

.content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}

.users {
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
}

.users p {
    font-size: 1.5rem;
}

.users-list {
    display: flex;
    justify-content: center;
    padding: 2rem 0rem 2rem 0rem;
    flex-wrap: wrap;
    row-gap: 1rem;
    column-gap: 3rem;
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
    cursor: default;
    user-select: text;
}

.btn-name {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 15rem;
    text-align: center;
    overflow-y: hidden;
    gap: 1rem;
}

.btn-name p {
    text-align: center;
    white-space: nowrap;
}

.btn-code {
    height: 4rem;
}

.btn-stop-hosting:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}

@media (min-width: 640px) {
    .header {
        flex-direction: row;
        padding: 0rem 5% 0rem 5%;
        gap: 0rem;
    }
}
</style>