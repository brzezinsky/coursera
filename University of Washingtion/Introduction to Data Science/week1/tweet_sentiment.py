import sys
import json
	
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

		print actual_score

	

if __name__ == '__main__':
    main()

