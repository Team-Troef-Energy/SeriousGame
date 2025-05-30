<template>
    <div class="race-join-page container">
        <div class="content">
            <template v-if="!isInActiveSession()">
                <template v-if="!isGivenSessionCodeValid">
                    <RaceSessionInputBox :placeholder="'Race code'" :buttonText="'Neem deel'"
                        :errorMessage="sessionCodeErrorMessage" @givenInput="handleSessionCode" />
                </template>
                <template v-else>
                    <RaceSessionInputBox :placeholder="'Gebruikersnaam'" :buttonText="'Start'"
                        :errorMessage="sessionJoinErrorMessage" @givenInput="handleSessionJoin" />
                </template>
            </template>
            <template v-else>
                <RaceSessionInputBox :buttonText="'Verlaat race'" :errorMessage="sessionLeaveErrorMessage"
                    @givenInput="handleSessionLeave" />
            </template>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import RaceSessionInputBox from '../../components/race/RaceSessionInputBox.vue';
import router from '../../router/Router';
import { raceSessionService } from '../../services/game/RaceSessionService';
import { raceSessionStorageService } from '../../services/game/RaceSessionStorageService';

export default defineComponent({
    name: 'RaceSessionPage',
    components: { RaceSessionInputBox },
    setup() {
        let sessionCode = ref('');
        let isGivenSessionCodeValid = ref(false);
        let givenSessionCode = ref('');
        let sessionCodeErrorMessage = ref('');
        let sessionJoinErrorMessage = ref('');
        let sessionLeaveErrorMessage = ref('');

        const isInActiveSession = (): boolean => {
            return sessionCode.value != '';
        };

        const handleInvalidSessionCode = (code: string) => {
            sessionCodeErrorMessage.value = `De race code '${code}' is ongeldig. Probeer een andere code te gebruiken.`;
            setTimeout(() => {
                sessionCodeErrorMessage.value = '';
            }, 100);
        };

        const handleSessionCode = (code: string) => {
            raceSessionService.fetchSessionByJoinCode(code)
                .then(() => {
                    isGivenSessionCodeValid.value = true;
                    givenSessionCode.value = code;
                })
                .catch(() => {
                    handleInvalidSessionCode(code);
                });
        };

        const navigateTo = (location: string) => {
            router.push(location);
        };

        const handleSessionJoinError = () => {
            sessionJoinErrorMessage.value = `Er is een fout opgetreden bij het deelnemen aan de race. Probeer het opnieuw met 
           een andere gebruikersnaam.`;
            setTimeout(() => {
                sessionJoinErrorMessage.value = '';
            }, 100);
        };

        const handleSessionJoin = (username: string) => {
            if (username.trim() === '') {
                return;
            }

            raceSessionService.joinSession({ joinCode: givenSessionCode.value, username })
                .then((response) => {
                    raceSessionStorageService.setSession({
                        joinCode: givenSessionCode.value,
                        username: username,
                        token: response.token,
                    });

                    navigateTo(`/levelselect`);
                })
                .catch((error) => {
                    handleSessionJoinError();
                    console.error('Error joining race session:', error);
                });
        };

        const handleSessionLeaveError = (message: string) => {
            sessionLeaveErrorMessage.value = message;
            setTimeout(() => {
                sessionLeaveErrorMessage.value = '';
            }, 100);
        };

        const handleSessionLeave = () => {
            const raceSessionUser = raceSessionStorageService.getSession();
            let isSessionActive = true;

            if (!raceSessionUser) {
                return handleSessionLeaveError('Er is iets verkeerd gegaan met het inladen van je lokale race sessie gegevens.');
            }

            raceSessionService.fetchSessionByJoinCode(raceSessionUser.joinCode).catch(() => {
                isSessionActive = false;
            });

            if (!isSessionActive) {
                raceSessionStorageService.clearSession();
                window.location.reload();
                return;
            }

            raceSessionService.leaveSession(raceSessionUser.token)
                .then(() => {
                    raceSessionStorageService.clearSession();
                    window.location.reload();
                })
                .catch((error) => {
                    handleSessionLeaveError('Er is iets verkeerd gegaan met het verlaten van de race. Probeer het later opnieuw.');
                    console.error('Error leaving race session:', error);
                });
        };

        onMounted(async () => {
            const session = raceSessionStorageService.getSession();
            if (session) {
                sessionCode.value = session.joinCode;
            }
        });

        return {
            sessionCodeErrorMessage,
            sessionJoinErrorMessage,
            sessionLeaveErrorMessage,
            sessionCode,
            isGivenSessionCodeValid,
            isInActiveSession,
            handleSessionCode,
            handleSessionJoin,
            handleSessionLeave,
        };
    }
});
</script>

<style scoped>
.race-join-page {
    display: flex;
    flex-direction: column;
    height: 90vh;
}

.content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    height: 90%;
}
</style>