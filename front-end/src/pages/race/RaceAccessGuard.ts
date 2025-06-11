import { inject, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { AuthContext } from '../../context/AuthProvider';
import { raceService } from '../../services/game/RaceService';

export function useRaceAccessGuard() {
  const route = useRoute();
  const router = useRouter();
  const authState = inject(AuthContext);
  const { user }: any = authState;
  const raceId = Number(route.params.id ?? route.params.raceId);

  onMounted(async () => {
    await raceService.checkIfRaceBelongsToEmail(raceId, user.value.email)
      .then((match: boolean) => {
        if (!match) {
          router.push('/');
        }
      })
      .catch(() => {
        router.push('/');
      });
  });
}