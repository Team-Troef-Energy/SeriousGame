<template>
    <div class="race-name-change-form">
        <HtmlModal :show="show" :width="30" :height="20">
            <template #header>
                <div class="header">
                    <h3 class="title">Verander race naam</h3>
                </div>
            </template>
            <template #body>
                <div class="body">
                    <form>
                        <div class="form-group">
                            <label for="name">Naam</label>
                            <input type="text" id="name" v-model="raceName" />
                        </div>
                    </form>
                </div>
            </template>
            <template #footer>
                <div class="footer">
                    <button @click="closeModal">Annuleer</button>
                    <button @click="changeRaceName">Verander</button>
                </div>
            </template>
        </HtmlModal>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import HtmlModal from '../global/modals/HtmlModal.vue';
export default defineComponent({
    name: 'RaceNameChangeModal',
    components: {
        HtmlModal,
    },
    props: {
        show: {
            type: Boolean,
            required: true,
        },
    },
    setup(props, { emit }) {
        const closeModal = () => {
            raceName.value = '';
            emit('close');
        }
        const raceName = ref<string>('');

        const changeRaceName = () => {
            if (raceName.value.trim() === '') {
                return;
            }
            emit('race-name-change', raceName.value);
            closeModal();
        }

        return {
            closeModal,
            raceName,
            changeRaceName
        }
    }
})
</script>

<style scoped>
.body {
    height: 60%;
}

form {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

.form-group {
    display: flex;
    align-items: center;
    width: 80%;
}

label {
    width: 8rem;
}

input {
    width: 13rem;
}

input {
    text-align: center;
    border: solid 1px rgba(0, 0, 0, .1) !important;
    ;
}

.footer {
    display: flex;
    justify-content: space-between;
}

.footer button {
    width: 8rem;
    height: 3rem;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: solid 1px rgba(0, 0, 0, .1);
    background-color: #fff;
    color: #000;
    cursor: pointer;
}

.footer button:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}
</style>