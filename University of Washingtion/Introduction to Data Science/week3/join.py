import MapReduce
import sys

mr = MapReduce.MapReduce()

def sublist(items, key):
	key = [key]
	return [x for x in items if x[0] in key]

def mapper(record):
	key = record[1]
	mr.emit_intermediate(key, record)

def reducer(key, ids):
	line_items = sublist(ids, 'line_item')
	orders = sublist(ids, 'order')
	result = [(a + b) for a in orders  for b in line_items]
	for item in result:
		mr.emit(item)

	

if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
