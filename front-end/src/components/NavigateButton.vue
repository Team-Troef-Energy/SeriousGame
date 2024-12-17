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
    },
    backgroundColor: {
      type: String,
      default: '#007bff',
      required: false
    },
    textColor: {
      type: String,
      default: '#ffffff',
      required: false
    }
  },
  setup(props) {
    const router = useRouter();

    const navigate = () => {
      router.push(props.to);
    };

    return {
      navigate,
      props
    };
  }
});
</script>

<template>
  <button @click="navigate" class="navigate-button" :style="{ backgroundColor: props.backgroundColor, color: props.textColor }">
    {{ label }}
  </button>
</template>

<style scoped>
.navigate-button {
  padding: 10px 20px;
  border: none;
  border-radius: 25px;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.navigate-button:hover {
  filter: brightness(85%);
}
</style>