<template>
    <div class="game-tutorial container">
        <div class="page-content">
            <div class="header">
                <h1>Game Uitleg</h1>
                <p>Leer hoe je het Troef spel speelt!</p>
            </div>
            <div class="content">
                <div class="content">
                    <section class="goal">
                        <h3 id="goal">Doel</h3>
                        <p></p>
                    </section>
                </div>
                <div class="table-of-contents">
                    <h2>Table of Contents</h2>
                    <ul>
                        <li><a href="#goal">Doel</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'GameTutorialPage',
    setup() {
    }
});
</script>

<style scoped>
.game-tutorial {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    gap: 1rem;
}

.game-tutorial::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('/green-happy-background.png');
    background-size: cover;
    z-index: -2;
}

.page-content {
    background-color: white;
    padding: 1rem;
    width: 95%;
    height: 100%;
}

.header {
    text-align: center;
    padding: 1rem;
}

.content {
    display: flex;
    justify-content: center;
    gap: 4rem;
    flex: 2;
}

.table-of-contents {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;
}
</style>