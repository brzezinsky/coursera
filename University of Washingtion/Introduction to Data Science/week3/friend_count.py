import MapReduce
import sys

mr = MapReduce.MapReduce()

def mapper(record):
	mr.emit_intermediate(record[0], record[1])

def reducer(key, vals):
	mr.emit((key, len(set(vals))))


if __name__ == '__main__':	
	inputdata = open(sys.argv[1])
	mr.execute(inputdata, mapper, reducer)
