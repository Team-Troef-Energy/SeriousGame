<template>
  <div>
    <h1>Hello World</h1>
    <button @click="handleClick">Click Me</button>
  </div>
</template>

<script>
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
        const response = await fetch('http://localhost:8080/levels/start/1'); //Start level 1
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        this.jsonData = await response.json();
        console.log(this.jsonData);
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
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