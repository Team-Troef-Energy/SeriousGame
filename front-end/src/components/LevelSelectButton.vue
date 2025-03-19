<template>
  <v-btn @click="handleClick">{{ level }}</v-btn>
</template>

<script lang="ts">
import { defineComponent, toRefs } from "vue";
import router from "../router/Router";
export default defineComponent({
  name: "LevelSelectButton",
  props: {
    level: {
      type: Number,
      required: true,
    },
  },
  methods: {
    handleClick() {
      router.push(`/level/${this.level}`);
    },
  },
  setup(props) {
    const level = toRefs(props);
    return level;
  },
});
</script>

<style scoped>
.level-button {
  position: relative;
  animation: pulse 3s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

.level-select button {
  border-radius: 50%;
  background-color: #2196f3;
  color: white;
  font-size: 3.5rem;
  width: 100px;
  height: 100px;
  border: none;
  cursor: pointer;
}

.level-select button:hover {
  background-color: #0d47a1;
}
</style>
