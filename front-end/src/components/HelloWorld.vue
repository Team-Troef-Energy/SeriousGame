<template>
  <div>
    <h1>Hello World</h1>
    <button @click="handleClick">Click Me</button>
  </div>
</template>

<script>
import { fetchData, fetchStartLevel1 } from '../utils/api';

export default {
  name: 'HelloWorld',
  data() {
    return {
      jsonData: null
    };
  },
  methods: {
    async handleClick() {
      try {
        this.jsonData = await fetchStartLevel('1'); // Start level 1
        console.log(this.jsonData);
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<style scoped>
h1 {
  color: #42b983;
}
button {
  margin-top: 10px;
}
</style>