<template>
    <div class="level-editor container">
        <form class="level-editor-form">
            <div class="level-editor-form-global-inputs">
                <div class="form-level-input form-row">
                    <label for="levelNumber">Level Nummer</label>
                    <input type="number" id="levelNumber" v-model="levelTemplate.levelNumber" min="0" />
                </div>
                <div class="form-max-co2-input form-row">
                    <label for="maxCo2">Maximale Co2</label>
                    <input type="number" id="maxCo2" v-model="levelTemplate.maxCo2" min="0" />
                </div>
                <div class="form-max-coins-input form-row">
                    <label for="maxCoins">Maximaal aantal munten</label>
                    <input type="number" id="maxCoins" v-model="levelTemplate.maxCoins" min="0" />
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
                <button class="button" @click.prevent="addHouse">Voeg huis toe</button>
            </div>
            <div class="level-editor-house-list">
                <ComponentHolder>
                    <HouseConfiguration v-for="(house, index) in levelTemplate.houses" :key="house.houseNumber"
                        :house-configuration="house" @remove-house="removeHouse(index)" />
                </ComponentHolder>
            </div>
            <div class="level-editor-buttons">
                <button class="button">Annuleren</button>
                <button class="button">Opslaan</button>
            </div>
        </form>
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType, ref } from 'vue';
import { levelTemplate } from '../../types/level-editor/LevelTemplate';
import ComponentHolder from './ComponentHolder.vue';
import HouseConfiguration from './HouseConfiguration.vue';

export default defineComponent({
    components: { ComponentHolder, HouseConfiguration },
    name: 'LevelEditor',
    props: {
        levelTemplate: {
            type: Object as PropType<levelTemplate>,
            required: false,
        },
    },
    setup(props) {
        let levelTemplate = ref<levelTemplate>({
            levelNumber: 0,
            maxCo2: 0,
            maxCoins: 0,
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
        });

        const addHouse = () => {
            levelTemplate.value.houses.push({
                houseNumber: levelTemplate.value.houses.length + 1,
                hasHeatPump: false,
                hasElectricalVehicle: false,
                hasCongestion: false,
                amountOfSolarPanels: 0,
                amountOfBatteries: 0
            });
        };

        const removeHouse = (index: number) => {
            levelTemplate.value.houses.splice(index, 1);
        };


        return {
            levelTemplate,
            addHouse,
            removeHouse
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

.button {
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: none;
    background-color: #007bff;
    color: white;
    cursor: pointer;
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