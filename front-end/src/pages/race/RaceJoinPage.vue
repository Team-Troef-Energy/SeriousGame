<template>
    <div class="race-join-page container">
        <div class="content"></div>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted} from 'vue';
import router from '../../router/Router';

export default defineComponent({
    name: 'RaceJoinPage',
    components: {},
    setup() {

        const navigateTo = (location: string) => {
            router.push(location);
        };

        onMounted(async () => {
        });

        return {

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
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}
</style>