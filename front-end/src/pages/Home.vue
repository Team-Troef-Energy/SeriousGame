<template>
  <div class="home-container">
    <div class="home-content">

      <h3 class="home-title">
        <img src="/troef-icon.png" alt="TROEF Logo" class="home-logo">
        TROEF Serious game
      </h3>
      <p class="home-description">
        Welkom! Dit is de digitale versie van de Troef serious game.
        Dit spel is er om mensen op een leuke manier kennis te laten maken met concepten en uitdagingen
        die bij de energietransitie komen kijken.
      </p>
      <br>
      <p class="home-description">
        Je wint een level door het doel te behalen. Het doel van een level kan bijvoorbeeld zijn om
        alleen maar groene stroom te gebruiken.
        Om hiernaar toe te werken kan je bij huizen zonnepanelen en thuis accu's plaatsen, en de transformator
        transformator voorzien van accu's.
        Het toevoegen van deze onderdelen kost munten en de uitdaging is dus het doel te behalen binnen het budget.
      </p>

      <div class="home-buttons">
        <a href="/levelSelect" class="play-btn">
          Speel de game
          <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M5 12H19M19 12L12 5M19 12L12 19" stroke="currentColor" stroke-width="2" stroke-linecap="round"
              stroke-linejoin="round" />
          </svg>
        </a>
        <a href="/tutorial" class="tutorial-btn">
          Bekijk de tutorial
          <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path
              d="M12 20H5.2C4.07989 20 3.51984 20 3.09202 19.782C2.71569 19.5903 2.40973 19.2843 2.21799 18.908C2 18.4802 2 17.9201 2 16.8V7.2C2 6.07989 2 5.51984 2.21799 5.09202C2.40973 4.71569 2.71569 4.40973 3.09202 4.21799C3.51984 4 4.07989 4 5.2 4H5.6C7.84021 4 8.96031 4 9.81596 4.43597C10.5686 4.81947 11.1805 5.43139 11.564 6.18404C12 7.03968 12 8.15979 12 10.4M12 20V10.4M12 20H18.8C19.9201 20 20.4802 20 20.908 19.782C21.2843 19.5903 21.5903 19.2843 21.782 18.908C22 18.4802 22 17.9201 22 16.8V7.2C22 6.07989 22 5.51984 21.782 5.09202C21.5903 4.71569 21.2843 4.40973 20.908 4.21799C20.4802 4 19.9201 4 18.8 4H18.4C16.1598 4 15.0397 4 14.184 4.43597C13.4314 4.81947 12.8195 5.43139 12.436 6.18404C12 7.03968 12 8.15979 12 10.4"
              stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
          </svg>
        </a>
      </div>
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
.home-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.home-content {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 100%;
  max-width: 1100px;
  margin-top: 200px;
}

.home-logo {
  width: 36px;
  display: inline-block;
  vertical-align: text-bottom;
  margin: 0 0 9px 0;
  padding: 0;
}

.home-title {
  font-weight: 700;
  font-size: 42px;
  margin-bottom: 15px;
}

.home-description {
  font-size: 16px;
  width: 50%;
}

.home-buttons {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}

.play-btn {
  margin-top: 20px;
  border-radius: 8px;
  padding: 6px 12px;
  border: none;
  background-color: #000;
  color: #fff;
  font-size: 15px;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 4px;
  width: fit-content;
}

.play-btn:hover {
  cursor: pointer;
  background-color: #404040;
}

.play-btn svg {
  width: 18px;
  height: 18px;
}

.tutorial-btn {
  margin-top: 20px;
  border-radius: 8px;
  padding: 6px 12px;
  border: none;
  background-color: var(--troef-blue);
  color: #fff;
  font-size: 15px;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 6px;
  width: fit-content;
}

.tutorial-btn:hover {
  cursor: pointer;
  background-color: var(--troef-blue-hover);
}

.tutorial-btn svg {
  width: 18px;
  height: 18px;
  margin-top: -2px;
}

/* Home right */

.home-right {
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: end;
}

.welcome-text {
  color: #000;
}

.welcome-text p {
  line-height: 1.5;
}

.welcome-text-sub {
  display: flex;
  justify-content: center;
  font-size: 1.3em;
  margin-bottom: 20px;
  color: #000000;
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
