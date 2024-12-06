<script lang="ts">
import { defineComponent } from 'vue';
import NavigateButton from '../components/NavigateButton.vue';

export default defineComponent({
  name: 'Home',
  components: {
    NavigateButton,
  },
});
</script>

<template>
  <div class="welcome-container">
    <img src="/troef-logo.png" alt="Troef logo" />
    <p class="welcome-text">Welkom! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
    <NavigateButton to="/playground" label="Speel" class="welcome-button" />

  </div>
</template>

<style scoped>
.welcome-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
  background: #dedede;
}

.welcome-text {
  font-size: 1.5em;
  margin-bottom: 20px;
  color: #3b3b3b;
  margin-left: 50px;
  margin-right: 50px;
}

img {
  width: 40%;
}

.welcome-button {
  margin-top: 20px;
  width: 200px;
  height: 50px;
  font-size: 1.6em;
}
</style>