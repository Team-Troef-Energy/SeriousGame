<template>
    <div class="house-configuration container">
        <form class="house-configuration-form">
            <div class="form-row">
                <label for="hasHeatPump">Heeft warmtepomp</label>
                <input type="checkbox" id="hasHeatPump" v-model="houseConfiguration.hasHeatPump" />
            </div>
            <div class="form-row">
                <label for="hasElectricalVehicle">Heeft elektrische auto</label>
                <input type="checkbox" id="hasElectricalVehicle" v-model="houseConfiguration.hasElectricalVehicle" />
            </div>
            <div class="form-row">
                <label for="hasCongestion">Heeft congestie</label>
                <input type="checkbox" id="hasCongestion" v-model="houseConfiguration.hasCongestion" />
            </div>
            <div class="form-row">
                <label for="amountOfSolarPanels">Aantal zonnepanelen</label>
                <input type="number" id="amountOfSolarPanels" v-model="houseConfiguration.amountOfSolarPanels" />
            </div>
            <div class="form-row">
                <label for="amountOfBatteries">Aantal batterijen</label>
                <input type="number" id="amountOfBatteries" v-model="houseConfiguration.amountOfBatteries" />
            </div>
        </form>
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType, ref } from "vue";
import { HouseConfiguration } from "../../types/level-editor/HouseConfiguration";

export default defineComponent({
    name: "HouseConfiguration",
    props: {
        houseConfiguration: {
            type: Object as PropType<HouseConfiguration>,
            required: false,
        },
    }, setup() {
        let houseConfiguration = ref<HouseConfiguration>({
            houseNumber: 0,
            hasHeatPump: false,
            hasElectricalVehicle: false,
            hasCongestion: false,
            amountOfSolarPanels: 0,
            amountOfBatteries: 0
        });

        return {
            houseConfiguration
        };
    }
});
</script>

<style scoped>
.house-configuration {
    width: 95%;
}
.house-configuration-form {
    display: flex;
    flex-direction: row;
    align-items: center;
    text-align: center;
    background-color: rgb(243 243 243);
    border-radius: 1rem;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    overflow: auto;
    padding: 0rem 1rem 0rem 1rem;
    height: 7rem;	
    gap: 2rem;
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
    width: 3rem;
    padding: 0.5rem;
    border-radius: 0.5rem;
    border: 1px solid black;
}

.form-row {
    display: flex;
    justify-content: space-between;
    gap: 2rem;
}
</style>