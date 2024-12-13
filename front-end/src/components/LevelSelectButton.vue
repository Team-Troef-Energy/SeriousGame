<template>
  <v-btn @click="handleClick"> level: {{ this.level }} </v-btn>
</template>

<script>
import { defineComponent, toRefs } from "vue";
import router from "../router/Router";
export default defineComponent({
  name: "LevelSelectButton",
  props: {
    level: {
      type: String,
      required: true,
    },
  },
  methods: {
    handleClick() {
      console.log(this.level);
      router.push(`/level/${this.level}`);
    },
  },
  setup(props) {
    const level = toRefs(props);
    return level;
  },
});
</script>
