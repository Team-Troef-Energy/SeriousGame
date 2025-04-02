import {
    createUserWithEmailAndPassword,
    GithubAuthProvider,
    GoogleAuthProvider,
    signInWithEmailAndPassword,
    signInWithPopup,
    signOut
} from "firebase/auth";
import { doc, getDoc, setDoc } from "firebase/firestore";
import { firebaseService } from "./FirebaseService";

class AuthenticationService {
    /**
     * Sign up a user with email and password.
     * @param email - The user's email.
     * @param password - The user's password.
     * @returns The authenticated user.
     */
    async signUpEmailAndPassword(email: string, password: string) {
        const userCredential = await createUserWithEmailAndPassword(firebaseService.auth, email, password);
        return userCredential.user;
    }

    /**
     * Register a user with Google authentication.
     * @returns The authenticated user.
     */
    async registerWithGoogle() {
        const provider = new GoogleAuthProvider();
        const result = await signInWithPopup(firebaseService.auth, provider);
        const user = result.user;

        const userDocRef = doc(firebaseService.db, "users", user.uid);
        const userDoc = await getDoc(userDocRef);

        if (!userDoc.exists()) {
            await setDoc(userDocRef, {
                uid: user.uid,
                name: user.displayName,
                email: user.email,
                photoURL: user.photoURL,
                createdAt: new Date()
            });
        }

        console.log(user);
        return user;
    }

    /**
     * Register a user with GitHub authentication.
     * @returns The authenticated user.
     */
    async registerWithGitHub() {
        const provider = new GithubAuthProvider();
        const result = await signInWithPopup(firebaseService.auth, provider);
        const user = result.user;

        const userDocRef = doc(firebaseService.db, "users", user.uid);
        const userDoc = await getDoc(userDocRef);

        if (!userDoc.exists()) {
            await setDoc(userDocRef, {
                uid: user.uid,
                name: user.displayName,
                email: user.email,
                photoURL: user.photoURL,
                createdAt: new Date()
            });
        }

        console.log(user);
        return user;
    }

    /**
     * Sign out the currently authenticated user.
     * @returns A promise that resolves when the user is signed out.
     */
    async signUserOut() {
        return signOut(firebaseService.auth);
    }

    /**
     * Sign in a user with email and password.
     * @param email - The user's email.
     * @param password - The user's password.
     * @returns The authenticated user.
     */
    async signInEmailAndPassword(email: string, password: string) {
        const userCredential = await signInWithEmailAndPassword(firebaseService.auth, email, password);
        return userCredential.user;
    }
}

export const authenticationService = new AuthenticationService();