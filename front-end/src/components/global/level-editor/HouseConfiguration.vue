<template>
    <div class="house-configuration">
        <form class="house-configuration-form">
            <div class="form-house-icon form-row">
                <img src="/images/level-editor/house.png" alt="house" />
            </div>
            <div class="house-configuration-form-inputs">
                <div class="form-row">
                    <label for="hasHeatPump">Heeft warmtepomp</label>
                    <input type="checkbox" id="hasHeatPump" v-model="localHouseConfiguration.hasHeatPump" />
                </div>
                <div class="form-row">
                    <label for="hasElectricVehicle">Heeft elektrische auto</label>
                    <input type="checkbox" id="hasElectricVehicle"
                        v-model="localHouseConfiguration.hasElectricVehicle" />
                </div>
                <div class="form-row">
                    <label for="hasCongestion">Heeft congestie</label>
                    <input type="checkbox" id="hasCongestion"
                        v-model="localHouseConfiguration.congestion.hasCongestion" />
                </div>
                <div class="form-row" v-if="localHouseConfiguration.congestion.hasCongestion">
                    <label for="maxCurrent">Maximale stroom</label>
                    <input type="number" id="maxCurrent" v-model="localHouseConfiguration.congestion.maxCurrent"
                        min="0" />
                </div>
                <div class="form-row">
                    <label for="maxAmountOfBatteries">Maximaal aantal batterijen</label>
                    <input type="number" id="maxAmountOfBatteries" v-model="localHouseConfiguration.maxBatteries"
                        min="0" />
                </div>
                <div class="form-row">
                    <label for="maxAmountOfSolarPanels">Maximaal aantal zonnepanelen</label>
                    <input type="number" id="maxAmountOfSolarPanels" v-model="localHouseConfiguration.maxSolarPanels"
                        min="0" />
                </div>
            </div>
            <div class="form-cross form-row">
                <button class="button" @click="removeHouse">
                    <img src="/images/level-editor/cross.png" alt="delete" />
                </button>
            </div>
        </form>
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType, reactive, watch } from "vue";
import { houseConfiguration } from "../../../types/game/levelTemplate/HouseConfiguration";

export default defineComponent({
    name: "HouseConfiguration",
    props: {
        houseConfiguration: {
            type: Object as PropType<houseConfiguration>,
            required: true,
        },
    },
    setup(props, { emit }) {
        const localHouseConfiguration = reactive(
            JSON.parse(JSON.stringify(props.houseConfiguration))
        );

        watch(
            () => localHouseConfiguration,
            (newVal) => {
                emit("update:houseConfiguration", JSON.parse(JSON.stringify(newVal)));
            },
            { deep: true, immediate: false }
        );

        watch(
            () => props.houseConfiguration,
            (newVal) => {
                Object.assign(localHouseConfiguration, JSON.parse(JSON.stringify(newVal)));
            },
            { deep: true }
        );

        const removeHouse = () => {
            emit("remove-house");
        };

        return {
            localHouseConfiguration,
            removeHouse,
        };
    },
});
</script>

<style scoped>
.house-configuration {
    width: 95%;
}

.house-configuration-form {
    display: flex;
    flex-direction: row;
    text-align: center;
    border-radius: 1rem;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    overflow: auto;
    padding: 1rem;
    gap: 1rem;
}

.house-configuration-form-inputs {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    width: 80%;
    gap: 0.5rem;
}

.house-configuration-form label,
.house-configuration-form input:not([type="checkbox"]) {
    text-align: center;
    color: black;
    margin: 0.5rem;
}

.house-configuration-form input[type="checkbox"] {
    width: 2rem;
}

.house-configuration-form label {
    display: flex;
    justify-content: center;
    align-items: center;
}

.house-configuration-form input {
    width: 3.5rem;
    padding: 0.5rem;
    border-radius: 0.5rem;
    border: 1px solid black;
}

.form-row {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
}

.form-house-icon {
    align-items: center;
    justify-content: center;
}

img {
    width: 5rem;
}

button img {
    width: 2.5rem;
}

@media (min-width: 1280px) {
    .house-configuration-form {
        justify-content: space-between;
    }
}
</style>