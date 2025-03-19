<template>
  <div class="home container">
    <div class="welcome-container">
      <img src="/troef-logo-light.png" alt="Troef logo" />
      <div class="welcome-text">
        <p>
          Welkom! Dit is de digitale versie van de Troef serious game.
          Dit spel is er om mensen op een leuke manier kennis te laten maken met concepten en uitdagingen
          die bij de energietransitie komen kijken. </p>
      </div>
      <div class="welcome-text-sub">
        <i>
          Je wint een level door het doel te behalen. Het doel van een level kan bijvoorbeeld zijn om
          alleen maar groene stroom te gebruiken.
          Om hiernaar toe te werken kan je bij huizen zonnepanelen en thuis accu's plaatsen, en de transformator
          transformator voorzien van accu's.
          Het toevoegen van deze onderdelen kost munten en de uitdaging is dus het doel te behalen binnen het budget.
        </i>
      </div>
      <NavigateButton to="/levelSelect" label="Speel" class="welcome-button" />
    </div>

  </div>
</template>

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

<style scoped>
.home {
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: auto;
}

.welcome-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  row-gap: 1rem;
}

.welcome-text {
  display: flex;
  justify-content: center;
  font-size: 1.5em;
  margin-bottom: 20px;
  color: #ffffff;
  margin-left: 50px;
  margin-right: 50px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.welcome-text p {
  line-height: 1.5;
}

.welcome-text-sub {
  display: flex;
  justify-content: center;
  font-size: 1.3em;
  margin-bottom: 20px;
  color: #ffffff;
  margin-left: 50px;
  margin-right: 50px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

img {
  margin: 1rem;
  width: 40%;
}

.welcome-button {
  margin: 1rem;
  width: 200px;
  height: 50px;
  text-align: center;
  font-size: 1.6em;
  line-height: 0;
}

.welcome-container::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0.8)), url('/green-happy-background.png');
  background-size: cover;
  background-position: center;
  z-index: -2;
}

@media (min-width: 640px) {
  .home {
    height: 100%;
  }
}

@media (min-width: 768px) {
  .welcome-text p {
    width: 55%;
  }

  .welcome-text-sub i {
    width: 70%;
  }
}
</style>
