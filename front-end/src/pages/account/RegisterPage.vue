<template>
    <div class="register-page-container">
        <div class="register-page-content">

            <div class="auth-top">
                <div>
                    <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M20 21C20 19.6044 20 18.9067 19.8278 18.3389C19.44 17.0605 18.4395 16.06 17.1611 15.6722C16.5933 15.5 15.8956 15.5 14.5 15.5H9.5C8.10444 15.5 7.40665 15.5 6.83886 15.6722C5.56045 16.06 4.56004 17.0605 4.17224 18.3389C4 18.9067 4 19.6044 4 21M16.5 7.5C16.5 9.98528 14.4853 12 12 12C9.51472 12 7.5 9.98528 7.5 7.5C7.5 5.01472 9.51472 3 12 3C14.4853 3 16.5 5.01472 16.5 7.5Z"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                    <h2>Registreer een account</h2>
                </div>
                <p>Heb je al een account?<br><a href="/login" class="top-a">Ga gelijk door naar het inloggen</a></p>
            </div>

            <form @submit="handleRegister" class="register-form">
                <input v-model="email" type="email" placeholder="Voer uw e-mail adres in" required />
                <input v-model="password" type="password" placeholder="Voer uw wachtwoord in" required />
                <button type="submit" class="register-btn">
                    Registreer account
                    <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M12 16L16 12M16 12L12 8M16 12H8M7.8 21H16.2C17.8802 21 18.7202 21 19.362 20.673C19.9265 20.3854 20.3854 19.9265 20.673 19.362C21 18.7202 21 17.8802 21 16.2V7.8C21 6.11984 21 5.27976 20.673 4.63803C20.3854 4.07354 19.9265 3.6146 19.362 3.32698C18.7202 3 17.8802 3 16.2 3H7.8C6.11984 3 5.27976 3 4.63803 3.32698C4.07354 3.6146 3.6146 4.07354 3.32698 4.63803C3 5.27976 3 6.11984 3 7.8V16.2C3 17.8802 3 18.7202 3.32698 19.362C3.6146 19.9265 4.07354 20.3854 4.63803 20.673C5.27976 21 6.11984 21 7.8 21Z"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </button>
            </form>
            <p class="or">OF</p>
            <div class="providers-container">
                <button @click="handleGoogleRegister">
                    <img src="/images/account/google_icon.png" alt="Google icon">
                    Registreer met Google
                </button>
                <button @click="handleGitHubRegister">
                    <img src="/images/account/github_icon.png" alt="GitHub icon">
                    Registreer met GitHub
                </button>
            </div>
            <div class="permission-box">
                <input type="checkbox" v-model="permission">
                <p>Door een account aan te maken, bevestig ik dat ik akkoord ga met de <a class="terms-a"
                        href="/terms">voorwaarden</a>.</p>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { authenticationService } from '../../services/firebase/AuthenticationService';

export default defineComponent({
    name: 'RegisterPage',
    setup() {
        const permission = ref(false);
        const email = ref('');
        const password = ref('');
        const noPermissionMessage = 'Je moet akkoord gaan met de voorwaarden.';

        const goToHomePage = () => {
            window.location.assign('/');
        };

        function handleRegister(e: any) {
            e.preventDefault();

            if (!permission.value) {
                window.alert(noPermissionMessage);
                return;
            }

            authenticationService.signUpEmailAndPassword(email.value, password.value)
                .then(() => {
                    goToHomePage();
                })
                .catch((error: any) => {
                    window.alert(error);
                })
        }

        async function handleGoogleRegister(e: any) {
            e.preventDefault();

            if (!permission.value) {
                window.alert(noPermissionMessage);
                return;
            }

            try {
                await authenticationService.registerWithGoogle();
                goToHomePage();
            } catch (error) {
                window.alert(error);
            }
        };

        async function handleGitHubRegister(e: any) {
            e.preventDefault();

            if (!permission.value) {
                window.alert(noPermissionMessage);
                return;
            }

            try {
                await authenticationService.registerWithGitHub();
                goToHomePage();
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
    height: 90vh;
}

.register-page-content {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    flex-direction: column;
    max-width: 300px;
}

.auth-top {
    width: 100%;
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    margin-bottom: 30px;
}

.auth-top div {
    display: flex;
    align-items: center;
    gap: 8px;
}

.auth-top svg {
    width: 20px;
    height: 20px;
    margin-top: -2px;
}

.auth-top h2 {
    margin: 0;
    font-size: 1.3rem;
}

.auth-top p {
    margin: 5px 0 0 0;
    text-align: center;
    font-size: 13px;
}

.top-a {
    color: var(--troef-blue);
    text-decoration: none;
}

.top-a:hover {
    text-decoration: underline;
    cursor: pointer;
}

.register-form {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

.register-form input {
    padding: 8px 14px;
    border: solid 1px rgba(0, 0, 0, .1);
    border-radius: 4px;
    margin: 3px 0;
    font-size: 13px;
    width: 100%;
}

.register-form input:focus {
    outline: rgba(0, 0, 0, .2) solid 1px;
}

.register-btn {
    width: 100%;
    border-radius: 4px;
    padding: 6px 14px;
    background-color: var(--troef-blue);
    margin: 4px 0 0 0;
    color: white;
    font-size: 13px;
    height: 36px;
    display: flex;
    align-items: center;
    gap: 6px;
    justify-content: center;
}

.register-btn svg {
    width: 16px;
    height: 16px;
}

.register-btn:hover {
    cursor: pointer;
    background-color: var(--troef-blue-hover);
}

.register-btn:focus {
    outline: none;
}

.or {
    font-size: 12px;
    color: rgba(0, 0, 0, .6);
    margin: 15px 0 10px 0;
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
    border-radius: 4px;
    margin-bottom: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
}

.providers-container button:hover {
    cursor: pointer;
    background-color: rgb(245, 245, 245);
}

.providers-container button img {
    width: 16px;
    height: 16px;
}

.permission-box {
    display: flex;
    align-items: start;
    gap: 10px;
    margin-top: 20px;
}

.permission-box p {
    font-size: 13px;
    margin-top: -3px;
}

.permission-box input:hover {
    cursor: pointer;
}

.terms-a {
    color: var(--troef-blue);
    text-decoration: none;
}

.terms-a:hover {
    text-decoration: underline;
    cursor: pointer;
}
</style>