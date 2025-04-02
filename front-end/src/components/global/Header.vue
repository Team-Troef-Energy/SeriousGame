<template>
    <header>
        <div class="header-grid">
            <a href="/">
                <img src="/troef-logo.png" alt="logo" />
            </a>
            <div class="header-links">
                <a href="/">Home</a>
                <a href="/terms">Terms</a>
                <a href="/dashboard">Dashboard</a>

                <template v-if="user">
                    <p class="user-email">{{ trimString(user.email) }}</p>
                    <button class="logoutbtn" @click="logout">Log uit</button>
                </template>
                <template v-else>
                    <a href="/register">Register</a>
                    <a href="/login">Login</a>
                </template>

            </div>
        </div>
    </header>
</template>

<script setup lang="ts">
import { signOut } from 'firebase/auth';
import { inject } from 'vue';
import { AuthContext } from '../../context/AuthProvider';
import { auth } from '../../utils/firebase-service';

const authState = inject(AuthContext);

if (!authState) {
    throw new Error('AuthProvider is missing.');
}

const { user, setUser }: any = authState;

async function logout() {
    try {
        await signOut(auth);
        setUser(null);
        console.log("User logged out successfully");
    } catch (error) {
        console.error("Logout failed:", error);
    }
}

function trimString(string: string) {
    const length = 10;
    return string.substring(0, length) + '...';
}
</script>

<style>
header {
    text-align: center;
    padding: 1em;
    background: linear-gradient(to right, #eeffff 80%, #e3d26b);
    font-size: 0.4rem;
    color: #000000;
}

header img {
    height: 40px;
}

header h1 {
    margin: 0;
    font-size: 3em;
    color: #3b3b3b;
    font-weight: 600;
    grid-column: 2;
    text-align: center;
}

.header-grid {
    display: flex;
    justify-content: space-between;
    margin: 0 10px;
    align-items: center;
}

.header-links {
    gap: 20px;
    display: flex;
    align-items: center;
}

.header-links a {
    font-size: 15px;
    color: black;
}

.header-links a:hover {
    text-decoration: underline;
}

.logoutbtn {
    padding: 8px 20px;
    background-color: white;
    border: rgba(0, 0, 0, .1) solid 1px;
    font-size: 13px;
    border-radius: 3px;
}

.user-email {
    font-size: 14px;
}
</style>