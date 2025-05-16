from flask import Flask, request, jsonify
from flask_cors import CORS
from transformers import pipeline

app = Flask(__name__)
CORS(app)  # Staat alle domeinen toe, pas dit aan als nodig

@app.route("/python-data", methods=["POST"])
def get_data():
    """
    Ontvangt de data van de front-end en verwerkt deze in het model

    returns: string antwoord van het model
    """
    # ontvang de data die doorgestuurd wordt
    data = request.get_json()

    match data['dest']:
        case "level":
            response = chatbot(data)
        case "admin":
            response = level_generation(data)

    return jsonify({"response": f"{response}".strip()})

def chatbot(data: dict) -> str:
    # filter de vraag uit de data
    question = data['inputMessage']
    # maak de context met de data
    context = create_context(data)

    # roep het model aan wat gebruikt wordt
    generator = pipeline("text2text-generation", model="google/flan-t5-large")
    # maak de prompt aan de hand van de vraag en de context
    prompt = f"""
Context:
{context}

Question:
{question}
"""
    print(prompt)
    # roep het model aan 
    response = generator(prompt, max_length=50)[0]
    print(response)

    return response['generated_text']

def create_context(data: dict) -> str:
    """
    Maak de context aan de hand van de huidige level data die doorgestuurd wordt.

    data: Dictionary, bevat alle informatie over de huidige staat van het level

    returns: string van de context uitgeschreven uit de data die gegeven is
    """
    # pak verschillende waardes zodat het makkelijker wordt om te indexen
    transformer = data['transformers'][0]
    dashboard = data['dashboard']

    # Alle huizen in de transformer pakken en een kleine zin maken over de statistieken die erbij horen
    context_list_house = [
    f"House {id+1}: amount of solarpanels: {house['solarpanels']}, production: {round(house['production'], 2)}, Batteries: {house['batteries']['amount']}\
, Electricity: {house['current']['direction']}, Consumption: {house['consumption']}"
    for id, house in enumerate(transformer['houses'])
    ]
    print(dashboard)
    # informatie dashboard pakken en in zinnen zetten
    context_string_dashboard = f"This level has this general information: \ncoins in use is {dashboard['coinsUsed']} and the \
maximum amount is {dashboard['maxCoins']}\n\
current amount CO2: {dashboard['currentCO2']}\
"   
    # dashboard string toevoegen aan de lijst van huisstrings
    context_list_house.append(context_string_dashboard)

    #  context opbouwen voor de transformator zelf
    context_string_transformer = f"Transformer: {transformer['batteries']} batteries"
    context_list_house.append(context_string_transformer)

    # de gehele lijst naar een string maken die per entry in de lijst een enter krijgt
    context = "\n".join(context_list_house)
    return context

def level_generation(data: dict) -> str:
    return -1

if __name__ == "__main__":
    app.run(debug=True, port=5000)