<template>
    <div class="race-hosting-page container">
        <div class="content"></div>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted} from 'vue';
import { useRoute } from 'vue-router';
import router from '../../router/Router';

export default defineComponent({
    name: 'RaceHostingPage',
    components: {},
    setup() {
        const route = useRoute();
        let raceCode = route.params.code;

        const navigateTo = (location: string) => {
            router.push(location);
        };

        onMounted(async () => {
            if (raceCode != '666') return navigateTo(`/`)
        });

        return {

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

.content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}
</style>