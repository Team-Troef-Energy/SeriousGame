from flask import Flask, request, jsonify
from flask_cors import CORS
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

    client = create_client()

    match data['location_request']:
        case "level":
            response = chatbot(data, client)
        case "admin":
            response = level_generation(data, client)

    return jsonify({"response": f"{response}".strip()})

def create_client():
    with open('ai-back-end/api_key.txt', 'r') as f:
        key = f.read()

    client = OpenAI(
        base_url="https://api.studio.nebius.com/v1/",
        api_key=key
    )
    return client

def chatbot(data: dict, client) -> str:
    # pak vraag uit de data
    question = data['inputMessage']

    # maak de context aan
    context = create_context(data)

    # vraag een response aan het model
    response = client.chat.completions.create(
            model="deepseek-ai/DeepSeek-V3-0324",
            max_tokens=512,
            temperature=0.3,
            top_p=0.95,
            messages=[
                {"role": "system", "content": context},
                {"role": "user", "content": question}
            ]
        )

    # pak het antwoord uit de response
    json_response = json.loads(response.to_json())
    content = json_response['choices'][0]['message']['content']

    return content

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

def level_generation(data: dict, client) -> str:
    user_input = data['inputMessage']

    full_prompt = f"""
Convert the input into a JSON dictionary with the following structure:

{{
  "Houses":
    {{ "house_1": {{ "max_solar_panels": <solar_panel_count>, "max_batteries": <battery_amount>, "has_heatpump": <has_heat>, "has_car": <has_car>, "has_congestion": <has_congestion>, "max_power": <max_power> }} }}
  ,
  "Level": {{
    "max_coins": <max_coins_amount>,
    "max_co2": <max_co2_amount>,
    "start_time": <start_time>,
    "end_time": <end_time>,
    "season": "<season>",
    "cost_solar_panel": <cost_solar_panel>,
    "cost_battery": <cost_battery>,
    "cost_co2": <cost_co2>,
    "max_batteries_transformer": <max_batteries_transformer>,
    "has_congestion_transformer": <has_congestion_transformer>,
    "max_power_transformer": <max_power_transformer>
  }}
}}

Extract the number of houses and their attributes from the input. For any missing values, use these defaults:
- 0 for max_solar_panels, max_batteries, max_coins, max_co2, cost_solar_panel, cost_battery, cost_co2, max_batteries_transformer, max_power.
- false for has_heatpump, has_car, has_congestion, has_congestion_transformer.
- 0 for start_time, end_time.
- SPRING for season.

if there is no number of houses specified in the input, return an empty dictionary for the houses
give the season in english and full caps if the season is FALL, give back AUTUMN instead
"""

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