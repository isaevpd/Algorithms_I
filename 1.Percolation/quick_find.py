
id_array = range(10)

def union(arr, p, q):
	"""
	conntects 2 components
	"""
	pid = arr[p]
	qid = arr[q]
	for idx in range(len(arr)):
		#print idx
		if arr[idx] == pid:
		#	print idx
			arr[idx] = qid;
	return arr

def connected(arr, p, q):
	"""
	checks if 2 components are connected
	"""
	return arr[p] == arr[q]


print union(id_array, 4, 9)
print union(id_array, 9, 1)
print union(id_array, 0, 9)
print union(id_array, 0, 5)
print union(id_array, 6, 9)
print union(id_array, 6, 7)


#print id_array
