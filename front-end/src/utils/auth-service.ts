import { auth } from "./firebase-service";
import {
    createUserWithEmailAndPassword,
    GoogleAuthProvider,
    signInWithPopup,
    signOut,
    GithubAuthProvider,
    signInWithEmailAndPassword
} from "firebase/auth";
import { doc, getDoc, setDoc } from "firebase/firestore";
import { db } from "./firebase-service";
const provider = new GoogleAuthProvider();
const githubProvider = new GithubAuthProvider();

export async function signUpEmailAndPassword(email: string, password: string) {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    return userCredential.user;
};

export async function registerWithGoogle() {
    const result = await signInWithPopup(auth, provider);
    const user = result.user;

    const userDocRef = doc(db, "users", user.uid);
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
};

export async function registerWithGitHub() {
    const result = await signInWithPopup(auth, provider);
    const user = result.user;

    const userDocRef = doc(db, "users", user.uid);
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

export function signUserOut() {
    return signOut(auth);
};

export const signInEmailAndPassword = async (email: string, password: string) => {
    const userCredential = await signInWithEmailAndPassword(auth, email, password);
    return userCredential.user;
};

export const signInGoogle = async () => {
    try {
        const result = await signInWithPopup(auth, provider);
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

export async function signInWithGitHub() {
    try {
        const result = await signInWithPopup(auth, githubProvider);
        const user = result.user;
        console.log("User Info:", user);

        return user;
    } catch (error) {
        console.error("Error during GitHub sign-in:", error);
    }
}