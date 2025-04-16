import { collection, doc, getDocs, setDoc, where, query } from "firebase/firestore";
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

    /**
     * Assigns a user by saving their data to Firestore and IndexedDB.
     * @param user - The user object to assign.
     */
    async assignUser(user: any) {
        const userData = {
            uid: user.uid,
            email: user.email,
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

    /**
     * Fetches a single user from Firestore by their email.
     * @param email - The email of the user to fetch.
     * @returns A promise that resolves to the user data, or null if not found.
     */
    async getUserByEmail(email: string) {
        const usersCollectionRef = collection(firebaseService.db, "users");
        const userQuery = query(usersCollectionRef, where("email", "==", email));
        const querySnapshot = await getDocs(userQuery);

        if (querySnapshot.empty) {
            console.warn(`No user found with email: ${email}`);
            return null;
        }

        return querySnapshot.docs[0].data();
    }
}

export const databaseService = new DatabaseService();