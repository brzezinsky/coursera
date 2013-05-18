import sys
import json


cnt = {}

def main():
	tweet_file = open(sys.argv[1])
	for line in tweet_file:
		obj =json.loads(line)
		if 'entities' in obj:
			ents = obj['entities']
			if 'hashtags' in ents:
				for tag in ents['hashtags']:
					val = tag['text'].encode('utf-8')
					if val in cnt:
						cnt[val] = cnt[val] + 1
					else:
						cnt[val] = 1

	vals = []
	for tag in cnt:
		vals.append((tag, cnt[tag]))
	sorted_vals = sorted(vals, key = lambda hashtag: hashtag[1],reverse=True)
	for i in range(10):
		print "{0} {1}".format(sorted_vals[i][0], sorted_vals[i][1]) 




if __name__ == '__main__':
	main()
