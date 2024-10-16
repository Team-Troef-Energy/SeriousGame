# SeriousGame

## Git-strategie
Om er voor te zorgen dat iedereen in het team dezelfde git strategie hanteert, is het belangrijk dat iedereen de volgende stappen volgt:
### 1. Branches opzetten voor jouw taak
1. Per **user story** waar aan gewerkt gaat worden moet er een nieuwe branch aangemaakt worden (gebaseerd op de main branch). Controleer wel eerst of deze al bestaat. De naam van deze branch moet beginnen met "us" en de **user story** ID, gevolgd door een korte omschrijving van de **user story**. Bijvoorbeeld: `us-1-backend-structuur`.
2. Voor elke development taak binnen de **user story** moet er een nieuwe branch aangemaakt worden. De naam van deze branch moet beginnen met "t" en de **task** ID, gevolgd door een korte omschrijving van de development taak. Bijvoorbeeld: `t-2-controllers`.
### 2. Aan jouw taak werken
* Als je aan jouw taak gaat werken dan moet je eerst de branch van de **task** waar je aan werkt ophalen. Dit doe je door bijvoorbeeld`git checkout us-1-backend-structuur` uit te voeren of door de GUI te gebruiken.
In deze **task** branch kan er vervolgens gewerkt worden aan de development taak.
### 3. Pull request aanmaken na taak afronding
* Als de development taak (**task**) afgerond is, dan moet er een pull request aangemaakt worden. Elke pull request van een **task** moet gemaakt worden naar de **user story** branch die bij de task hoort!
Een aantal afspraken:
### 4. User story afronden
Nadat alle **tasks** binnen een **user story** afgerond zijn, dan kan de **user story** afgerond worden. Dit gebeurt door een **pull request** aan te maken van de **user story** branch naar de main branch. Deze **pull request** moet minimaal 1 reviewer hebben. Deze reviewer moet een andere persoon zijn dan degene die de pull request heeft aangemaakt.
### Commit afspraken
1. **Commits** moeten klein en overzichtelijk zijn. Probeer niet te veel code in één **commit** te stoppen.
2. **Commits** moeten een duidelijke omschrijving hebben. Zorg er voor dat er omschreven is waar er specifiek aan gewerkt is en dat het traceerbaar is voor iedereen die het voor het eerst leest.
3. **Commits** moeten in het Nederlands zijn.
4. **Commits** moeten in de **task** branch gemaakt worden.
### Pull request afspraken
1. **Pull requests** moeten een duidelijke omschrijving hebben. Zorg er voor dat er omschreven is waar er specifiek aan gewerkt is en dat het traceerbaar is voor iedereen die het voor het eerst leest.
2. **Pull requests** moeten in het Nederlands zijn.
3. **Pull requests** moeten minimaal 1 reviewer hebben. Deze reviewer moet een andere persoon zijn dan degene die de pull request heeft aangemaakt.
4. **Pull requests** moeten specifiek blijven aan de **task** waar aan gewerkt is. Dat betekend dat als er meerdere **tasks** in de **user story** zitten, dan moet er per **task** minimaal één **pull request** aangemaakt worden.
