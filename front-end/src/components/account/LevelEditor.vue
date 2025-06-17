<template>
    <div class="level-editor">
        <GlobalLevelEditor :fetchAllLevels="fetchAllLevels" :fetchStartLevel="fetchStartLevel"
            @createLevel="createLevelTemplate" @updateLevel="updateLevelTemplate" @deleteLevel="deleteLevelTemplate">
        </GlobalLevelEditor>
        <Teleport to="body">
            <TextModal :show="isTextModalVisible" :content="textModalContent" @close="isTextModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { gameLevelService } from '../../services/game/GameLevelService';
import { templateLevelService } from '../../services/game/TemplateLevelService';
import { templateType } from '../../types/game/levelTemplate/TemplateType';
import { templateWrapper } from '../../types/game/levelTemplate/TemplateWrapper';
import GlobalLevelEditor from '../global/level-editor/LevelEditor.vue';
import TextModal from '../global/modals/TextModal.vue';
import { useTextModal } from '../global/modals/UseTextModal';

export default defineComponent({
    components: { GlobalLevelEditor, TextModal },
    name: 'LevelEditor',
    setup() {
        const { isTextModalVisible, textModalContent, showModal } = useTextModal();

        const fetchAllLevels = async () => {
            return await templateLevelService.fetchAllLevels();
        }

        const fetchStartLevel = async (level: number) => {
            return await gameLevelService.fetchStartLevel(level);
        }

        const createLevelTemplate = async (templateWrapper: templateWrapper) => {
            const errorMessage = 'Er is een fout opgetreden bij het aanmaken van het level';
            if (templateWrapper.template === undefined) {
                return showModal('Fout', errorMessage);
            }
            templateWrapper.template.levelType = templateType.GLOBAL;
            templateLevelService.createLevelTemplate(templateWrapper.template).then(() => {
                showModal('Succes', 'Level is succesvol aangemaakt');
            }).catch((error) => {
                console.error(error);
                showModal('Fout', errorMessage);
            });
        }

        const updateLevelTemplate = async (templateWrapper: templateWrapper) => {
            const errorMessage = 'Er is een fout opgetreden bij het updaten van het level';
            if (templateWrapper.template === undefined || templateWrapper.id === undefined) {
                return showModal('Fout', errorMessage);
            }
            templateLevelService.updateLevelTemplate(templateWrapper.id, templateWrapper.template).then(() => {
                showModal('Succes', 'Level is succesvol gewijzigd');
            }).catch((error) => {
                console.error(error);
                showModal('Fout', errorMessage);
            });
        }

        const deleteLevelTemplate = async (templateWrapper: templateWrapper) => {
            const errorMessage = 'Er is een fout opgetreden bij het verwijderen van het level';
            if (templateWrapper.id === undefined) {
                return showModal('Fout', errorMessage);
            }
            templateLevelService.deleteLevelTemplate(templateWrapper.id).then(() => {
                showModal('Succes', 'Level is succesvol verwijderd');
            }).catch((error) => {
                console.error(error);
                showModal('Fout', errorMessage);
            });
        }

        return {
            isTextModalVisible,
            textModalContent,
            showModal,
            fetchAllLevels,
            fetchStartLevel,
            createLevelTemplate,
            updateLevelTemplate,
            deleteLevelTemplate
        }
    }
}
)
</script>

<style scoped></style>