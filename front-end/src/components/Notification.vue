<template>
    <v-dialog
        v-model="status"
        transition="dialog-bottom-transition"
        width="auto">
        <template v-slot:default="{ isActive }">
            <v-card>
                <v-toolbar title="Opening from the Bottom"></v-toolbar>
                <v-card-text class="text-h2 pa-12">
                    {{ message }}
                </v-card-text>
                <v-card-actions class="justify-end">
                    <v-btn
                        text
                        @click="closeDialog"
                    >Close</v-btn>
                </v-card-actions>
            </v-card>
        </template>
    </v-dialog>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'Notification',
        props: {
            status: {
                type: Boolean,
                required: true
            },
            message: {
                type: String,
                required: true
            }
        },
        emits: ['update:status'],
        setup(props, { emit }) {
            const closeDialog = () => {
                emit('update:status', false);
            };

            return {
                closeDialog
            };
        }
    });
</script>