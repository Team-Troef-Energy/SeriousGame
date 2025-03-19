import { openDB } from "idb";
import { db } from "./firebase-service";
import "firebase/database";
import { collection, doc, getDoc, getDocs, setDoc } from "firebase/firestore";

const initIndexedDB = async () => {
    return openDB("users", 1, {
        upgrade(db) {
            db.createObjectStore("users", { keyPath: "uid" });
        },
    });
};

export const assignUser = async (user: any) => {
    const userData = {
        uid: user.uid,
        email: user.email,
        assignedAt: new Date().toISOString(),
    };

    // Opslaan in Firestore
    await setDoc(doc(db, "users", user.uid), userData);

    const idb = await initIndexedDB();
    await idb.put("users", userData);
};

export async function getAllUsers() {
    const usersCollectionRef = collection(db, "users"); // Reference to the "users" collection
    const querySnapshot = await getDocs(usersCollectionRef); // Fetch all documents

    let users: any = [];

    if (querySnapshot.empty) {
        console.warn("No users found!");
    } else {
        querySnapshot.forEach(doc => {
            users.push(doc.data());
        });
    }

    return users;
}