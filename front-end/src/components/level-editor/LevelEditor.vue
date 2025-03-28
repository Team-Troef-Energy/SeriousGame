<template>
    <div class="level-editor container">
        <form class="level-editor-form">
            <div class="level-editor-form-global-inputs">
                <div class="form-level-input form-row">
                    <label for="levelNumber">Level nummer</label>
                    <select id="levelNumber" v-model="levelTemplate.levelNumber" @change="onLevelNumberChange">
                        <option :value="0" selected>Nieuw Level</option>
                        <option v-for="level in totalAmountOfLevels" :key="level" :value="level">
                            {{ level }}
                        </option>
                    </select>
                </div>
                <div class="form-max-co2-input form-row">
                    <label for="maxCo2">Maximale Co2</label>
                    <input type="number" id="maxCo2" v-model="levelTemplate.objective.maxCo2" min="0" />
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
                <div class="form-amount-of-batteries-for-transformator-input form-row">
                    <label for="amountOfBatteriesForTransformator">Aantal batterijen voor transformator</label>
                    <input type="number" id="amountOfBatteriesForTransformator"
                        v-model="levelTemplate.amountOfBatteriesForTransformator" min="0" />
                </div>
                <div class="form-costs-battery form-row">
                    <label for="costs-battery">Kosten batterij</label>
                    <input type="number" id="costs-battery" v-model="levelTemplate.resourceCosts.battery" min="0" />
                </div>
                <div class="form-costs-solar-panel form-row">
                    <label for="costs-solar-panel">Kosten zonnepaneel</label>
                    <input type="number" id="costs-solar-panel" v-model="levelTemplate.resourceCosts.solarPanel"
                        min="0" />
                </div>
                <div class="form-costs-co2 form-row">
                    <label for="costs-co2">Kosten Co2</label>
                    <input type="number" id="costs-co2" v-model="levelTemplate.resourceCosts.co2" min="0" />
                </div>
                <div class="form-start-time form-row">
                    <label for="start-time">Start tijd</label>
                    <input type="number" id="start-time" v-model="levelTemplate.startTime" min="0" max="24" />
                </div>
                <div class="form-end-time form-row">
                    <label for="end-time">Eind tijd</label>
                    <input type="number" id="end-time" v-model="levelTemplate.endTime" min="0" max="24" />
                </div>
            </div>
            <div class="level-editor-house-button form-row">
                <button class="btn" @click.prevent="addHouse">Voeg huis toe</button>
            </div>
            <div class="level-editor-house-list">
                <ComponentHolder>
                    <HouseConfiguration v-for="(house, index) in levelTemplate.houses" :key="house.houseNumber"
                        :house-configuration="house" @remove-house="removeHouse(index)" />
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
import { textModal } from '../../types/global/TextModal';
import { levelTemplate } from '../../types/level-editor/LevelTemplate';
import { fetchCountLevels, fetchStartLevel } from '../../utils/api';
import TextModal from '../global/TextModal.vue';
import ComponentHolder from './ComponentHolder.vue';
import HouseConfiguration from './HouseConfiguration.vue';

export default defineComponent({
    components: { ComponentHolder, HouseConfiguration, TextModal },
    name: 'LevelEditor',
    setup() {
        let totalAmountOfLevels = ref<number[]>([]);

        onMounted(async () => {
            totalAmountOfLevels.value = await fetchCountLevels();
        });

        let isModalVisible = ref(false)

        let modalContent = ref<textModal>({
            header: 'Alert',
            body: 'Nothing to show'
        });

        let emptyLevelTemplate: levelTemplate = {
            levelNumber: 0,
            objective: {
                maxCo2: 0,
                maxCoins: 0
            },
            season: 'SPRING',
            amountOfBatteriesForTransformator: 0,
            houses: [],
            resourceCosts: {
                battery: 0,
                solarPanel: 0,
                co2: 0,
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
            const startLevelData = await fetchStartLevel(savedLevelValue.toString());
            console.log(startLevelData);
            clearLevelTemplate();

            levelTemplate.value.levelNumber = savedLevelValue;

            const transformer = startLevelData.hours[startLevelData.hours.length - 1].transformers[0];
            const newLevelTemplate = {
                levelNumber: levelTemplate.value.levelNumber,
                objective: {
                    maxCo2: startLevelData.objective.maxCO2,
                    maxCoins: startLevelData.objective.maxCoins
                },
                season: startLevelData.season,
                amountOfBatteriesForTransformator: transformer.batteries.amount,
                houses: transformer.houses.map((house: any, index: number) => {
                    return {
                        houseNumber: index + 1,
                        hasHeatPump: house.hasHeatpump,
                        hasElectricalVehicle: house.hasElectricVehicle,
                        hasCongestion: house.hasCongestion,
                        battery: {
                            amount: house.batteries.amount,
                            maxAmount: house.maxBatteryCount
                        },
                        solarPanel: {
                            amount: house.solarpanels,
                            maxAmount: house.maxSolarPanelCount
                        }
                    };
                }),
                resourceCosts: {
                    battery: startLevelData.cost.batteryCost,
                    solarPanel: startLevelData.cost.solarPanelCost,
                    co2: startLevelData.cost.co2Cost,
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
            levelTemplate.value = { ...emptyLevelTemplate };
        };

        const addHouse = () => {
            levelTemplate.value.houses.push({
                houseNumber: levelTemplate.value.houses.length + 1,
                hasHeatPump: false,
                hasElectricalVehicle: false,
                hasCongestion: false,
                battery: {
                    amount: 0,
                    maxAmount: 0
                },
                solarPanel: {
                    amount: 0,
                    maxAmount: 0
                }
            });
        };

        const removeHouse = (index: number) => {
            levelTemplate.value.houses.splice(index, 1);
        };

        const saveOrEditLevel = () => {
            if (levelTemplate.value.objective.maxCoins < 0) return showModal('Fout', 'Maximaal aantal munten mag niet negatief zijn');
            if (levelTemplate.value.objective.maxCo2 < 0) return showModal('Fout', 'Maximale Co2 mag niet negatief zijn');
            if (levelTemplate.value.amountOfBatteriesForTransformator < 0) return showModal('Fout', 'Aantal batterijen voor transformator mag niet negatief zijn');
            if (levelTemplate.value.resourceCosts.battery < 0) return showModal('Fout', 'Kosten batterij mag niet negatief zijn');
            if (levelTemplate.value.resourceCosts.solarPanel < 0) return showModal('Fout', 'Kosten zonnepaneel mag niet negatief zijn');
            if (levelTemplate.value.resourceCosts.co2 < 0) return showModal('Fout', 'Kosten Co2 mag niet negatief zijn');
            if (levelTemplate.value.startTime < 0 || levelTemplate.value.startTime > 24) return showModal('Fout', 'Start tijd moet tussen 0 en 24 zijn');
            if (levelTemplate.value.endTime < 0 || levelTemplate.value.endTime > 24) return showModal('Fout', 'Eind tijd moet tussen 0 en 24 zijn');
            if (levelTemplate.value.startTime >= levelTemplate.value.endTime) return showModal('Fout', 'Start tijd moet voor eind tijd zijn');
            if (levelTemplate.value.houses.length === 0) return showModal('Fout', 'Er moet minimaal 1 huis zijn');

            for (const house of levelTemplate.value.houses) {
                if (house.battery.amount < 0) return showModal('Fout', 'Aantal batterijen mag niet negatief zijn voor een huis');
                if (house.solarPanel.amount < 0) return showModal('Fout', 'Aantal zonnepanelen mag niet negatief zijn voor een huis');
                if (house.battery.maxAmount < 0) return showModal('Fout', 'Maximaal aantal batterijen mag niet negatief zijn voor een huis');
                if (house.solarPanel.maxAmount < 0) return showModal('Fout', 'Maximaal aantal zonnepanelen mag niet negatief zijn voor een huis');
                if (house.battery.amount > house.battery.maxAmount) return showModal('Fout', 'Aantal batterijen mag niet groter zijn dan maximaal aantal batterijen voor een huis');
                if (house.solarPanel.amount > house.solarPanel.maxAmount) return showModal('Fout', 'Aantal zonnepanelen mag niet groter zijn dan maximaal aantal zonnepanelen voor een huis');
            }

            if (levelTemplate.value.levelNumber == 0) {
                // TODO: Save level
            } else {
                // TODO: Edit level
            }

        };

        const deleteLevel = () => {
            if (levelTemplate.value.levelNumber == 0) return showModal('Fout', 'Kan geen nieuw level verwijderen');

            // TODO: Delete level
        }


        return {
            levelTemplate,
            totalAmountOfLevels,
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
    align-items: center;
    height: 100%;
}

.level-editor-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    background-color: white;
    border-radius: 1rem;
    padding: 2rem;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    height: 80%;
    overflow: auto;
}

.level-editor-form-global-inputs {
    display: flex;
    flex-direction: column;
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
    width: 80%;
    margin-top: 2rem;
}

.btn {
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: none;
    background-color: #007bff;
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

@media (min-width: 768px) {

    .level-editor-form,
    .level-editor-form-global-inputs {
        flex-direction: row;
        justify-content: center;
        flex-wrap: wrap;
        height: unset;
    }

    .level-editor-form {
        width: 75rem;
    }

    .level-editor-form-global-inputs {
        width: 50rem;
    }

    .level-editor-form label {
        width: 30rem;
    }

    .form-row {
        justify-content: center;
        gap: 1rem;
        width: 20rem;
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

    .level-editor-form {
        width: 85rem;
    }

    .level-editor-form-global-inputs {
        width: 60rem;
    }

    .level-editor-form label,
    .level-editor-form input,
    .level-editor-form select {
        white-space: nowrap;
    }

    .level-editor-form label {
        width: 30rem;
    }

    .form-row {
        gap: 1rem;
        width: 25rem;
    }

    .level-editor-house-button {
        width: 100%;
    }
}
</style>