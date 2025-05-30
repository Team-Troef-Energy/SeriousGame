<template>
    <div class="race-join-page container">
        <div class="content">
            <template v-if="!isInActiveSession()">
                <template v-if="!isSessionCodeValid">
                    <RaceJoinInput :placeholder="'Sessie code'" :buttonText="'Neem deel'"
                        :errorMessage="sessionErrorMessage" @givenInput="handleSessionCode" />
                </template>
                <template v-else>
                    <RaceJoinInput :placeholder="'Gebruikersnaam'" :buttonText="'Start'" @givenInput="handleUsername" />
                </template>
            </template>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import RaceJoinInput from '../../components/race/RaceJoinInput.vue';
import router from '../../router/Router';
import { raceSessionService } from '../../services/game/RaceSessionService';

export default defineComponent({
    name: 'RaceJoinPage',
    components: { RaceJoinInput },
    setup() {
        let sessionCode = ref('');
        let isGivenSessionCodeValid = ref(false);
        let givenSessionCode = ref('');
        let sessionErrorMessage = ref('');

        const isInActiveSession = (): boolean => {
            return sessionCode.value != '';
        };

        const handleNotValidSessionCode = (code: string) => {
            sessionErrorMessage.value = `De sessie code '${code}' is ongeldig. Probeer het opnieuw.`;
            setTimeout(() => {
                sessionErrorMessage.value = '';
            }, 100);
        };

        const handleSessionCode = (code: string) => {
            if (code == '666') return handleNotValidSessionCode(code);
            isGivenSessionCodeValid.value = true;
            givenSessionCode.value = code;
        };

        const navigateTo = (location: string) => {
            router.push(location);
        };

        const handleUsername = (username: string) => {
            if (username.trim() === '') {
                return;
            }

            raceSessionService.setSession({
                code: givenSessionCode.value,
                username: username,
            });

            navigateTo(`/levelselect`);
        };

        onMounted(async () => {
        });

        return {
            sessionErrorMessage,
            sessionCode,
            isSessionCodeValid: isGivenSessionCodeValid,
            isInActiveSession,
            handleSessionCode,
            handleUsername,
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