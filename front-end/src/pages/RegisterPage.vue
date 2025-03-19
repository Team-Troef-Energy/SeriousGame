<script lang="ts">
import { defineComponent } from 'vue';
import { ref } from 'vue';
import { signUpEmailAndPassword } from '../utils/auth-service';

export default defineComponent({
    name: 'RegisterPage',
    setup() {
        const email = ref('');
        const password = ref('');

        function handleRegister(e: any) {
            e.preventDefault();
            signUpEmailAndPassword(email.value, password.value)
                .then(() => {
                    console.log('ingelogd!');
                })
                .catch((error: any) => {
                    window.alert(error);
                })
        }

        return {
            email,
            password,
            handleRegister
        };
    }
});
</script>

<template>
    <div class="register-page-container">
        <form @submit="handleRegister" class="register-form">
            <input v-model="email" type="email" placeholder="Enter email" required />
            <input v-model="password" type="password" placeholder="Enter password" required />
            <button type="submit" class="register-btn">Register</button>
        </form>
    </div>
</template>

<style scoped>
.register-page-container {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    height: 100%;
}

.register-form {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.register-form input {
    padding: 8px 20px;
    border: solid 1px rgba(0, 0, 0, .1);
    border-radius: 4px;
    margin: 3px 0;
    font-size: 14px;

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
</style>