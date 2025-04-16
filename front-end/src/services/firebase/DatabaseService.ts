import { collection, doc, getDocs, setDoc } from "firebase/firestore";
import { openDB } from "idb";
import { firebaseService } from "./FirebaseService";

class DatabaseService {
    private indexedDBName = "users";
    private indexedDBVersion = 1;

    /**
     * Initializes IndexedDB for local storage.
     * @returns A promise that resolves to the IndexedDB instance.
     */
    private async initIndexedDB() {
        return openDB(this.indexedDBName, this.indexedDBVersion, {
            upgrade(db) {
                if (!db.objectStoreNames.contains("users")) {
                    db.createObjectStore("users", { keyPath: "uid" });
                }
            },
        });
    }

    async assignUserRole(user: any) {
        const userData = {
            uid: user.uid,
            email: user.email,
            role: "user",
            assignedAt: new Date().toISOString(),
        };
    
        // Save in Firestore
        await setDoc(doc(firebaseService.db, "users", user.uid), userData);
    
        const idb = await this.initIndexedDB();
        await idb.put("users", userData);
    };

    /**
     * Assigns a user by saving their data to Firestore and IndexedDB.
     * @param user - The user object to assign.
     */
    async assignUser(user: any) {
        const userData = {
            uid: user.uid,
            email: user.email,
            role: "user",
            assignedAt: new Date().toISOString(),
        };

        // Save to Firestore
        await setDoc(doc(firebaseService.db, "users", user.uid), userData);

        // Save to IndexedDB
        const idb = await this.initIndexedDB();
        await idb.put("users", userData);
    }

    /**
     * Fetches all users from Firestore.
     * @returns A promise that resolves to an array of user data.
     */
    async getAllUsers() {
        const usersCollectionRef = collection(firebaseService.db, "users"); // Reference to the "users" collection
        const querySnapshot = await getDocs(usersCollectionRef); // Fetch all documents

        let users: any[] = [];

        if (querySnapshot.empty) {
            console.warn("No users found!");
        } else {
            querySnapshot.forEach(doc => {
                users.push(doc.data());
            });
        }

        return users;
    }
}

export const databaseService = new DatabaseService();