import sys
import MapReduce

mr = MapReduce.MapReduce()


def mapper(record):
	(ID, dna) = (record[0], record[1])
	mr.emit_intermediate(dna[:-10], ID)

def reducer(key, vals):
	mr.emit(key)



if __name__ == '__main__':
	mr.execute(open(sys.argv[1]), mapper, reducer)
