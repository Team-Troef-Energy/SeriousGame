<template>
    <div class="level-editor container">

    </div>
</template>

<script lang="ts">
import { defineComponent, PropType, ref } from 'vue';
import { levelTemplate } from '../../types/level-editor/LevelTemplate';

export default defineComponent({
    name: 'LevelEditor',
    props: {
        levelTemplate: {
            type: Object as PropType<levelTemplate>,
            required: true,
        },
    },
    setup(props) {
        let levelTemplate = ref<levelTemplate>({
            levelNumber: 0,
            maxCo2: 0,
            maxCoins: 0,
            season: 'SPRING',
            amountOfBatteriesForTransformator: 0,
            resourceCosts: {
                battery: 0,
                solarPanel: 0,
                co2: 0,
            },
            startTime: 0,
            endTime: 0
        });


        return {
            levelTemplate
        };
    }
});
</script>

<style></style>