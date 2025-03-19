import { doc, setDoc } from "firebase/firestore";
import { openDB } from "idb";
import {db} from "./firebase-service";

const initIndexedDB = async () => {
    return openDB("users", 1, {
        upgrade(db) {
            db.createObjectStore("users", { keyPath: "uid" });
        },
    });
};

export const assignWandelaarRole = async (user: any) => {
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