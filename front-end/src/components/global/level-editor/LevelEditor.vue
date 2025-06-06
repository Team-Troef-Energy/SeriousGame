<template>
    <div class="level-editor">
        <form class="level-editor-form">
            <div class="level-editor-form-global-inputs">
                <div class="form-level-input form-row">
                    <label for="levelNumber">Level nummer</label>
                    <input id="levelNumber" list="levelNumbers" v-model="levelTemplate.levelNumber"
                        @change="onLevelNumberChange" min="0" />
                    <datalist id="levelNumbers">
                        <option v-for="level in levels" :key="level.levelNumber" :value="level.levelNumber">
                            {{ level.levelNumber }}
                        </option>
                    </datalist>
                </div>
                <div class="form-max-co2-input form-row">
                    <label for="maxCo2">Maximale Co2</label>
                    <input type="number" id="maxCo2" v-model="levelTemplate.objective.maxCO2" min="0" />
                </div>
                <div class="form-max-coins-input form-row">
                    <label for="maxCoins">Maximaal aantal munten</label>
                    <input type="number" id="maxCoins" v-model="levelTemplate.objective.maxCoins" min="0" />
                </div>
                <div class="form-season-input form-row">
                    <label for="season">Seizoen</label>
                    <select v-model="levelTemplate.season">
                        <option value="SPRING">Lente</option>
                        <option value="SUMMER">Zomer</option>
                        <option value="AUTUMN">Herfst</option>
                        <option value="WINTER">Winter</option>
                    </select>
                </div>
                <div class="form-max-amount-of-batteries-for-transformator-input form-row">
                    <label for="maxAmountOfBatteriesForTransformator">Maximaal aantal batterijen voor
                        transformator</label>
                    <input type="number" id="maxAmountOfBatteriesForTransformator"
                        v-model="levelTemplate.transformers[0].maxBatteryCount" min="0" />
                </div>
                <div class="form-congestion-input form-row">
                    <label for="congestion">Heeft de transformator congestie</label>
                    <input type="checkbox" id="congestion"
                        v-model="levelTemplate.transformers[0].congestion.hasCongestion" />
                </div>
                <div class="form-max-current-input form-row"
                    v-if="levelTemplate.transformers[0].congestion.hasCongestion">
                    <label for="maxCurrent">Maximale stroom voor de transformator</label>
                    <input type="number" id="maxCurrent" v-model="levelTemplate.transformers[0].congestion.maxCurrent"
                        min="0" />
                </div>
                <div class="form-costs-battery form-row">
                    <label for="costs-battery">Kosten batterij</label>
                    <input type="number" id="costs-battery" v-model="levelTemplate.cost.batteryCost" min="0" />
                </div>
                <div class="form-costs-solar-panel form-row">
                    <label for="costs-solar-panel">Kosten zonnepaneel</label>
                    <input type="number" id="costs-solar-panel" v-model="levelTemplate.cost.solarPanelCost" min="0" />
                </div>
                <div class="form-costs-co2 form-row">
                    <label for="costs-co2">Kosten Co2</label>
                    <input type="number" id="costs-co2" v-model="levelTemplate.cost.co2Cost" min="0" />
                </div>
                <div class="form-start-time form-row">
                    <label for="start-time">Start tijd</label>
                    <input type="number" id="start-time" v-model="levelTemplate.startTime" min="0" max="23" />
                </div>
                <div class="form-end-time form-row">
                    <label for="end-time">Eind tijd</label>
                    <input type="number" id="end-time" v-model="levelTemplate.endTime" min="0" max="23" />
                </div>
            </div>
            <div class="level-editor-house-button form-row">
                <div class="level-editor-message-input">
                    <input v-model="userInput" id="level-editor-input" placeholder="Typ je bericht..."
                        @keydown.enter.prevent="handleAiGeneration" />
                    <div class="info-tooltip">
                        ℹ️
                        <span class="tooltip-text">Deze input laat je dingen toevoegen aan het level zonder zelf op
                            dingen te hoeven klikken. Zoals: Een huis met maximaal 2 batterijen en 3 zonnepanelen en het
                            level heeft maximaal 30 munten ter beschikking.</span>
                    </div>
                    <button class="level-editor-send-button" @click="handleAiGeneration">Verstuur bericht</button>
                </div>
                <button class="btn" @click.prevent="addHouse">Voeg huis toe</button>
            </div>
            <div class="level-editor-house-list">
                <ComponentHolder>
                    <HouseConfiguration v-for="(house, index) in levelTemplate.transformers[0].houses"
                        :key="house.houseNumber" :house-configuration="house"
                        @update:houseConfiguration="updateHouseConfiguration(index, $event)"
                        @remove-house="removeHouse(index)" />
                </ComponentHolder>
            </div>
            <div class="level-editor-buttons">
                <button class="btn btn-cancel" @click.prevent="clearLevelTemplate">Annuleren</button>
                <button class="btn btn-delete" @click.prevent="deleteLevel">Verwijderen</button>
                <button class="btn btn-save" @click.prevent="saveOrEditLevel">Opslaan</button>
            </div>
        </form>
        <Teleport to="body">
            <TextModal :show="isModalVisible" :content="modalContent" @close="isModalVisible = false" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { pythonService } from '../../../services/PythonService';
import { textModal } from '../../../types/global/modals/TextModal';
import { levelTemplate } from '../../../types/levelTemplate/LevelTemplate';
import { templateWrapper } from '../../../types/levelTemplate/TemplateWrapper';
import TextModal from '../modals/TextModal.vue';
import ComponentHolder from './ComponentHolder.vue';
import HouseConfiguration from './HouseConfiguration.vue';

export default defineComponent({
    components: { ComponentHolder, HouseConfiguration, TextModal },
    name: 'GlobalLevelEditor',
    props: {
        fetchAllLevels: {
            type: Function,
            required: true
        }, fetchStartLevel: {
            type: Function,
            required: true
        },
    },
    setup(props, { emit }) {
        let levels = ref<levelTemplate[]>([]);
        let userInput = ref("");
        let promptOutput = ref("");

        const handleAiGeneration = async (e: any) => {

            e.preventDefault();

            const data = {
                inputMessage: userInput.value,
                location_request: "admin"
            };

            await pythonService.fetchMessage(data).then((response: any) => {
                promptOutput.value = response.response
            }).catch((error: any) => {
                console.error(error);
            });
        }

        const fetchAllLevels = async () => {
            const fetchedLevels = await props.fetchAllLevels();
            levels.value = [...fetchedLevels.sort((a: levelTemplate, b: levelTemplate) => a.levelNumber - b.levelNumber)];
        };

        onMounted(async () => {
            await fetchAllLevels();
        });

        let isModalVisible = ref(false)

        let modalContent = ref<textModal>({
            header: 'Alert',
            body: 'Nothing to show'
        });

        let emptyLevelTemplate: levelTemplate = {
            levelNumber: 0,
            objective: {
                maxCO2: 0,
                maxCoins: 0
            },
            season: 'SPRING',
            transformers: [
                {
                    maxBatteryCount: 0,
                    congestion: {
                        hasCongestion: false,
                        maxCurrent: 0,
                    },
                    houses: [],
                }
            ],
            cost: {
                batteryCost: 0,
                solarPanelCost: 0,
                co2Cost: 0,
            },
            startTime: 0,
            endTime: 0
        };

        let levelTemplate = ref<levelTemplate>({ ...emptyLevelTemplate });

        const showModal = (header: string, body: string) => {
            modalContent.value.header = header;
            modalContent.value.body = body;
            isModalVisible.value = true;
        };

        const onLevelNumberChange = async () => {
            const savedLevelValue = levelTemplate.value.levelNumber;

            if (!(await doesLevelExist(savedLevelValue))) {
                return;
            }

            const levelId = await getLevelIdFromLevelNumber(savedLevelValue);

            if (levelId === undefined) {
                return;
            }

            const startLevelData = await props.fetchStartLevel(levelId);

            clearLevelTemplate();

            levelTemplate.value.levelNumber = savedLevelValue;

            const transformer = startLevelData.hours[startLevelData.hours.length - 1].transformers[0];

            const newLevelTemplate: levelTemplate = {
                levelNumber: levelTemplate.value.levelNumber,
                objective: {
                    maxCO2: startLevelData.objective.maxCO2,
                    maxCoins: startLevelData.objective.maxCoins
                },
                season: startLevelData.season,
                transformers: [
                    {
                        maxBatteryCount: transformer.maxBatteryCount,
                        congestion: {
                            hasCongestion: transformer.congestion.hasCongestion,
                            maxCurrent: transformer.congestion.maxCurrent,
                        },
                        houses: transformer.houses.map((house: any, index: number) => {
                            return {
                                congestion: {
                                    hasCongestion: house.hasCongestion,
                                    maxCurrent: house.maxCurrent
                                },
                                houseNumber: index + 1,
                                hasHeatPump: house.hasHeatpump,
                                hasElectricVehicle: house.hasElectricVehicle,
                                maxBatteries: house.maxBatteryCount,
                                maxSolarPanels: house.maxSolarPanelCount
                            };
                        }),
                    }
                ],
                cost: {
                    batteryCost: startLevelData.cost.batteryCost,
                    solarPanelCost: startLevelData.cost.solarPanelCost,
                    co2Cost: startLevelData.cost.co2Cost,
                },
                startTime: startLevelData.startTime,
                endTime: startLevelData.endTime
            };

            insertLevelTemplate(newLevelTemplate);
        };

        const insertLevelTemplate = (givenLevelTemplate: levelTemplate) => {
            levelTemplate.value = givenLevelTemplate;
        };

        const clearLevelTemplate = () => {
            levelTemplate.value.transformers[0].houses = [];
            levelTemplate.value = { ...emptyLevelTemplate };
        };

        const addHouse = () => {
            levelTemplate.value.transformers[0].houses.push({
                houseNumber: levelTemplate.value.transformers[0].houses.length + 1,
                congestion: {
                    hasCongestion: false,
                    maxCurrent: 0
                },
                hasHeatPump: false,
                hasElectricVehicle: false,
                maxBatteries: 0,
                maxSolarPanels: 0,
            });
        };

        const updateHouseConfiguration = (index: number, updatedHouse: any) => {
            const currentHouse = levelTemplate.value.transformers[0].houses[index];

            if (JSON.stringify(currentHouse) !== JSON.stringify(updatedHouse)) {
                levelTemplate.value.transformers[0].houses[index] = updatedHouse;
            }
        };

        const removeHouse = (index: number) => {
            levelTemplate.value.transformers[0].houses.splice(index, 1);
        };

        const doesLevelExist = async (levelNumber: number) => {
            await fetchAllLevels();
            return levels.value.some(level => level.levelNumber == levelNumber);
        };

        const getLevelIdFromLevelNumber = async (levelNumber: number) => {
            await fetchAllLevels();

            const level = levels.value.find(level => level.levelNumber == levelNumber);

            if (level?.id) {
                return level.id;
            } else {
                return undefined;
            }
        };

        const getTemplateIdFromLevelNumber = async (levelNumber: number) => {
            await fetchAllLevels();

            const level = levels.value.find(level => level.levelNumber == levelNumber);

            if (level?.id) {
                return level.id;
            } else {
                showModal('Fout', 'Level bestaat niet');
                throw new Error(`Level with number ${levelNumber} does not exist`);
            }
        };

        const saveOrEditLevel = async () => {
            if (levelTemplate.value.objective.maxCoins < 0) return showModal('Fout', 'Maximaal aantal munten mag niet negatief zijn');
            if (levelTemplate.value.objective.maxCO2 < 0) return showModal('Fout', 'Maximale Co2 mag niet negatief zijn');
            if (levelTemplate.value.transformers[0].maxBatteryCount < 0) return showModal('Fout', 'Maximaal aantal batterijen voor transformator mag niet negatief zijn');
            if (levelTemplate.value.transformers[0].congestion.maxCurrent < 0) return showModal('Fout', 'Maximale stroom voor transformator mag niet negatief zijn');
            if (levelTemplate.value.cost.batteryCost < 0) return showModal('Fout', 'Kosten batterij mag niet negatief zijn');
            if (levelTemplate.value.cost.solarPanelCost < 0) return showModal('Fout', 'Kosten zonnepaneel mag niet negatief zijn');
            if (levelTemplate.value.cost.co2Cost < 0) return showModal('Fout', 'Kosten Co2 mag niet negatief zijn');
            if (levelTemplate.value.startTime < 0 || levelTemplate.value.startTime > 23) return showModal('Fout', 'Start tijd moet tussen 0 en 23 zijn');
            if (levelTemplate.value.endTime < 0 || levelTemplate.value.endTime > 23) return showModal('Fout', 'Eind tijd moet tussen 0 en 23 zijn');
            if (levelTemplate.value.startTime >= levelTemplate.value.endTime) return showModal('Fout', 'Start tijd moet voor eind tijd zijn');
            if (levelTemplate.value.transformers[0].houses.length === 0) return showModal('Fout', 'Er moet minimaal 1 huis zijn');
            if (levelTemplate.value.transformers[0].houses.length > 7) return showModal('Fout', 'Er mogen maximaal 7 huizen zijn');

            for (const house of levelTemplate.value.transformers[0].houses) {
                if (house.maxBatteries < 0) return showModal('Fout', 'Maximaal aantal batterijen mag niet negatief zijn voor een huis');
                if (house.maxSolarPanels < 0) return showModal('Fout', 'Maximaal aantal zonnepanelen mag niet negatief zijn voor een huis');
                if (house.congestion.maxCurrent < 0) return showModal('Fout', 'Maximale stroom mag niet negatief zijn voor een huis');
            }

            let levelNumber = levelTemplate.value.levelNumber;
            if (await doesLevelExist(levelNumber)) {
                let templateId = await getTemplateIdFromLevelNumber(levelNumber);
                emit('updateLevel', {
                    id: templateId,
                    template: levelTemplate.value
                } as templateWrapper);
            } else {
                emit('createLevel', {
                    template: levelTemplate.value
                } as templateWrapper);
            }
        };

        const deleteLevel = async () => {
            let levelNumber = levelTemplate.value.levelNumber;
            if (!(await doesLevelExist(levelNumber))) return showModal('Fout', 'Kan geen nieuw level verwijderen');
            let templateId = await getTemplateIdFromLevelNumber(levelNumber);
            emit('deleteLevel', {
                id: templateId,
            } as templateWrapper);

            clearLevelTemplate();
        }

        return {
            userInput,
            handleAiGeneration,
            promptOutput,
            levelTemplate,
            updateHouseConfiguration,
            levels,
            isModalVisible,
            showModal,
            modalContent,
            onLevelNumberChange,
            addHouse,
            removeHouse,
            clearLevelTemplate,
            saveOrEditLevel,
            deleteLevel
        };
    }
});
</script>

<style scoped>
.level-editor {
    display: flex;
    justify-content: center;
    height: 100%;
    width: 100%;
    overflow-y: auto;
}

.level-editor-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    background-color: white;
    padding: 1rem;
    overflow: auto;
}

.level-editor-form-global-inputs {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.level-editor-form label,
.level-editor-form input,
.level-editor-form select {
    text-align: center;
    color: black;
    width: 40%;
    margin: 0.5rem;
}

.level-editor-form label {
    display: flex;
    justify-content: center;
    align-items: center;
}

.level-editor-form input,
.level-editor-form select {
    padding: 0.5rem;
    border-radius: 0.5rem;
    border: 1px solid black;
}

input[type="checkbox"] {
    width: 2rem;
}

.form-row {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
    width: 80%;
}

.level-editor-house-button {
    justify-content: center;
    margin-top: 2rem;
}

.level-editor-house-list {
    display: flex;
    justify-content: center;
    height: 15rem;
    width: 100%;
    margin-top: 2rem;
}

.level-editor-buttons {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
    width: 100%;
    margin-top: 2rem;
}

.btn {
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: none;
    background-color: var(--troef-blue);
    color: white;
    cursor: pointer;
}

.btn-cancel {
    background-color: #6c757d;
}

.btn-delete {
    background-color: #ff3c3c;
}

.btn-save {
    background-color: #28a745;
}

.level-editor-message-input {
    display: flex;
    flex: 1;
    width: 100%;
}

.level-editor-message-input input {
    flex: 1;
    width: 100%;
    padding: 0.5rem 1rem;
    box-sizing: border-box;
}

.level-editor-send-button {
    position: relative;
    top: 0px;
    left: .5%;
    border-radius: 0.5rem;
    border: 1px solid #4a4a4a;
    padding: 0px 7px;
}

.info-tooltip {
    position: relative;
    display: inline-block;
    cursor: pointer;
    font-weight: bold;
    margin-left: 0.5rem;
}

.tooltip-text {
    visibility: hidden;
    width: 200px;
    background-color: #fff;
    color: black;
    text-align: left;
    border-radius: 0.5rem;
    padding: 0.5rem;
    position: absolute;
    z-index: 1;
    bottom: 100%;
    left: 50%;
    transform: translateX(-50%);
    opacity: 0;
    transition: opacity 0.3s ease;
    font-size: 0.85rem;
}

.info-tooltip:hover .tooltip-text {
    visibility: visible;
    opacity: 1;
}

@media (min-width: 768px) {

    .level-editor-form,
    .level-editor-form-global-inputs {
        flex-direction: row;
        flex-wrap: wrap;
    }

    .level-editor-form {
        padding: 1rem 5rem 1rem 5rem;
    }

    .level-editor-form-global-inputs {
        justify-content: unset;
    }

    .level-editor-form input,
    .level-editor-form select {
        width: 5rem;
        height: 3rem;
    }

    input[type="checkbox"] {
        width: 2rem;
    }

    .form-row {
        justify-content: center;
        width: 19rem;
    }

    .level-editor-house-button {
        width: 100%;
    }

}

@media (min-width: 1024px) {

    .level-editor-form,
    .level-editor-form-global-inputs {
        flex-direction: row;
        flex-wrap: wrap;
    }

    .form-row {
        width: 17rem;
        gap: 1rem;
    }

    .level-editor-house-button {
        width: 100%;
    }

}

@media (min-width: 1280px) {
    .level-editor-form-global-inputs {
        justify-content: unset;
    }

}

@media (min-width: 1536px) {
    .level-editor-form-global-inputs {
        width: 100%;
    }

    /* .level-editor-house-list {
        height: 20rem;
    } */

    .level-editor-form label,
    .level-editor-form input,
    .level-editor-form select {
        white-space: nowrap;
    }

    .level-editor-form label {
        width: 19rem;
        gap: 3rem;
    }

    .form-row {
        width: 27rem;
        gap: 1rem;
    }

    .level-editor-house-button {
        width: 100%;
    }
}
</style>