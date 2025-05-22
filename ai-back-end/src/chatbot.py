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

    match data['location_request']:
        case "level":
            response = chatbot(data)
        case "admin":
            response = level_generation(data)

    return jsonify({"response": f"{response}".strip()})

def chatbot(data: dict) -> str:
    # opzetten vertaalmodellen
    translator_nl_to_en = pipeline("translation", model="Helsinki-NLP/opus-mt-nl-en")
    translator_en_to_nl = pipeline("translation", model="Helsinki-NLP/opus-mt-en-nl")

    # filter de vraag uit de data
    question = data['inputMessage']
    # zet vraag om naar engels
    question_english = translator_nl_to_en(question)[0]['translation_text']
    # maak de context met de data
    context = create_context(data)

    # roep het model aan wat gebruikt wordt
    generator = pipeline("text2text-generation", model="google/flan-t5-base")
    # maak de prompt aan de hand van de vraag en de context
    prompt = f"""
Context:
{context}

Question:
{question_english}
"""
    # roep het model aan 
    response = generator(prompt, max_length=50)[0]
    response_english = response['generated_text']

    # vertaal response terug naar nederlands
    response_dutch = translator_en_to_nl(response_english)[0]['translation_text']

    return response_dutch

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
    user_input = data['inputMessage']

    generator = pipeline("text2text-generation", model="google/flan-t5-large")

    full_prompt = f"""
You are an AI that converts natural language into a specific JSON format.

Extract the number of houses and the number of solar panels mentioned in the input. Then generate a JSON dictionary in the following structure:

{{
  "Houses": [
    {{ "house_1": {{ "solar_panels": <solar_panel_count>, "batteries": 0, "has_heatpump": false, "has_car": false }} }},
    {{ "house_2": {{ "solar_panels": <solar_panel_count>, "batteries": 0, "has_heatpump": false, "has_car": false }} }},
    ...
  ],
  "Level": {{
    "max_coins": 0,
    "max_co2": 0,
    "start_end_time": "0h - 0h",
    "season": ""
  }}
}}

Example:

{{
    "Houses": [
    {{"house_1": {{"solar_panels: 2, "batteries: 0, "has_heatpump": false, "has_car": false}} }},
    ...
    ],
    "Level": {{
    "max_coins": 0,
    "max_co2": 0,
    "start_end_time": "0h - 0h",
    "season": ""
  }}
}}

Only include as many houses as specified in the input, and set the solar_panels number for each one. Fill in all other fields exactly as shown.

Input: "{user_input}"
"""
    response = generator(full_prompt, max_length=500, num_return_sequences=1)[0]

    print(full_prompt)
    print(response)

    return response['generated_text']


if __name__ == "__main__":
    app.run(debug=True, port=5000)