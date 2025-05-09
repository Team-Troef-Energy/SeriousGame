<template>
    <div class="race-badge">
        <input v-model="raceName" type="text" readonly />
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent({
    name: 'RaceBadge',
    props: {
        name: {
            type: String,
            required: true,
        },
        id: {
            type: String,
            required: true,
            validator: (value: string) => {
                const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
                return uuidRegex.test(value);
            }
        }
    },
    setup(props) {
        const raceName = ref<string>(props.name);

        return {
            raceName
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