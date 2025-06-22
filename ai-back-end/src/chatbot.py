import json
from openai import OpenAI

def chatbot(data: dict, client) -> str:
    """
    Krijgt een data object en een client, waarmee een context gemaakt kan worden en het antwoord gevraagt kan worden.
    In het data object zit een JSON format van alle huizen die bij een transformator horen + alles wat er bij die specifieke huizen hoort,
    en alle informatie over het level.

    return: antwoord op de vraag van de gebruiker
    """
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