<template>
  <v-btn @click="handleClick" class="level-btn">{{ level }}</v-btn>
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
.level-btn {
  color: #000;
  background-image: linear-gradient(white, white), linear-gradient(to right, var(--troef-green), var(--troef-blue));
  background-origin: border-box;
  border: .5px solid transparent;
  background-clip: content-box, border-box;
  padding: .5px;
  height: 190px;
  width: fit-content;
  outline: none;
  box-shadow: none;
}

.level-btn:hover {
  outline: none;
  box-shadow: none;
  background-color: #fefefe;
}
</style>
