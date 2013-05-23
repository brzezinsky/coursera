import MapReduce
import sys


mr = MapReduce.MapReduce()

def unique(lst):
    return list(set(lst))

def mapper(record):
    docid = record[0]
    text = record[1]
    words = text.split()
    for w in words:
      mr.emit_intermediate(w, docid)

def reducer(key, ids):
		mr.emit((key, unique(ids)))
	

if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
