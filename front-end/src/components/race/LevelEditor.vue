<template>
    <div class="race-level-editor">
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
import { raceService } from '../../services/game/RaceService';
import { templateLevelService } from '../../services/game/TemplateLevelService';
import { levelTemplate } from '../../types/levelTemplate/LevelTemplate';
import { templateType } from '../../types/levelTemplate/TemplateType';
import { templateWrapper } from '../../types/levelTemplate/TemplateWrapper';
import GlobalLevelEditor from '../global/level-editor/LevelEditor.vue';
import TextModal from '../global/modals/TextModal.vue';
import { useTextModal } from '../global/modals/UseTextModal';

export default defineComponent({
    components: { GlobalLevelEditor, TextModal },
    name: 'RaceLevelEditor',
    props: {
        raceId: {
            type: Number,
            required: true
        }
    },
    setup(props) {
        const { isTextModalVisible, textModalContent, showModal } = useTextModal();

        const fetchAllLevels = async () => {
            const race = await raceService.fetchRaceById(props.raceId);
            return race.levels;
        }

        const fetchStartLevel = async (level: number) => {
            return await gameLevelService.fetchStartLevel(level);
        }

        const createLevelTemplate = async (templateWrapper: templateWrapper) => {
            const errorMessage = 'Er is een fout opgetreden bij het aanmaken van het level';
            if (templateWrapper.template === undefined) {
                return showModal('Fout', errorMessage);
            }
            addTypeAndRaceIdToTemplate(templateWrapper.template);
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

        const addTypeAndRaceIdToTemplate = (template: levelTemplate) => {
            template.levelType = templateType.RACE;
            template.raceId = props.raceId;
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

<style scoped>
.race-level-editor {
    display: flex;
    justify-content: center;
    height: 100%;
    width: 100%;
    overflow-y: auto;
}
</style>