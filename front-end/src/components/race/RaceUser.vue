<template>
    <div class="race-user">
        <div class="content">
            <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M3 20C5.33579 17.5226 8.50702 16 12 16C15.493 16 18.6642 17.5226 21 20M16.5 7.5C16.5 9.98528 14.4853 12 12 12C9.51472 12 7.5 9.98528 7.5 7.5C7.5 5.01472 9.51472 3 12 3C14.4853 3 16.5 5.01472 16.5 7.5Z"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
            <p>{{ username }}</p>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent({
    name: 'RaceUser',
    props: {
        username: {
            type: String,
            required: true,
        }
    },
    setup(props) {
        const username = ref<string>(props.username);

        return {
            username,
        }
    }
})
</script>

<style scoped>
.race-user {
    display: flex;
    align-items: center;
    width: 12rem;
    height: 6rem;
    border: .5px solid transparent;
    border-radius: 1rem;
    background-image: linear-gradient(white, white), linear-gradient(to right, #9cbf9e, #a4cee5);
    background-origin: border-box;
    background-clip: content-box, border-box;
    overflow-x: auto;
}

.content {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.5rem;
    width: 100%;
    height: 100%;
    gap: 1rem;
}

p {
    width: 5rem;
    text-align: center;
    overflow-x: auto;
}
</style>