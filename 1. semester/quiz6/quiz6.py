import matplotlib.pyplot as plot
file = open ("weather_2012.csv","r")
x = []
temp =[]
c = -1
daym = [31,60,91,121,152,182,213,244,274,305,335,366]
months = ["January","February","March","April","May","June","July","Agust","September","October","November","December"]
xpos = [i for i in range(12)]
def maxtemp(x):
	f = x[0]
	for i in x:
		if i>f:
			f = i
	return f
for l in file.readlines():
	c = c+1
	if not l[0]=="D":
		l2 = l.split(",")
		x.append(float(l2[1]))
	if c/24 == daym[0]:
		temp.append(maxtemp(x))
		daym.pop(0)
		x = []
print("Month  Max Temperature")
for i in range(len(months)):
	print(months[i],"",temp[i])
#temp is the lowest temperature for each month.
plot.bar(xpos,temp,align='center',color='b',alpha=0.8)
plot.xticks(xpos,months)
plot.title("Maximum Temperature for each month")
plot.show()
file.close()
#end of task1
def dtemp(d,k,v):
	try:
		x = d[k]
		x.append(v)
		d[k] = x
		return d
	except :
		d[k] = [v]
		return d	
def davg(d):
	for x in d.keys():
		v = d[x]
		c = 0
		for i in v:
			c = c + float(i)
		a = c/len(v)
		d[x] = a
	return d 
			
file = open ("weather_2012.csv","r")
tempd= {}
for l in file.readlines():
	if not l[0] == "D": 
		l = l.rstrip("\n")
		if l[-1] == '"':
			list1 = l.split('"')
			k = list1[-2]
			list2 = l.split(",")
			v = list2[1]
			dtemp(tempd,k,v)
		else:
			list1 = l.split(",")
			k = list1[-1]
			v = list1[1]
			dtemp(tempd,k,v)
#at this point tempd is a dictionary. keys are wheather types and values are all assosiated temperatures
davg(tempd)
#now every key has mean temperature as its value
keys = list(tempd.keys())
keys.sort()
print("Weather  Mean Temperature")
for i in keys:
	print(i,"",tempd[i])
xpos1 = [i for i in range(len(keys))]
values = [tempd[i] for i in keys]
plot.bar(xpos1,values,align='center',color='b',alpha=0.8)
plot.xticks(xpos1,keys)
plot.title("Mean Temperature for each weather condition")
plot.tight_layout()
plot.show()
file.close()
	
	
