import sys
inputfile = sys.argv[1]
file1 = open(inputfile ,"r")
def avgFirstThreeDigit(f):
	listavg = []
	for k in range (len(f)-1,-1,-1):
		i = f[k]
		while i > 1000:
			i = i // 10

		x = i % 10
		i = i // 10
		y = i % 10
		i = i // 10
		z = i
		average = (x+y+z)/3
		listavg.append(average)
	return listavg
for line in file1.readlines():
	snumbers = line.split(";")
	ListIntegers = []
	for s in snumbers:
		new = int(s)
		ListIntegers.append(new)
	output = avgFirstThreeDigit(ListIntegers)
	print(output)
file1.close()


		
		
			
