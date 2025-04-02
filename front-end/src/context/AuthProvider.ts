import type { User } from 'firebase/auth';
import { doc, getDoc } from 'firebase/firestore';
import { defineComponent, onMounted, provide, ref } from 'vue';
import { firebaseService } from '../services/firebase/FirebaseService';

export const AuthContext = Symbol('AuthContext');

export default defineComponent({
  name: 'AuthProvider',
  setup(_, { slots }) {
    const user = ref<User | null>(null);
    const role = ref<string | null>(null);
    const loading = ref<boolean>(true);

    const setUser = async (newUser: User | null) => {
      user.value = newUser;
      if (newUser) {
        const userDocRef = doc(firebaseService.db, 'users', newUser.email!);
        const userDoc = await getDoc(userDocRef);
        role.value = userDoc.exists() ? userDoc.data().role : null;
      } else {
        role.value = null;
      }
    };

    onMounted(() => {
      const unsubscribe = firebaseService.auth.onAuthStateChanged(async (currentUser: any) => {
        await setUser(currentUser);
        loading.value = false;
      });

      return () => unsubscribe();
    });

    provide(AuthContext, { user, role, loading, setUser });

    return () => slots.default ? slots.default() : null;
  }
});