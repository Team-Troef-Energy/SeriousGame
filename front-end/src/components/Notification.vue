<template>
  <v-dialog v-model="status" transition="dialog-bottom-transition" width="auto">
    <template v-slot:default="{ isActive }">
      <v-card>
        <v-card-text class="text-h2 pa-12">
          {{ message }}
        </v-card-text>
        <v-card-actions class="justify-end">
          <NavigateButton to="/levelSelect" label="Ga terug" />
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavigateButton from "../components/NavigateButton.vue";

export default defineComponent({
  name: "Notification",
  components: {
    NavigateButton,
  },
  props: {
    status: {
      type: Boolean,
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
  },
});
</script>
