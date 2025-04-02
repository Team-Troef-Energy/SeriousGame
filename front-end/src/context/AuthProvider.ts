import { defineComponent, ref, onMounted, provide } from 'vue';
import { auth, db } from '../utils/firebase-service';
import { doc, getDoc } from 'firebase/firestore';
import type { User } from 'firebase/auth';

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
        const userDocRef = doc(db, 'users', newUser.email!);
        const userDoc = await getDoc(userDocRef);
        role.value = userDoc.exists() ? userDoc.data().role : null;
      } else {
        role.value = null;
      }
    };

    onMounted(() => {
      const unsubscribe = auth.onAuthStateChanged(async (currentUser: any) => {
        await setUser(currentUser);
        loading.value = false;
      });

      return () => unsubscribe();
    });

    provide(AuthContext, { user, role, loading, setUser });

    return () => slots.default ? slots.default() : null;
  }
});