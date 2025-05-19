<template>
    <div class="header">
        <button class="btn-back" @click="navigateTo(location)">Ga terug</button>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import router from '../../router/Router';

export default defineComponent({
    name: 'RaceBackButtonHeader',
    props: {
        location: {
            type: String,
            required: true
        }
    },
    setup() {
        const navigateTo = (location: string) => {
            router.push(location);
        };

        return {
            navigateTo
        };
    }
});
</script>

<style scoped>
.header {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    height: 5rem;
    flex: 1;
}

.content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
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

.btn-back {
    margin: 1rem 5% 0rem 0rem;
}

button:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}
</style>