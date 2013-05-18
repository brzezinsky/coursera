import sys
import json
cnt = {}
def main():
	total = 0
	tweet_file = open(sys.argv[1])
	for line in tweet_file:
		obj = json.loads(line)
		if 'text' in obj:
			tokens = obj['text'].split()
			for token1 in tokens:
				token = token1.encode('utf-8')
				cnt[token] = 1 + (cnt[token] if token in cnt else 0)
				total += 1
	
	for key in cnt:
		print "{0} {1}".format(key, 1.0 *  cnt[key] / total)



if __name__ == '__main__':
	main()
