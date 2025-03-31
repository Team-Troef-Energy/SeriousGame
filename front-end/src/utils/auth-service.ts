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

export async function signUpEmailAndPassword(email: string, password: string) {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    return userCredential.user;
};

export async function registerWithGoogle() {
    const provider = new GoogleAuthProvider();
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
    const provider = new GithubAuthProvider();
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