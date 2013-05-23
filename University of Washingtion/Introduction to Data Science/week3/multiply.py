import sys
import MapReduce

mr = MapReduce.MapReduce()

def mapper(record):
	(i, j, val) = record[1:]
	if record[0] == 'a':
		for it in range(0, 5):
			mr.emit_intermediate((i, it), record)
	else:
		for it in range(0, 5):
			mr.emit_intermediate((it, j), record)


def reducer(key, vals):
	(row, col) = key
	print vals
	print key
	result = sum([a[3] * b[3] for a in vals for b in vals if a[0] == 'a' and b[0] == 'b' and a[2] == b[1]])
	mr.emit((row, col, result))


if __name__ == '__main__':
	mr.execute(open(sys.argv[1]), mapper, reducer) 
