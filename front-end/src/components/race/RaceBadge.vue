<template>
    <div class="race-badge" @click="navigateToRace">
        <input v-model="raceName" type="text" readonly />
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import router from '../../router/Router';
export default defineComponent({
    name: 'RaceBadge',
    props: {
        name: {
            type: String,
            required: true,
        },
        id: {
            type: Number,
            required: true,
        }
    },
    setup(props) {
        const raceName = ref<string>(props.name);

        const navigateToRace = () => {
            router.push(`/race/${props.id}`);
        };

        return {
            raceName,
            navigateToRace
        }
    }
})
</script>

<style scoped>
.race-badge {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80%;
    height: 12.5rem;
    border: .5px solid transparent;
    border-radius: 1rem;
    background-image: linear-gradient(white, white), linear-gradient(to right, var(--troef-green), var(--troef-blue));
    background-origin: border-box;
    background-clip: content-box, border-box;
}

input {
    text-align: center;
    background-color: #fbfbfb;
    width: 80%;
    height: 3rem;
    border-radius: 0.5rem;
}

p {
    font-size: 1.5rem;
    text-align: center;
}

@media (min-width: 475px) {
    .race-badge {
        width: 20rem;
    }
}
</style>