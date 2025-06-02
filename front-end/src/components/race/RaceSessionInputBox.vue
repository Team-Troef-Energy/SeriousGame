<template>
    <div class="race-session-input-box">
        <form @submit="handleSubmit">
            <input v-if="placeholder" type="text" v-model="input" :placeholder="placeholder" />
            <button type="submit"> {{ buttonText }}</button>
        </form>
        <Teleport to="body">
            <TextModal :show="isTextModalVisible" :content="textModalContent" @close="isTextModalVisible = false" />
        </Teleport>
    </div>

</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
import { textModal } from '../../types/global/modals/TextModal';
import TextModal from '../global/modals/TextModal.vue';

export default defineComponent({
    name: 'RaceSessionInputBox',
    components: { TextModal },
    props: {
        placeholder: {
            type: String,
            required: false,
        },
        buttonText: {
            type: String,
            required: true,
        },

        errorMessage: {
            type: String,
            required: false,
        },
    },
    setup(props, { emit }) {
        let isTextModalVisible = ref(false)

        let textModalContent = ref<textModal>({
            header: 'Alert',
            body: 'Nothing to show'
        });

        const showModal = (header: string, body: string) => {
            textModalContent.value.header = header;
            textModalContent.value.body = body;
            isTextModalVisible.value = true;
        };

        const input = ref('');

        const handleSubmit = (event: Event) => {
            event.preventDefault();
            const trimmedInput = input.value.trim();
            emit('givenInput', trimmedInput);
        };

        watch(
            () => props.errorMessage,
            (newVal) => {
                if (newVal) {
                    if (newVal !== '') {
                        showModal('Foutmelding', newVal);
                    }
                }
            }
        );

        return {
            input,
            handleSubmit,
            isTextModalVisible,
            textModalContent,
        };
    }
});
</script>

<style scoped>
.race-session-input-box {
    display: flex;
    align-items: center;
    width: 80%;
    height: 12rem;
    border: .5px solid transparent;
    border-radius: 1rem;
    background-image: linear-gradient(white, white), linear-gradient(to right, var(--troef-green), var(--troef-blue));
    background-origin: border-box;
    background-clip: content-box, border-box;
}

form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    gap: 1rem;
}

input {
    text-align: center;
    width: 80%;
    height: 3rem;
    color: rgb(51, 51, 51);
    border: .5px solid transparent;
    background-image: linear-gradient(white, white), linear-gradient(to right, var(--troef-green), var(--troef-blue));
    background-origin: border-box;
    background-clip: content-box, border-box;
}

button {
    width: 80%;
    height: 3rem;
    color: white;
    font-weight: bold;
    border-radius: 0.5rem;
    border: none;
    cursor: pointer;
    background-image: linear-gradient(to right, var(--troef-green), var(--troef-blue));
}

@media (min-width: 475px) {
    .race-session-input-box {
        width: 20rem;
    }
}
</style>