<template>
    <div class="terms-container">
        <div class="terms-content">
            <h3>1. Inleiding</h3>
            <p>Welkom bij de Troef Energie Game! Door deze game te spelen, gaat u akkoord met de onderstaande
                voorwaarden.
                Lees
                deze voorwaarden zorgvuldig door voordat u een account aanmaakt.</p>

            <h3>2. Gebruikersgegevens</h3>
            <p>Bij het registreren in de Troef Energie Game wordt u gevraagd de volgende gegevens te verstrekken:</p>
            <ul>
                <li><strong>E-mailadres</strong> – Nodig voor accountverificatie en communicatie.</li>
                <li><strong>Wachtwoord</strong> – Voor beveiligde toegang tot uw account.</li>
                <li><strong>Naam</strong> – Voor personalisatie binnen de game.</li>
                <li><strong>Rol</strong> – Geeft aan of u een speler, beheerder of andere specifieke gebruiker bent.
                </li>
            </ul>

            <h3>3. Gebruik van Gegevens</h3>
            <p>Uw gegevens worden uitsluitend gebruikt voor:</p>
            <ul>
                <li>Toegang tot en functionaliteit binnen de game.</li>
                <li>Communicatie over updates, onderhoud en relevante meldingen.</li>
                <li>Analyse en verbetering van de spelervaring.</li>
            </ul>
            <p>Wij zullen uw gegevens nooit delen met derden zonder uw expliciete toestemming, tenzij wettelijk
                verplicht.
            </p>

            <h3>4. Account en Beveiliging</h3>
            <ul>
                <li>U bent verantwoordelijk voor het beveiligen van uw wachtwoord en accountgegevens.</li>
                <li>Het is niet toegestaan om uw account te delen met anderen.</li>
                <li>Troef Energie behoudt zich het recht voor om accounts te blokkeren bij misbruik of overtreding van
                    deze
                    voorwaarden.</li>
            </ul>

            <h3>5. Intellectueel Eigendom</h3>
            <p>Alle content, inclusief maar niet beperkt tot teksten, afbeeldingen en gameplaymechanieken, is eigendom
                van
                Troef
                Energie en mag niet zonder toestemming worden gekopieerd of verspreid.</p>

            <h3>6. Aansprakelijkheid</h3>
            <ul>
                <li>Troef Energie is niet aansprakelijk voor eventuele schade, verlies van gegevens of onderbrekingen in
                    de
                    spelervaring.</li>
                <li>De game wordt aangeboden “zoals deze is”, zonder garanties op ononderbroken beschikbaarheid.</li>
            </ul>

            <h3>7. Wijzigingen in de Voorwaarden</h3>
            <ul>
                <li>Troef Energie kan deze voorwaarden op elk moment wijzigen.</li>
                <li>Bij belangrijke wijzigingen zullen gebruikers op de hoogte worden gesteld.</li>
                <li>Voortgezet gebruik van de game na wijzigingen betekent acceptatie van de nieuwe voorwaarden.</li>
            </ul>

            <h3>8. Contact</h3>
            <p>Voor vragen over deze voorwaarden kunt u contact opnemen via <a
                    href="mailto:energygame@hu.nl">energygame@hu.nl</a>.</p>

            <p>Door verder te gaan en een account aan te maken, bevestigt u dat u deze voorwaarden hebt gelezen en ermee
                akkoord
                gaat.</p>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'TermsPage',
    setup() {
    }
});
</script>

<style scoped>
.terms-container {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
}

.terms-content {
    width: 100%;
    margin-top: 50px;
    max-width: 1100px;
    display: flex;
    align-items: start;
    flex-direction: column;
}

.terms-content ul {
    margin-left: 20px;
    padding: 0 20px;
}
</style>