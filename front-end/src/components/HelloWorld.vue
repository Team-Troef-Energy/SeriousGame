<template>
  <div>
    <h1>Hello World</h1>
    <button @click="handleClick">Click Me</button>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  methods: {
    handleClick() {
      console.log('Button clicked!');
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