import json
from progress.bar import Bar

# Specify the path to the JSON file
file_path = 'output.json'

keywords = set()

# Open the JSON file and load its contents
with open(file_path, 'r', encoding='utf-8') as file:
    data = json.load(file)

# Now you can work with the parsed JSON data
# For example, you can access specific values using keys
liste = data['hashObject']

# Or iterate over the data if it's a list

bar = Bar('Processing', max=len(liste))

for item in liste:
    # Do something with each item
    for keyword in item["keywords"]:
        keywords.add(keyword)
    bar.next()

bar.finish()

# export the set as a json list
with open('keywords.json', 'w', encoding='utf-8') as outfile:
    json.dump(list(keywords), outfile)