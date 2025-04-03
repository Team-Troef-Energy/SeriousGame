from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Staat alle domeinen toe, pas dit aan als nodig

@app.route("/python-data", methods=["POST"])
def get_data():
    data = request.get_json()
    return jsonify({"response": f"{data}"})

if __name__ == "__main__":
    app.run(debug=True, port=5000)