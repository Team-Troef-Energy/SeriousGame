from flask import Flask, request, jsonify
from flask_cors import CORS
from openai import OpenAI
from chatbot import chatbot
from generation import level_generation


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
    """
    maakt een client aan om de modellen mee aan te kunnen roepen

    returns: client
    """
    with open('ai-back-end/api_key.txt', 'r') as f:
        key = f.read()

    client = OpenAI(
        base_url="https://api.studio.nebius.com/v1/",
        api_key=key
    )
    return client

if __name__ == "__main__":
    app.run(debug=True, port=5000)