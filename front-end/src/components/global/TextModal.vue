<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
            <div class="modal-container">
                <div class="modal-header">
                    <h3>{{ content.header }}</h3>
                </div>

                <div class="modal-body">
                    <p>{{ content.body }}</p>
                </div>

                <div class="modal-footer">
                    <p>{{ content.footer }}</p>
                    <slot name="footer">
                        <button class="modal-default-button" @click="$emit('close')">OK</button>
                    </slot>
                </div>
            </div>
        </div>
    </Transition>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue";
import { textModal } from "../../types/global/TextModal";

export default defineComponent({
    name: "TextModal",
    props: {
        show: {
            type: Boolean,
            required: true,
        },
        content: {
            type: Object as PropType<textModal>,
            required: false,
            default: () => ({
                header: "Alert",
                body: "Nothing to show",
                footer: "",
            }),
        }

    },
    emits: ["close"],
});
</script>

<style scoped>
h3 {
    color: var(--troef-blue);
}

p {
    color: #333;
}

button {
    background-color: var(--troef-blue);
    padding: 0.5rem 1rem 0.5rem 1rem;
    border-radius: 0.5rem;
}

.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    transition: opacity 0.3s ease;
}

.modal-container {
    width: 300px;
    margin: auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
}

.modal-header h3 {
    margin-top: 0;
    color: var(--troef-blue);
}

.modal-body {
    margin: 20px 0;
}

.modal-default-button {
    float: right;
}

.modal-enter-from {
    opacity: 0;
}

.modal-leave-to {
    opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
</style>