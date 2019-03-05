import sys
inputfile = sys.argv[1]
file1 = open(inputfile, "r")

def calculateTotalCost(bulletlist):
	ResultList = []
	for i in range(0,len(bulletlist)):
		x = bulletlist[i][0]
		y = bulletlist[i][1]
		z = bulletlist[i][2]
		t = x + y*10 + x*z*10
		ResultList.append(t)
	return ResultList
def displayCosts(list1):
	list = calculateTotalCost(list1)
	l = []
	for i in range(1,len(list)+1):
		x = str(i)+"."
		l.append(x)
	for k in range(0,len(list)):
		print (str(l[k]), "house's total cost is:", str(list[k]))
def selectBestBuy(list1):
	list = calculateTotalCost(list1)
	l = []
	for i in range(1,len(list)+1):
		x = str(i)+"."
		l.append(x)
	x = list[0]
	for i in list:
		if i < x:
			x = i
	print ("You should choose" , str(l[list.index(x)]) , "house.Its total cost is" , str(x))

bulletList = []
for line in file1.readlines():
	list1 = line.split(" ")
	nmblist = []
	for s in list1:
		nmb = float(s)
		nmblist.append(nmb)
	bulletList.append(nmblist)
displayCosts(bulletList)
selectBestBuy(bulletList)
file1.close()

		
	
	
