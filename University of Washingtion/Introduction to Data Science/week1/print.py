import urllib
import json
for i in range(1, 11):
	print "page {0}".format(i)
	print "------------------------------------------------\n"
	response = urllib.urlopen("http://search.twitter.com/search.json?q=microsoft&page={0}".format(i))
	sets = json.load(response)
	for twit in sets['results']:
		print twit[u'text'].encode('utf-8')
	print "\n"
