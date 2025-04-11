<template>
  <div class="home-container">
    <div class="home-content">

      <div class="home-left">
        <h3 class="home-title">TROEF Serious game</h3>
        <p class="home-description">
          TROEF Serious game is een strategisch simulatiespel waarin jij de wijk helpt bij de overstap naar groene energie.
          Plaats zonnepanelen, installeer batterijen en optimaliseer het energiesysteem van huis
          tot transformator. Elke keuze telt: blijf binnen budget, beperk de CO2-uitstoot en zorg dat de wijk zo veel
          mogelijk draait op zelf opgewekte groene stroom. Speel door verschillende seizoenen en dagdelen heen, los realistische
          energie-uitdagingen op en word een meester van de energietransitie!
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

      <div class="home-right">
          <img src="../assets/laptop_with_game.png" alt="A laptop with the TROEF Energy Serious game on the display. In the game there are a few houses, a transformater, and a dashboard.">
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
  height: 90vh;
}

.home-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  max-width: 1100px;
}

/* Home left */

.home-left {
  width: 80%;
}

.home-logo {
  width: 36px;
  display: inline-block;
  vertical-align: text-bottom;
  margin: 0 0 9px 0;
  padding: 0;
}

.home-title {
  font-weight: 750;
  color: #000;
  text-transform: uppercase;
  font-size: 34px;
  line-height: 1.25;
  letter-spacing: -.025em;
  margin-bottom: 10px;
}

.home-description {
  font-size: 14px;
  width: 90%;
  line-height: 1.56;
}

.home-buttons {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-top: 20px;
}

.play-btn {
  border-radius: 8px;
  padding: 10px 18px;
  border: none;
  background-color: #000;
  color: #fff;
  font-size: 12px;
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
  width: 16px;
  height: 16px;
}

.tutorial-btn {
  border-radius: 8px;
  padding: 10px 18px;
  border: solid 1px rgba(0, 0, 0, .1);
  background-color: #fff;
  color: #000;
  font-size: 13px;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 6px;
  width: fit-content;
}

.tutorial-btn:hover {
  cursor: pointer;
  background-color: #f8f8f8;
}

.tutorial-btn svg {
  width: 16px;
  height: 16px;
  margin-top: -2px;
}

/* Home right */

.home-right {
  display: flex;
  align-items: center;
  justify-content: end;
  width: 70%;
}

.home-right img {
  width: 100%;
}
</style>