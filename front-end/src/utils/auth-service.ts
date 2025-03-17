import { auth } from "./firebase-service";
import { createUserWithEmailAndPassword } from "firebase/auth";

export async function signUpEmailAndPassword(email: string, password: string) {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    return userCredential.user;
};