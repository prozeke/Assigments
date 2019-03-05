import matplotlib.pyplot as plot
import sys
import numpy as np
def retrieveData(fi,l):
	f2 = open("retrievedData.txt", "w")
	global listvotes
	listvotes = []
	for name in l:
		f = open(fi , "r")
		c = 0
		for line in f.readlines():
			line = line.rstrip("\n")
			listf = line.split(",")
			if c == 0:
				for i in listf:
					plc = listf.index(name)	
				c = 1
			else:
				listvotes.append(int(listf[plc]))
		f.close()
	f2.write(str(listvotes))
	f2.close()	
	return listvotes
def DispBarPlot():#sumvotes
	list1 = list(nominee)
	global sumvotes
	sumvotes = []
	x = len(listvotes)//len(nominee)
	c = 0
	t = 0
	for i in listvotes:
		t = t + int(i)
		c = c+1
		if c == x:
			c = 0
			sumvotes.append(t)
			t = 0
	lsumvotes = list(sumvotes)
	n1 = list1[lsumvotes.index(max(lsumvotes))]#nominee 1
	list1.remove(n1)
	lsumvotes.remove(max(lsumvotes))
	n2 = list1[lsumvotes.index(max(lsumvotes))]#nominee 2
	f = open(sys.argv[1],"r")
	z = True
	states = []
	for line in f.readlines():
		line = line.rstrip("\n")
		if z:
			z = False
		else:
			l = line.split(",")
			state = l[0]
			states.append(state)
	f.close()
	indexn1 = nominee.index(n1)
	indexn2 = nominee.index(n2)
	listn1 = listvotes[x*indexn1:x*(indexn1+1)]
	listn2 = listvotes[x*indexn2:x*(indexn2+1)]
	###                   ###
	N = len(states)

	ind = np.arange(N)  # the x locations for the groups
	width = 0.35       # the width of the bars

	fig, ax = plot.subplots()
	rects1 = ax.bar(ind, listn1, width, color='r')

	rects2 = ax.bar(ind + width, listn2, width, color='b')

	ax.set_ylabel('Vote Counts')
	ax.set_title('States')
	ax.set_xticks(ind + width)
	ax.set_xticklabels(states,rotation = "vertical",fontsize = 6.0)
	plot.tight_layout()
	ax.legend((rects1[0], rects2[0]), (n1, n2))
	plot.savefig("ComparativeVotes.pdf",format = "pdf")

def CompareVoteonBar():
	xtick = [str(int((i / sum(sumvotes))*100000)/1000)+" %" for i in sorted(sumvotes)[::-1] ]
	ytick = [(i / sum(sumvotes))*100 for i in sorted(sumvotes)[::-1] ]
	ordernominee = [nominee[sumvotes.index(i)] for i in sorted(sumvotes)[::-1]]
	rects = [i for i in range(len(xtick))]
	N = len(xtick)
	colors = ["r","b","g","y","purple"]#DONT FORGET TO ADD COlORS!
	ind = np.arange(len(rects))  # the x locations for the groups
	width = 0.35       # the width of the bars

	fig, ax = plot.subplots()
	for i in range(len(rects)):
		rects[i] = ax.bar(ind[i], ytick[i], width, color=colors[i%5])


	ax.set_ylabel('Vote percentages')
	ax.set_xlabel("Nominees")
	ax.set_xticks(ind + width)
	ax.set_xticklabels(xtick,rotation = "horizontal")
	
	ax.legend(rects, ordernominee)
	plot.savefig("CompVotePercs.pdf",format = "pdf")
	
def obtainHistogram(l):
	l2 = [0 for i in range(10)]
	for i in l:
		x = int(str(i)[-1])
		try:
			y = int(str(i)[-2])
		except:
			y = 0
		l2[x] = l2[x] + 1
		l2[y] = l2[y] + 1
	freq = [i / (2*len(l)) for i in l2]
	return freq

def plotHistogram(l):
	fig, ax = plot.subplots()
	ideal = [0.1 for i in range(10)]
	x = [i for i in range(len(l))]
	y = [i for i in l]
	line1, = plot.plot(x, y,linewidth = 2,color = "r")
	line2, = plot.plot(x,ideal,"--",linewidth=2,color = "g")
	ax.set_title("Histogram of least sign. digits")
	ax.set_xlabel("Digits")
	ax.set_ylabel("Distribution")
	ax.legend((line2,line1 ),("mean","Digit dist"))
	plot.savefig("Histogram.pdf", format = "pdf")

def plotHistogramWithSample():
	names = ["HistogramofSample1.pdf","HistogramofSample2.pdf","HistogramofSample3.pdf","HistogramofSample4.pdf","HistogramofSample5.pdf"]
	l1 = [10,50,100,1000,10000]
	colors = ["orange","b","g","y","purple"]
	for i in range(len(l1)):
		fig, ax = plot.subplots()
		yx = np.random.random(l1[i])
		yx = yx*1000
		yx = yx // 10
		yx = [int(i) for i in yx]
		y = obtainHistogram(yx)
		ideal = [0.1 for i in range(10)]
		x = [i for i in range(10)]
		line1, = plot.plot(x, y,linewidth = 2,color = colors[i])
		line2, = plot.plot(x,ideal,"--",linewidth=2,color = "g")
		ax.set_title("Histogram of least sign. digits")
		ax.set_xlabel("Digits")
		ax.set_ylabel("Distribution")
		ax.legend((line2,line1 ),("mean","Digit dist"))
		plot.savefig(names[i], format = "pdf")

def calculateMSE(l1,l2):
	t = 0
	for i in range(len(l1)):
		x = l1[i]
		y =l2[i]
		d = (x-y)**2
		t = t+d
	return t
	
def compareMSEs(x):
	t = 0
	for i in range(10000):
		y = np.random.random(len(listvotes))
		y = y*1000
		y = y // 10
		y = [int(i) for i in y]
		z = obtainHistogram(y)
		l = [0.1 for i in range(10)]
		if x < calculateMSE(l,z):
			t = t+1
	return t
			
	
	
	
# nominee, sumvotes and listvotes are global variables. types are str , int , int
global nominee
nominee = sys.argv[2].split(",")
retrieveData(sys.argv[1],nominee)#=listvotes
DispBarPlot()
CompareVoteonBar()
freq = obtainHistogram(listvotes)
plotHistogram(freq)
plotHistogramWithSample()
l2 = [0.1 for i in freq]
msefreq = calculateMSE(freq,l2)
p = compareMSEs(msefreq)
pvalue = (10000 -p) / 10000
print("MSE value of 2012 USA election is {}".format(msefreq))
print("The number of MSE of random samples which are larger than or equal to USA election MSE is {}".format(p))
print("The number of MSE of random samples which are smaller than USA election MSE is {}".format(10000-p))
print("2012 USA election rejection level p is {}".format(pvalue))
if p < 500:
	print("Finding: We reject the null hypothesis at the p= {} level".format(pvalue))
else:
	print("Finding: There is no statistical evidence to reject null hypothesis")
answer = open("myAnswer.txt","w")
answer.write("MSE value of 2012 USA election is " +str(msefreq)+"\n"+ 
"The number of MSE of random samples which are larger than or equal to USA election MSE is "+str(p)+"\n"+
"The number of MSE of random samples which are smaller than USA election MSE is "+str(10000-p)+"\n"+
"2012 USA election rejection level p is "+str(pvalue)+"\n")
if p<500:
	answer.write("Finding: We reject the null hypothesis at the p= " +str(pvalue)+"level\n")
else:
	answer.write("Finding: There is no statistical evidence to reject null hypothesis")
answer.close()







