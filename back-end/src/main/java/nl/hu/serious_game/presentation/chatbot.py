from flask import Flask, request, jsonify
from flask_cors import CORS
from transformers import pipeline

app = Flask(__name__)
CORS(app)  # Staat alle domeinen toe, pas dit aan als nodig

@app.route("/python-data", methods=["POST"])
def get_data():
    data = request.get_json()
    question = data['inputMessage']
    context = create_context(data)

    qa_model = pipeline("question-answering", "timpal0l/mdeberta-v3-base-squad2")
    answer = qa_model(question = question, context = context)['answer']

    return jsonify({"response": f"{answer}".strip()})

def create_context(data):
    print(data)
    return f"There are {data['transformers'][0]['houses'][0]['solarpanels']} solarpanels on house 0"

if __name__ == "__main__":
    app.run(debug=True, port=5000)