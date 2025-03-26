<template>
    <header>
        <div class="header-grid">
            <a href="/">
                <img src="/troef-logo.png" alt="logo" />
            </a>
            <div class="header-links">
                <a href="/">Home</a>
                <a href="/register">Register</a>
                <a href="/terms">Terms</a>
            </div>
        </div>
    </header>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    name: 'Header',
});
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
</style>