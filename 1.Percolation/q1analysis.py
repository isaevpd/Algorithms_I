import math, pylab

def f(x):
	return 8.53 ** -10 * x ** 2.25

def g(x):
	return x * x

def h(x):
	return x * x * 0.5


#tests = [2 ** value for value in range(8, 20)]

pylab.plot([-3, -2.25, -1.5, -.75, 0, \
.75, 1.5, 2.25, 3], [-.51, -1.48, .16, -.26, -.25, \
-.17, -.24, .29, .11])
pylab.show()

#for num in tests: print 'f(x) result is: ' + str(num) + ": " + str(f(num))
	

# for num in tests: print 'g(x) result is: ', g(num)

# for num in tests: print 'h(x) result is ' , h(num)

