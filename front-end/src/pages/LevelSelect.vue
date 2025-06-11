<template>
  <div class="level-select-container">
    <div class="level-select-content">
      <h3 class="title">
        <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M17.5 17H6.5M17.5 13H6.5M3 9H21M7.8 3H16.2C17.8802 3 18.7202 3 19.362 3.32698C19.9265 3.6146 20.3854 4.07354 20.673 4.63803C21 5.27976 21 6.11984 21 7.8V16.2C21 17.8802 21 18.7202 20.673 19.362C20.3854 19.9265 19.9265 20.3854 19.362 20.673C18.7202 21 17.8802 21 16.2 21H7.8C6.11984 21 5.27976 21 4.63803 20.673C4.07354 20.3854 3.6146 19.9265 3.32698 19.362C3 18.7202 3 17.8802 3 16.2V7.8C3 6.11984 3 5.27976 3.32698 4.63803C3.6146 4.07354 4.07354 3.6146 4.63803 3.32698C5.27976 3 6.11984 3 7.8 3Z"
            stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
        </svg>
        Selecteer een level
      </h3>
      <div class="level-select-grid">
        <LevelSelectButton v-for="level in levels" :key="level.levelNumber" :gameId="level.id"
          :levelNumber="level.levelNumber" class="level-button" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import LevelSelectButton from "../components/LevelSelectButton.vue";
import { raceSessionService } from "../services/game/RaceSessionService";
import { raceSessionStorageService } from "../services/game/RaceSessionStorageService";
import { templateLevelService } from "../services/game/TemplateLevelService";
import { levelTemplate } from "../types/levelTemplate/LevelTemplate";

export default defineComponent({
  name: "Level",
  components: {
    LevelSelectButton,
  },
  setup() {
    let levels: any = ref<levelTemplate[]>([]);
    let fetchedLevels: levelTemplate[] = [];

    const fetchGlobalLevels = async () => {
      fetchedLevels = await templateLevelService.fetchAllLevels();
      levels.value = fetchedLevels.sort((a: levelTemplate, b: levelTemplate) => a.levelNumber - b.levelNumber);
    };

    onMounted(async () => {
      if (raceSessionStorageService.hasSession()) {
        const localSavedSession = raceSessionStorageService.getSession();

        if (!localSavedSession) {
          return await fetchGlobalLevels();
        }

        await raceSessionService.fetchSessionByJoinCode(localSavedSession.joinCode)
          .then((session) => {
            fetchedLevels = session.race.levels;
            levels.value = fetchedLevels.sort((a: levelTemplate, b: levelTemplate) => a.levelNumber - b.levelNumber);
          })
          .catch(async (error) => {
            if (error.status == 404) {
              raceSessionStorageService.clearSession();
              window.location.reload();
            } else {
              await fetchGlobalLevels();
              console.error("Error fetching session:", error);
            }
          });
      } else {
        await fetchGlobalLevels();
      }
    });

    return {
      levels,
    };
  },
});
</script>

<style scoped>
.level-select-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
}

.level-select-content {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  max-width: 1100px;
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

.level-select-grid {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 20px;
}
</style>