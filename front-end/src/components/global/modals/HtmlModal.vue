<template>
    <Transition name="modal">
        <div v-if="show" class="modal-mask">
            <div class="modal-container" :style="{ width: width + 'rem', height: height + 'rem' }">
                <slot name ="header"></slot>
                <slot name ="body"></slot>
                <slot name ="footer"></slot>
            </div>
        </div>
    </Transition>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    name: "HtmlModal",
    props: {
        show: {
            type: Boolean,
            required: true,
        },
        width: {
            type: Number,
            required: false,
            default: 50,
        },
        height: {
            type: Number,
            required: false,
            default: 10,
        },
    },
});
</script>

<style scoped>
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
    margin: auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
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