tree_array = range(10)

def union(arr, p, q):
	"""
	conntects 2 components
	"""
	while q != arr[q]:
		q = arr[q]
	while p != arr[p]:
		p = arr[p]
	arr[p] = q
	return arr

def connected(arr, p, q):
	"""
	checks if 2 components are connected
	"""
	pass


print union(tree_array, 4, 3)
print union(tree_array, 3, 8)
print union(tree_array, 6, 5)
print union(tree_array, 9, 4)
print union(tree_array, 2, 1)
print union(tree_array, 8, 9)
print union(tree_array, 5, 0)
print union(tree_array, 7, 2)
print union(tree_array, 6, 1)
print union(tree_array, 7, 3)



