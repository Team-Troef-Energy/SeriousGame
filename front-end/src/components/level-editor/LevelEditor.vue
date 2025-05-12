<template>
    <div class="level-editor">

   
        <Teleport to="body">
            <TextModal :show="isModalVisible" :content="modalContent" @close="isModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { gameLevelService } from '../../services/game/GameLevelService';
import { templateLevelService } from '../../services/game/TemplateLevelService';
import { textModal } from '../../types/global/modals/TextModal';
import { levelTemplate } from '../../types/levelTemplate/LevelTemplate';
import TextModal from '../global/modals/TextModal.vue';
import ComponentHolder from './ComponentHolder.vue';
import HouseConfiguration from './HouseConfiguration.vue';

export default defineComponent({
    components: { ComponentHolder, HouseConfiguration, TextModal },
    name: 'LevelEditor',
    setup() {
    }
})
</script>

<style scoped>

</style>