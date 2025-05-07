<template>
    <div class="create-race-form">
        <h1 class="title">Maak Race aan</h1>
        <div class="form">
            <div class="form-group">
                <label for="name">Naam</label>
                <input type="text" id="name" v-model="raceName" />
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent( {
    name: 'RaceCreateForm',
    setup(props, { emit }) {
        const raceName = ref<string>('');

        const createRace = () => {
            emit('race-create', raceName.value);
        }

        return {
            raceName,
            createRace
        }
    }
})
</script>

<style>
label {
    color: green;
}
</style>