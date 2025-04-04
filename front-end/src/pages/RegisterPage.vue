<template>
    <div class="register-page-container">

      <div class="register-page-content">
            <form @submit="handleRegister" class="register-form">
                <input v-model="email" type="email" placeholder="Enter email" required />
                <input v-model="password" type="password" placeholder="Enter password" required />
                <button type="submit" class="register-btn">Register</button>
            </form>
            <div class="providers-container">
                <button @click="handleGoogleRegister">
                    <img src="../assets/google_icon.png" alt="Google icon">
                    Register met Google
                </button>
                <button @click="handleGitHubRegister">
                    <img src="../assets/github_icon.png" alt="GitHub icon">
                    Register met GitHub
                </button>
            </div>
            <div class="permission-box">
                <input type="checkbox" v-model="permission">
                <p>Door een account aan te maken, bevestig ik dat ik akkoord ga met de <a href="/terms">voorwaarden</a>.</p>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { authenticationService } from '../services/firebase/AuthenticationService';

export default defineComponent({
    name: 'RegisterPage',
    setup() {
        const permission = ref(false);
        const email = ref('');
        const password = ref('');

        function handleRegister(e: any) {
            e.preventDefault();

            if (!permission.value) {
                window.alert('You have to check permission.');
                return;
            }

            authenticationService.signUpEmailAndPassword(email.value, password.value)
                .then(() => {
                    console.log('ingelogd!');
                })
                .catch((error: any) => {
                    window.alert(error);
                })
        }

        async function handleGoogleRegister(e: any) {
            e.preventDefault();
            
            if (!permission.value) {
                window.alert('You have to check permission.');
                return;
            }

            try {
                await authenticationService.registerWithGoogle();
            } catch (error) {
                window.alert(error);
            }
        };

        async function handleGitHubRegister(e: any) {
            e.preventDefault();

            if (!permission.value) {
                window.alert('You have to check permission.');
                return;
            }

            try {
                await authenticationService.registerWithGitHub();
                window.location.reload();
            } catch (error) {
                window.alert(error);
            }
        }

        return {
            permission,
            email,
            password,
            handleRegister,
            handleGoogleRegister,
            handleGitHubRegister
        };
    }
});
</script>

<style scoped>
.register-page-container {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    height: 100%;
}

.register-page-content {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    flex-direction: column;
    max-width: 300px;
}

.register-form {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

.register-form input {
    padding: 8px 20px;
    border: solid 1px rgba(0, 0, 0, .1);
    border-radius: 4px;
    margin: 3px 0;
    font-size: 14px;
    width: 100%;
}

.register-btn {
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
    margin-bottom: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
}

.providers-container button img {
    width: 16px;
    height: 16px;
}

.permission-box {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-top: 20px;
}

.permission-box p {
    font-size: 12px;
}

.permission-box input:hover {
    cursor: pointer;
    transform: scale(1.2);
}
</style>