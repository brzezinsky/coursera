import sys
import json
	
scores = {}
new_terms = {}
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
			for word in dic['text'].split():
				if word not in scores:
					if word not in new_terms:
						new_terms[word] = []
					new_terms[word].append(actual_score)
	for term in new_terms:
		print '{0} {1}'.format(term.encode('utf-8'), sum(new_terms[term]) / float(len(new_terms[term])))
	

if __name__ == '__main__':
    main()


