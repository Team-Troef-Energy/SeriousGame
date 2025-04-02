<template>
    <div class="login-page-container">

        <div class="login-page-content">
            <form @submit="handleLogin" class="login-form">
                <input v-model="email" type="email" placeholder="Enter email" required />
                <input v-model="password" type="password" placeholder="Enter password" required />
                <button type="submit" class="login-btn">Login</button>
            </form>
            <div class="providers-container">
                <button @click="handleGoogleLogin">
                    <img src="../assets/google_icon.png" alt="Google icon">
                    Login met Google
                </button>
                <button @click="handleGitHubLogin">
                    <img src="../assets/github_icon.png" alt="GitHub icon">
                    Login met GitHub
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { authenticationService } from '../services/firebase/AuthenticationService';

export default defineComponent({
    name: 'LoginPage',
    setup() {
        const email = ref('');
        const password = ref('');

        function handleLogin(e: any) {
            e.preventDefault();
            authenticationService.signInEmailAndPassword(email.value, password.value)
                .then(() => {
                    console.log('ingelogd!');
                })
                .catch((error: any) => {
                    window.alert(error);
                })
        }

        async function handleGoogleLogin() {
            try {
                const userData = await authenticationService.signInGoogle();
            } catch (error) {
                console.error(error);
                window.location.assign('/register');
            }
        }

        async function handleGitHubLogin() {
            try {
                const userData = await authenticationService.signInWithGitHub();
            } catch (error) {
                console.error(error);
                window.location.assign('/register');
            }
        }

        return {
            email,
            password,
            handleLogin,
            handleGoogleLogin,
            handleGitHubLogin
        };
    }
});
</script>

<style scoped>
.login-page-container {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    height: 100%;
}

.login-page-content {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    flex-direction: column;
    max-width: 300px;
}

.login-form {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

.login-form input {
    padding: 8px 20px;
    border: solid 1px rgba(0, 0, 0, .1);
    border-radius: 4px;
    margin: 3px 0;
    font-size: 14px;
    width: 100%;
}

.login-btn {
    width: 100%;
    border-radius: 4px;
    padding: 6px 12px;
    background-color: rgb(21, 129, 230);
    margin: 4px 0 0 0;
    color: white;
    font-size: 14px;
}

.providers-container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    margin-top: 5px;
    width: 100%;
}

.providers-container button {
    border: solid 1px rgba(0, 0, 0, .1);
    background-color: white;
    padding: 8px 20px;
    width: 100%;
    font-size: 13px;
    display: flex;
    margin-bottom: 5px;
    align-items: center;
    justify-content: center;
    gap: 8px;
}

.providers-container button img {
    width: 16px;
    height: 16px;
}
</style>