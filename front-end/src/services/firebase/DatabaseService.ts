import { collection, doc, getDocs, setDoc, where, query, updateDoc } from "firebase/firestore";
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

    async getCurrentUserRole() {
        return new Promise((resolve, reject) => {
            firebaseService.auth.onAuthStateChanged(async (user: any) => {
                if (user) {
                    try {
                        const userWithRole: any = await databaseService.getUserByEmail(user.email);
                        resolve(userWithRole.role);
                    } catch (error) {
                        reject(error);
                    }
                } else {
                    resolve("user");
                }
            });
        });
    }

    async updateUserRole(email: string, newRole: string): Promise<void> {
        try {
            // Find user with correspondig email
            const usersCollection = collection(firebaseService.db, 'users');
            const q = query(usersCollection, where('email', '==', email));
            const querySnapshot = await getDocs(q);

            if (querySnapshot.size > 1) {
                console.error(`Multiple users found with email: ${email}`);
                throw new Error(`Multiple users found with email: ${email}. Please ensure email uniqueness.`);
            }

            // Get the single matching user document
            const userDoc = querySnapshot.docs[0];
            const userData = userDoc.data();
            const userId = userDoc.id;

            // Update the role in memory
            const updatedUserData = {
                ...userData,
                role: newRole
            };

            // Update the document in Firestore
            const userRef = doc(firebaseService.db, 'users', userId);
            await setDoc(userRef, updatedUserData, { merge: true });
        } catch (error: any) {
            console.error(`Failed to update user with email ${email}:`, error);
            throw new Error(`Failed to update user: ${error.message}`);
        }
    }
}

export const databaseService = new DatabaseService();