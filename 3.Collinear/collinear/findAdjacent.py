# def findAdjacent(lst):
#     my_max = 0
#     freq = 0
#     for item in lst:
#         if lst.count(item) > freq:
#             freq = lst.count(item)
#             my_max = item
#     return 'Most frequent element is ' + str(my_max), 'The frequency is ' + str(freq), 'Index is ' + str(lst.index(my_max))

def countItems(lst):
    item = None;
    my_idx = 0
    my_max = 0
    temp = 0
    for idx in range(len(lst) - 1):
        if lst[idx] == lst[idx + 1]:
            temp += 1
            if temp > my_max:
                my_max = temp
                item = lst[idx]
                my_idx = idx - my_max + 1
        else:
            temp = 0
    return 'most frequent item: ' + str(item), 'frequency is ' + str(my_max + 1), 'index is ' + str(my_idx)

print countItems([-0.6875, -0.5918367346938775, -0.5, -0.5, -0.5, -0.3269230769230769, -0.16666666666666666, float('inf'), float('inf')])





# print findAdjacent([0, 1])


