<template>
    <div class="race-hosting-page container">
        <div class="header">
            <div class="button-wrapper">
                <button class="btn-name">
                    <p>Race naam:</p>
                    <p>{{ raceName }}</p>
                </button>
            </div>
            <div class="button-wrapper">
                <button class="btn-code"> Code: {{ sessionCode }}</button>
            </div>
            <div class="button-wrapper">
                <button class="btn-stop-hosting" @click="stopHosting">Stop hosten</button>
            </div>
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
import { defineComponent, onMounted, onUnmounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import TextModal from '../../components/global/modals/TextModal.vue';
import RaceUser from '../../components/race/RaceUser.vue';
import router from '../../router/Router';
import { raceSessionService } from '../../services/game/RaceSessionService';
import { textModal } from '../../types/global/modals/TextModal';
import { raceUser } from '../../types/race/RaceUser';

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
        const raceId = Number(route.params.raceId);
        const sessionId = Number(route.params.sessionId);
        const joinCode = ref('');
        let raceName = ref<string>('');
        let users = ref<raceUser[]>([]);
        let fetchUsersInterval = ref<ReturnType<typeof setInterval> | undefined>(undefined);

        const fetchUsers = async () => {
            try {
                let response = await raceSessionService.fetchSessionById(sessionId);
                users.value = response.users;
            } catch (error) {
                showModal('Error', 'Er is een fout opgetreden bij het ophalen van de gebruikers');
                console.error(error);
            }
        };

        const navigateTo = (location: string) => {
            router.push(location);
        };


        const stopHosting = () => {
            raceSessionService.deleteSession(sessionId)
                .then(() => {
                    navigateTo(`/race/${raceId}/`);
                })
                .catch((error) => {
                    showModal('Error', 'Er is een fout opgetreden bij het stoppen van de race sessie');
                    console.error(error);
                });
        }

        onMounted(() => {
            raceSessionService.fetchSessionById(sessionId)
                .then((response) => {
                    raceName.value = response.race.name;
                    joinCode.value = response.joinCode;
                })
                .catch((error) => {
                    showModal('Error', 'Er is een fout opgetreden bij het ophalen van de race sessie');
                    console.error(error);
                });

            raceSessionService.checkIfSessionCorrelatesWithRace(raceId, sessionId)
                .catch((error) => {
                    showModal('Error', 'De gemaakte race sessie hoort niet bij deze race');
                    console.error(error);
                });


            fetchUsers();
            fetchUsersInterval.value = setInterval(fetchUsers, 5000);
        });

        onUnmounted(() => {
            if (fetchUsersInterval.value) {
                clearInterval(fetchUsersInterval.value);
            }
        });

        return {
            isTextModalVisible,
            textModalContent,
            raceId,
            sessionCode: joinCode,
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
    padding: 1rem 0rem 1rem 0rem;
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

.button-wrapper {
    display: flex;
    justify-content: center;
    width: 30%;
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
        gap: 0rem;
    }
}
</style>