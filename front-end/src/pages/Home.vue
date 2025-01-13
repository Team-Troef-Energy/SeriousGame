<script lang="ts">
import { defineComponent } from "vue";
import NavigateButton from "../components/NavigateButton.vue";

export default defineComponent({
  name: "Home",
  components: {
    NavigateButton,
  },
});
</script>

<template>
  <div class="welcome-container">
    <img src="/troef-logo.png" alt="Troef logo" />
    <br />
    <p class="welcome-text">
      Welkom! Dit is de digitale versie van de Troef serious game,
      <br />
      dit spel is er om mensen op een leuke manier kennis te laten maken met concepten en
      uitdagingen
      <br />
      die bij de energietransitie komen kijken.
    </p>
    <p class="welcome-text">
      Elk level heeft een wijk van huizen die door middel van een transformator met elkaar en het
      stroomnet verbonden zijn.
      <br />
      Je wint een level door het doel te behalen, het doel van een level kan bijvoorbeeld zijn om
      alleen maar groene stroom te gebruiken.
      <br />
      om hiernaar toe te werken kan je bij huizen en de transformator zonnepanelen of accus
      toevoegen,
      <br />
      deze kosten munten en de uitdaging is dus het doel te behalen binnen het budget.
    </p>
    <NavigateButton to="/levelSelect" label="Speel" class="welcome-button" />
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
