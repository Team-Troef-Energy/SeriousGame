import { initializeApp } from "firebase/app";
import { Auth, getAuth } from "firebase/auth";
import { Firestore, getFirestore } from "firebase/firestore";

class FirebaseService {
    private app;
    public auth: Auth;
    public db: Firestore;

    constructor() {
        // Firebase configuration
        const firebaseConfig = {
            apiKey: "AIzaSyBWdp7HAsncEm08RIkhkOK3_Z_A7yG4GMU",
            authDomain: "energygame-57639.firebaseapp.com",
            projectId: "energygame-57639",
            storageBucket: "energygame-57639.firebasestorage.app",
            messagingSenderId: "1076144260668",
            appId: "1:1076144260668:web:6fa4a660cd4cb8cc51e539"
        };

        // Initialize Firebase
        this.app = initializeApp(firebaseConfig);
        this.auth = getAuth(this.app);
        this.db = getFirestore(this.app);
    }
}

export const firebaseService = new FirebaseService();