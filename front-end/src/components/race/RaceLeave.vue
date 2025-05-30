<template>
    <div class="race-leave">
        <form @submit="handleSubmit">
            <button type="submit">Verlaat race</button>
        </form>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { raceSessionService } from '../../services/game/RaceSessionService';

export default defineComponent({
    name: 'RaceLeave',
    components: {},
    setup() {
        const handleSubmit = (event: Event) => {
            event.preventDefault();
            raceSessionService.clearSession();
            window.location.reload();
        };

        return {
            handleSubmit,
        };
    }
});
</script>

<style scoped>
.race-leave {
    display: flex;
    align-items: center;
    width: 80%;
    height: 12rem;
    border: .5px solid transparent;
    border-radius: 1rem;
    background-image: linear-gradient(white, white), linear-gradient(to right, var(--troef-green), var(--troef-blue));
    background-origin: border-box;
    background-clip: content-box, border-box;
}

form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    gap: 1rem;
}

button {
    width: 80%;
    height: 3rem;
    color: white;
    font-weight: bold;
    border-radius: 0.5rem;
    border: none;
    cursor: pointer;
    background-image: linear-gradient(to right, var(--troef-green), var(--troef-blue));
}

@media (min-width: 475px) {
    .race-leave {
        width: 20rem;
    }
}
</style>