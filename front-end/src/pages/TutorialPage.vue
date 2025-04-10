<template>
    <div class="tutorial-page container">
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
                            Elk level speelt zich af in een wijk met meerdere huizen en een transformator. Aan elk huis
                            kan je zonnepanelen toewijzen om zo groen stroom op te wekken.
                            Daarnaast kun je batterijen
                            plaatsen bij zowel de huizen als de transformator.
                            Maar let op: je hebt een beperkt budget!
                            Je wint elk level alleen als je slimme keuzes maakt, door binnen het budget te blijven én
                            tegelijkertijd voldoende CO2
                            te besparen.
                        </p>
                    </section>
                    <section class="dashboard">
                        <h3 id="dashboard">Het Dashboard</h3>
                        <p> Het dashboard bevat meerdere waarden. Waar elke waarden voor staat, staat in het
                            onderstaande tabel weergegeven.
                        </p>
                        <table>
                            <thead>
                                <tr>
                                    <th>Waarden</th>
                                    <th>Betekenis</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Huidige munten</td>
                                    <td>De hoeveelheid munten dat je momenteel hebt gespendeerd. Munten heb je nodig
                                        voor zonnepanelen en batterijen.</td>
                                </tr>
                                <tr>
                                    <td>Max. aantal munten</td>
                                    <td>De maximale munten is eigenlijk het budget. Je kan over het budget heen gaan,
                                        maar dan heb je het level niet gehaald. Zorg er dus voor dat de huidige munten
                                        kleiner of gelijk zijn aan het maximaal aantal munten dat je mag gebruiken.</td>
                                </tr>
                                <tr>
                                    <td>Huidige CO2-uitstoot</td>
                                    <td>De hoeveelheid CO2 in kg dat wordt uitgestoten door de gehele wijk.</td>
                                </tr>
                                <tr>
                                    <td>Max. CO2-uitstoot</td>
                                    <td>De maximale hoeveelheid CO2 in kg dat de wijk mag uitstoten. De huidige
                                        CO2-uitstoot moet gelijk of onder de maximale CO2-uitstoot zitten om het level
                                        te kunnen behalen.</td>
                                </tr>
                                <tr>
                                    <td>Totale energieconsumptie</td>
                                    <td>De hoeveelheid energie in kWh dat wordt uitgestoken door de gehele wijk.</td>
                                </tr>
                                <tr>
                                    <td>Groen geproduceerde energiepercentage</td>
                                    <td>Het percentage groene stroom ten opzichte van de totale energieproductie.</td>
                                </tr>
                                <tr>
                                    <td>Start- en eindtijd</td>
                                    <td>Het dag bereik waar in het level plaatsvindt. Dit kan lopen van 0h tot 24h, of
                                        te wel van 00:00 t/m 23:59 uur.</td>
                                </tr>
                                <tr>
                                    <td>Seizoen</td>
                                    <td>Het seizoen waarin het level plaatsvindt. Dit kunnen de volgende seizoen zijn:
                                        herfst, winter, lente en zomer. </td>
                                </tr>
                            </tbody>
                        </table>
                    </section>
                    <section class="transformer">
                        <h3 id="transformer">De Transformator</h3>
                        <p>
                            De transformator zet de hoogspanning van het elektriciteitsnet om in laagspanning die
                            geschikt is voor de huizen. Vervolgens verdeelt hij de stroom naar de huizen in de wijk.
                            Wanneer er een tekort aan energie is, gebruikt de transformator eerst de energie die is
                            opgeslagen in zijn batterijen. Als die batterijen niet aanwezig zijn of leeg zijn, schakelt
                            de transformator over op energie van het elektriciteitsnet.
                        </p>

                        <p><b>Hint:</b> De energie van het elektriciteitsnet
                            is niet volledig groen, dus we willen zo min mogelijk energie daar vandaan halen. </p>
                    </section>
                    <section class="solar-panels">
                        <h3 id="solar-panels">Zonnepanelen</h3>
                        <p>
                            Zonnepanelen kan je op huizen plaatsen. Ze zetten zonlicht om in elektriciteit. De
                            hoeveelheid elektriciteit die ze
                            produceren, hangt af van de hoeveelheid zonlicht die ze ontvangen. Dit kan variëren
                            afhankelijk van het seizoen en het tijdstip van de dag. Het seizoen en tijdstip van de dag
                            kan je vinden op het <i><a href="#dashboard">dashboard</a></i>.
                        </p>
                    </section>
                    <section class="batteries">
                        <h3 id="batteries">Batterijen</h3>
                        <p>
                            Batterijen slaan ongebruikte energie op, die weer gebruikt kan worden op momenten van
                            energietekort. Ongebruikte energie is de energie die niet door de huizen wordt verbruikt.
                            Dit kan bijvoorbeeld zijn omdat de huizen op dat moment geen energie nodig hebben, of omdat
                            er teveel energie is geproduceerd door de zonnepanelen. De ongebruikte energie wordt
                            teruggeleverd aan het elektriciteitsnet, wanneer de batterijen niet aanwezig of vol zijn.
                        </p>

                        <p>Batterijen kunnen worden geplaatst bij zowel de huizen als de <i><a href="#transformer">transformator</a></i>.</p>
                    </section>
                </div>
                <div class="table-of-contents">
                    <h2>Inhoudsopgave</h2>
                    <ul>
                        <li><a href="#what-is-the-game">Wat is het Troef serious spel?</a></li>
                        <li><a href="#goal">Doel</a></li>
                        <li><a href="#dashboard">Dashboard</a></li>
                        <li><a href="#transformer">Transformator</a></li>
                        <li><a href="#solar-panels">Zonnepanelen</a></li>
                        <li><a href="#batteries">Batterijen</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'TutorialPage',
    setup() {
    }
});
</script>

<style scoped>
.tutorial-page {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 94vh;
    gap: 1rem;
}

.tutorial::after {
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
    width: 95%;
    height: 100%;
    overflow: auto;
}

.header {
    text-align: center;
    position: relative;
    padding: 1rem;
    background-color: var(--troef-green);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 5;
}

.content {
    display: flex;
    padding-left: 5%;
    height: 85%;
}

.tutorial {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
    flex: 3;
    overflow: auto;
    padding: 1rem 0rem 1rem 0rem;
}

.table-of-contents {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;
    background-color: rgb(249 249 249);
    padding-top: 1rem;
}

ul {
    list-style-type: none;
    padding-top: 0.5rem;
    line-height: 1.7rem;
}

li {
    width: 15rem;
}

a {
    text-decoration: none;
    color: inherit;
}

a[href]:hover {
    font-weight: bold;
    color: var(--troef-green);
}

section {
    width: 100%;
}

section p {
    padding-bottom: 0.5rem;
}

table {
    border: 2px solid var(--troef-green);
    border-radius: 3px;
    background-color: #fff;
}

th {
    background-color: var(--troef-green);
    color: rgb(255, 255, 255);
}

td {
    background-color: #f9f9f9;
}

th,
td {
    width: 45%;
    padding: 10px 20px;
}
</style>