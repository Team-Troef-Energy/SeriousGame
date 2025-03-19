// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import {getFirestore} from "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyBWdp7HAsncEm08RIkhkOK3_Z_A7yG4GMU",
    authDomain: "energygame-57639.firebaseapp.com",
    projectId: "energygame-57639",
    storageBucket: "energygame-57639.firebasestorage.app",
    messagingSenderId: "1076144260668",
    appId: "1:1076144260668:web:6fa4a660cd4cb8cc51e539"
  };

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
export const db = getFirestore(app);