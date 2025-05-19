<template>
    <div class="race-delete-modal">
        <HtmlModal :show="show" :width="30" :height="20">
            <template #header>
                <div class="header">
                    <h3 class="title">Race verwijderen</h3>
                </div>
            </template>
            <template #body>
                <div class="body">
                  <p>Weet je zeker dat je deze race wilt verwijderen?</p>
                </div>
            </template>
            <template #footer>
                <div class="footer">
                    <button @click="closeModal">Nee</button>
                    <button @click="deleteRace">Ja</button>
                </div>
            </template>
        </HtmlModal>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import HtmlModal from '../global/modals/HtmlModal.vue';
export default defineComponent({
    name: 'RaceDeleteModal',
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
            emit('close');
        }


        const deleteRace = () => {
            emit('race-delete');
            closeModal();
        }

        return {
            closeModal,
            deleteRace
        }
    }
})
</script>

<style scoped>
.body {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 60%;
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