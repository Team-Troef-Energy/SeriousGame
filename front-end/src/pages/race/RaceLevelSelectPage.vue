<template>
  <div class="race-level-select-container">
    <RaceBackButtonHeader :location="`/race/${raceId}`"></RaceBackButtonHeader>
    <div class="race-level-select-content">
      <h3 class="title">
        <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M17.5 17H6.5M17.5 13H6.5M3 9H21M7.8 3H16.2C17.8802 3 18.7202 3 19.362 3.32698C19.9265 3.6146 20.3854 4.07354 20.673 4.63803C21 5.27976 21 6.11984 21 7.8V16.2C21 17.8802 21 18.7202 20.673 19.362C20.3854 19.9265 19.9265 20.3854 19.362 20.673C18.7202 21 17.8802 21 16.2 21H7.8C6.11984 21 5.27976 21 4.63803 20.673C4.07354 20.3854 3.6146 19.9265 3.32698 19.362C3 18.7202 3 17.8802 3 16.2V7.8C3 6.11984 3 5.27976 3.32698 4.63803C3.6146 4.07354 4.07354 3.6146 4.63803 3.32698C5.27976 3 6.11984 3 7.8 3Z"
            stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
        </svg>
        Selecteer een level
      </h3>
      <div class="race-level-select-grid">
        <LevelSelectButton v-for="level in levels" :key="level.levelNumber" :gameId="level.id" :levelNumber="level.levelNumber"
          class="level-button" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import LevelSelectButton from "../../components/LevelSelectButton.vue";
import RaceBackButtonHeader from "../../components/race/RaceBackButtonHeader.vue";
import { raceService } from "../../services/game/RaceService";
import { levelTemplate } from "../../types/levelTemplate/LevelTemplate";

export default defineComponent({
  name: "RaceLevelSelectPage",
  components: {
    RaceBackButtonHeader, LevelSelectButton,
  },
  setup() {
    const route = useRoute();
    let raceId = Number(route.params.id);
    let levels: any = ref<levelTemplate[]>([]);

    onMounted(async () => {
      const race = await raceService.fetchRaceById(raceId);
      const fetchedLevels = race.levels;
      levels.value = fetchedLevels.sort((a: levelTemplate, b: levelTemplate) => a.levelNumber - b.levelNumber);
    });

    return {
      raceId,
      levels,
    };
  },
});
</script>

<style scoped>
.race-level-select-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 90vh;
}

.race-level-select-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem 0rem 2rem 0rem;
  width: 100%;
  max-width: 1100px;
  flex: 10;
}

.title {
  font-size: 22px;
  margin: 50px 0 30px 0;
  display: flex;
  align-items: center;
  gap: 5px;
}

.title svg {
  width: 20px;
  height: 20px;
}

.race-level-select-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  width: 100%;
}
</style>