## AI backend

De AI backend bestaat uit 3 files:
- app.py
- chatbot.py
- generation.py

#### app.py
App is de file die de connectie regelt met de front end. Je kan het op 2 manieren runnen. Als je app.py lokaal runt krijg je een lokale flask server waar je naar kan connecten met de front end. Als je de container runt krijg je een gunicorn server met 4 workers. Deze workers kunnen allemaal apart van elkaar taken uitvoeren, waardoor je meerdere requests tegelijk aan kan. Verder doet de app.py niet veel, het checkt waar de data vandaan komt en roept zo de juiste files aan.

#### chatbot.py 
Dit is de file die het chatbot gedeelte runt in een level. Het ontvangt data vanuit de front end waar informatie over het level en het doel van het level staat. Aan de hand van deze data maakt het een context aan die mee wordt gegeven samen met de vraag van de gebruiker, om zo een antwoord te krijgen van het model. 

#### generation.py
In deze file wordt het level generation gedeelte gedaan, aan de hand van de input van de gebruiker worden waardes in een standaard JSON format aangepast en teruggegeven aan de front end. Alle base waardes staan gespecificeerd in de promt en extra instructies voor het model om te volgen staan na de base waardes.

In app.py wordt de file 'api_key.txt' uitgelezen. Deze file bevat de api key van het account dat gekopelt is aan de modellen die gebruikt worden. Om deze api key te verkrijgen kijk in het overdrachtsdocument. 