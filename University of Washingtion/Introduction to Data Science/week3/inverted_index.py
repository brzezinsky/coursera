import MapReduce
import Helper
import sys


mr = MapReduce.MapReduce()
hlp = Helper.Helper()


def mapper(record):
    docid = record[0]
    text = record[1]
    words = text.split()
    for w in words:
      mr.emit_intermediate(w, docid)

def reducer(key, ids):
		mr.emit((key, hlp.unique(ids)))
	

if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
