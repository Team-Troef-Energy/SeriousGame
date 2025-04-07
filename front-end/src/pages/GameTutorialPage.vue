<template>
    <div class="game-tutorial container">
        <div class="page-content">
            <div class="header">
                <h1>Game Uitleg</h1>
                <p>Leer hoe je het Troef serious spel speelt!</p>
            </div>
            <div class="content">
                <div class="tutorial">
                    <section class="what-is-the-game">
                        <h3 id="what-is-the-game">Wat is het Troef serious spel?</h3>
                        <p> Het Troef serious spel is een educatief spel dat is ontworpen om spelers te helpen de
                            concepten en uitdagingen van de energietransitie te begrijpen. Het spel is bedoeld voor
                            zowel studenten als burgers en ambtenaren.
                        </p>
                    </section>
                    <section class="goal">
                        <h3 id="goal">Het doel</h3>
                        <p> Het doel van het spel is om de hoeveelheid CO2-uitstoot te verminderen en dat doe je met
                            behulp van groen stroom!
                            Elk level speelt zich af in een wijk met meerdere huizen en een transformator. Je wekt
                            groene stroom op door zonnepanelen aan de huizen toe te voegen. Daarnaast kun je batterijen
                            plaatsen bij zowel de huizen als de transformator.
                            Maar let op: je hebt een beperkt budget!
                            Je wint elk level alleen als je slimme keuzes maakt, binnen het budget blijft én genoeg CO2
                            weet te besparen.
                        </p>
                    </section>
                </div>
                <div class="table-of-contents">
                    <h2>Table of Contents</h2>
                    <ul>
                        <li><a href="#what-is-the-game">Wat is het Troef serious spel?</a></li>
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
    overflow: auto;
}

.header {
    text-align: center;
    padding: 1rem;
}

.content {
    display: flex;
    gap: 4rem;
    padding-left: 5%;
}

.tutorial {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
    flex: 2;
}

.table-of-contents {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;
}
</style>