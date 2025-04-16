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
const provider = new GoogleAuthProvider();
const githubProvider = new GithubAuthProvider();

class AuthenticationService {
    /**
     * Sign up a user with email and password.
     * @param email - The user's email.
     * @param password - The user's password.
     * @returns The authenticated user.
     */
    async signUpEmailAndPassword(email: string, password: string) {
        const userCredential = await createUserWithEmailAndPassword(firebaseService.auth, email, password);

        await setDoc(doc(firebaseService.db, "users", userCredential.user.uid), {
            email: userCredential.user.email,
            role: "user",
            createdAt: new Date().toISOString()
        });

        return userCredential.user;
    }

    /**
     * Register a user with Google authentication.
     * @returns The authenticated user.
     */
    async registerWithGoogle() {
        const result = await signInWithPopup(firebaseService.auth, provider);
        const user = result.user;

        const userDocRef = doc(firebaseService.db, "users", user.uid);
        const userDoc = await getDoc(userDocRef);

        if (!userDoc.exists()) {
            await setDoc(userDocRef, {
                uid: user.uid,
                name: user.displayName,
                email: user.email,
                role: "user",
                photoURL: user.photoURL,
                createdAt: new Date()
            });
        }

        return user;
    }

    /**
     * Register a user with GitHub authentication.
     * @returns The authenticated user.
     */
    async registerWithGitHub() {
        const result = await signInWithPopup(firebaseService.auth, provider);
        const user = result.user;

        const userDocRef = doc(firebaseService.db, "users", user.uid);
        const userDoc = await getDoc(userDocRef);

        if (!userDoc.exists()) {
            await setDoc(userDocRef, {
                uid: user.uid,
                name: user.displayName,
                email: user.email,
                role: "user",
                photoURL: user.photoURL,
                createdAt: new Date()
            });
        }

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

    /**
     * Sign in a user with Google authentication.
     * @returns The authenticated user.
     */
    async signInGoogle() {
        try {
            const result = await signInWithPopup(firebaseService.auth, provider);
            const credential = GoogleAuthProvider.credentialFromResult(result);
            const accessToken = credential?.accessToken;  // Get the access token

            return {
                user: result.user,
                accessToken: accessToken
            };
        } catch (error) {
            console.error("Error during Google login:", error);
            throw error;
        }
    };

    /**
     * Sign in a user with GitHub authentication.
     * @returns The authenticated user.
     */
    async signInWithGitHub() {
        try {
            const result = await signInWithPopup(firebaseService.auth, githubProvider);
            return result.user;
        } catch (error) {
            console.error("Error during GitHub sign-in:", error);
        }
    }
}

export const authenticationService = new AuthenticationService();