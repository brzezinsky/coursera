import MapReduce
import sys

mr = MapReduce.MapReduce()

def worker(lst, name):
	st = dict((el, 0) for el in lst)
	for item in lst:
		st[item] = st[item] + 1
	return [item for item in st.keys() if  st[item] == 1]

def mapper(record):
	(a, b) = (record[0], record[1])
	mr.emit_intermediate(a, b)
	mr.emit_intermediate(b, a)


def reducer(key, vals):
	actual = worker(vals, key)
	for item in actual:
		mr.emit((key, item))
	


if __name__ == '__main__':
	mr.execute(open(sys.argv[1]), mapper, reducer)
