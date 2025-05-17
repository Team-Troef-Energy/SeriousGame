<template>
    <HtmlModal :show="show" :width="19" :height="13">
        <template #header>
            <div class="header">
                <h3>{{ content.header }}</h3>
            </div>
        </template>
        <template #body>
            <div class="body">
                <p>{{ content.body }}</p>
            </div>
        </template>
        <template #footer>
            <div class="footer">
                <p>{{ content.footer }}</p>
                <button @click="$emit('close')">OK</button>
            </div>
        </template>
    </HtmlModal>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue";
import { textModal } from "../../../types/global/modals/TextModal";
import HtmlModal from "./HtmlModal.vue";

export default defineComponent({
    name: "TextModal",
    components: {
        HtmlModal,
    },
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
        },
    },
    setup(props) {
        return {
            content: props.content,
        };
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

.body {
    padding: 1.2rem 0rem 1rem 0rem;
}

.footer {
    display: flex;
    justify-content: flex-end;
    padding: 1rem 0rem 1rem 0rem;
}
</style>