<script lang="ts">
import { defineComponent } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'NavigateButton',
  props: {
    to: {
      type: String,
      required: true
    },
    label: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const router = useRouter();

    const navigate = () => {
      router.push(props.to);
    };

    return {
      navigate
    };
  }
});
</script>

<template>
  <button @click="navigate" class="navigate-button">
    {{ label }}
  </button>
</template>

<style scoped>
.navigate-button {
  padding: 10px 20px;
  border: none;
  border-radius: 25px;
  background-color: #007bff;
  color: white;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.navigate-button:hover {
  background-color: #0056b3;
}
</style>