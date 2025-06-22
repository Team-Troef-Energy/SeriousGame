import json

def level_generation(data: dict, client) -> str:
    """
    Krijgt een data object en een client, en vult een JSON schema in om zo een level te genereren

    returns: string van JSON format om te gebruiken in front end 
    """
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
  }},
  wantReset: <want_to_reset>
}}

Extract the number of houses and their attributes from the input. For any missing values, use these defaults:
- 0 for max_solar_panels, max_batteries, max_coins, max_co2, cost_solar_panel, cost_battery, cost_co2, max_batteries_transformer, max_power.
- false for has_heatpump, has_car, has_congestion, has_congestion_transformer and Reset.
- 0 for start_time, end_time.
- SPRING for season.

if there is a mention of the word reset in the input, put Reset to true
if the input is talking about emptying also turn Reset to true
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