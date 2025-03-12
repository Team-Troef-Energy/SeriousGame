<template>
    <header>
        <div class="header-grid">
            <img src="/troef-logo.png" alt="logo" />
            <h1>TROEF Energy - Serious game</h1>
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

.header-grid {
    display: grid;
    grid-template-columns: 50px 1fr 50px;
    align-items: center;
}

header img {
    height: 50px;
    grid-column: 1;
}

header h1 {
    margin: 0;
    font-size: 3em;
    color: #3b3b3b;
    font-weight: 600;
    grid-column: 2;
    text-align: center;
    visibility: hidden;
}

@media (min-width: 640px) {
  header h1 {
    visibility: visible;
  }
}
</style>