from flask import Flask, request, jsonify
from flask_cors import CORS
from transformers import pipeline
import json
from openai import OpenAI


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

    # translator_nl_to_en = pipeline("translation", model="Helsinki-NLP/opus-mt-nl-en")
    # user_input_english = translator_nl_to_en(user_input)[0]['translation_text']

    # generator = pipeline("text2text-generation", model="google/flan-t5-large")

    full_prompt = f"""
Convert the input into a JSON dictionary with the following structure:

{{
  "Houses":
    {{ "house_1": {{ "solar_panels": <solar_panel_count>, "batteries": <battery_amount>, "has_heatpump": <has_heat>, "has_car": <has_car> }} }}
  ,
  "Level": {{
    "max_coins": <max_coins_amount>,
    "max_co2": <max_co2_amount>,
    "start_end_time": "<start_time>h - <end_time>h",
    "season": "<season>"
  }}
}}

Extract the number of houses and their attributes from the input. For any missing values, use these defaults:
- 0 for solar_panels, batteries, max_coins, max_co2.
- false for has_heatpump, has_car.
- 0 for start_time, end_time.
- "" for season.

if there is no number of houses specified in the input, return an empty dictionary for the houses
"""
    # response = generator(full_prompt, max_length=800, num_return_sequences=1)[0]

    with open('ai-back-end/api_key.txt', 'r') as f:
        key = f.read()

    client = OpenAI(
        base_url="https://api.studio.nebius.com/v1/",
        api_key=key
    )

    response = client.chat.completions.create(
        model="deepseek-ai/DeepSeek-V3-0324",
        max_tokens=512,
        temperature=0.3,
        top_p=0.95,
        messages=[
            {"role": "system", "content": full_prompt},
            {"role": "user", "content": user_input}
        ]
    )

    json_response = json.loads(response.to_json())
    content = json_response['choices'][0]['message']['content']

    # Strip the ```json markdown and parse the inner JSON
    inner_json = content.strip('```json\n').strip('```')
    inner_dict = json.loads(inner_json)

    return inner_dict
if __name__ == "__main__":
    app.run(debug=True, port=5000)