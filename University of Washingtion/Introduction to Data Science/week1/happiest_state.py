import sys
import json
states = {
        'AK': 'Alaska',
        'AL': 'Alabama',
        'AR': 'Arkansas',
        'AS': 'American Samoa',
        'AZ': 'Arizona',
        'CA': 'California',
        'CO': 'Colorado',
        'CT': 'Connecticut',
        'DC': 'District of Columbia',
        'DE': 'Delaware',
        'FL': 'Florida',
        'GA': 'Georgia',
        'GU': 'Guam',
        'HI': 'Hawaii',
        'IA': 'Iowa',
        'ID': 'Idaho',
        'IL': 'Illinois',
        'IN': 'Indiana',
        'KS': 'Kansas',
        'KY': 'Kentucky',
        'LA': 'Louisiana',
        'MA': 'Massachusetts',
        'MD': 'Maryland',
        'ME': 'Maine',
        'MI': 'Michigan',
        'MN': 'Minnesota',
        'MO': 'Missouri',
        'MP': 'Northern Mariana Islands',
        'MS': 'Mississippi',
        'MT': 'Montana',
        'NA': 'National',
        'NC': 'North Carolina',
        'ND': 'North Dakota',
        'NE': 'Nebraska',
        'NH': 'New Hampshire',
        'NJ': 'New Jersey',
        'NM': 'New Mexico',
        'NV': 'Nevada',
        'NY': 'New York',
        'OH': 'Ohio',
        'OK': 'Oklahoma',
        'OR': 'Oregon',
        'PA': 'Pennsylvania',
        'PR': 'Puerto Rico',
        'RI': 'Rhode Island',
        'SC': 'South Carolina',
        'SD': 'South Dakota',
        'TN': 'Tennessee',
        'TX': 'Texas',
        'UT': 'Utah',
        'VA': 'Virginia',
        'VI': 'Virgin Islands',
        'VT': 'Vermont',
        'WA': 'Washington',
        'WI': 'Wisconsin',
        'WV': 'West Virginia',
        'WY': 'Wyoming'
}

states_happy = {}	
scores = {}
def fill_scores(fp):
	for line in fp:
		term, score  = line.split("\t")
		scores[term] = int(score)
	

def main():
    	sent_file = open(sys.argv[1])
    	tweet_file = open(sys.argv[2])
    	fill_scores(sent_file)
    	for line in tweet_file:
	    	dic = json.loads(line)
		actual_score = 0
		if 'text' in dic:
		    	for word in dic['text'].split():
			    	if word in scores:
				    	actual_score += scores[word]

		if 'place' in dic and not (dic['place'] is None):
			place = dic['place']
			if 'country_code' in place:
				if place['country_code'] == 'US':
					tokens = place['full_name'].encode('utf-8').split(",")
					for i in range(len(tokens)):
						tokens[i] = tokens[i].strip()
					if len(tokens) == 2:
						if tokens[1] == 'US':
							for st in states:
								if states[st] == tokens[0]:
									tokens[1] = st
									break
						states_happy[tokens[1]] = actual_score + (states_happy[tokens[1]] if (tokens[1] in states_happy) else 0)
	vals = []
	for st in states_happy:
		vals.append((st, states_happy[st]))
	sorted_vals = sorted(vals, key = lambda state: state[1],reverse=True)
	if len(sorted_vals) > 0:
		print sorted_vals[0][0]

		
	

if __name__ == '__main__':
    main()


