<template>
  <div><Dashboard></Dashboard></div>
</template>

<script>
import Transformer from "../components/Transformer.vue";
import Dashboard from "../components/Dashboard.vue";

export default {
  name: "Playground",
  components: {
    Transformer,
    Dashboard,
  },
};
</script>
