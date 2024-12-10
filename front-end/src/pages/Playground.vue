<template>
  <div>
    <Transformer :width="200" :height="250" :hasBatteries="true" />
  </div>
</template>

<script>
import Transformer from '../components/Transformer.vue';

export default {
  name: 'Playground',
  components: {
    Transformer,
  },
};
</script>