def helper(n, dim):
    """
    should return the coordinates of n
    given dimension
    """
    row = n / dim if (n % dim != 0) else n / dim - 1
    col = n % dim if (n % dim != 0) else dim
    return row, col - 1

def manhattan(tiles, i, j):
    """
    returns manhattan distance 
    of a point at [i, j] coordinates
    """
    point = tiles[i][j]
    if point == 0:
        return 0
    goal = helper(point, len(tiles))
    return abs(i - goal[0]) + abs(j - goal[1])

tiles = [[8, 7, 6], [5, 1, 2], [3, 4, 0]]



result = 0
# print manhattan(1, 3)
for i in range(len(tiles)):
    for j in range(len(tiles)):
        print 'i is ' + str(i) + ', j is ' + str(j)
        print 'manhattan is ' + str(manhattan(tiles, i, j))
        result += manhattan(tiles, i, j)
# print str(manhattan(10, 9)) + ' should be (1, 0)'

print result