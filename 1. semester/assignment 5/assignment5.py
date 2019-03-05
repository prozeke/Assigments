import os,sys
item = open("u.item","r",encoding='iso-8859-1')
genre = open("u.genre", "r",encoding='iso-8859-1')
filmnames = []
lfn = [i for i in os.listdir("film")]
lfndic = {}
for i in lfn:	
	film = open("film/"+i,"r",encoding='iso-8859-1')
	l = film.readline()
	filmnamefind = l.split("(")[0]
	while filmnamefind[-1]==" ":
		filmnamefind = filmnamefind.strip(" ")
	filmnames.append(filmnamefind.casefold())
	lfndic[filmnamefind.casefold()] = "film/"+i
	film.close()
genredic = {} 
for i in genre.readlines():
	i = i.rstrip("\n")
	l1 = i.split("|")
	genredic[int(l1[1])] = l1[0]
itemdic = {}
for i in item.readlines():
	i = i.rstrip("\n")
	l = i.split("|")
	l[1] = l[1].split("(")[0]
	l[1] = l[1].rstrip(" ")
	l[1] = l[1].casefold()
	for p in range(len(l[4:])):
		if l[p+4] == "1":
			l[p+4] = genredic[p]
	while "0" in l:
		l.remove("0")
	itemdic[l[0]] = l
#itemdic = movieid : u.item info
class NotMatched(Exception):
	pass
rew = open("rewiews.txt","w",encoding='iso-8859-1')
movidlist = []
for i in itemdic.values():
	try:
		if i[1] in filmnames:
			rew.write(i[0]+" "+i[1]+" is found in folder\n")
			movidlist.append(i[0])
		else:
			raise NotMatched
	except:
		rew.write(i[0]+" "+i[1]+" is not found in folder. Look at "+i[3]+"\n")
rew.close()
###organizing user info
def listcreate(x):
	l = []
	for i in x.readlines():
		i = i.rstrip("\n")
		l1 = i.split("|")
		l.extend(l1)
	return l
oc = open("u.occupation","r",encoding='iso-8859-1')
oclist = listcreate(oc)
ocdic = {i:oclist[2*i+1] for i in range(len(oclist)//2)}
oc.close()
us = open("u.user","r")
uslist = listcreate(us)
for i in range(len(uslist)//5):
	x = uslist[5*i+3]
	uslist[5*i+3] = ocdic[int(x)]
usdic = {uslist[5*i]:uslist[5*i:5*i+5] for i in range(len(uslist)//5)}#str(usid) : usinfo

us.close()
data = open("u.data","r",encoding='iso-8859-1')
datalist = []
e = 0
for i in data.readlines():
	i = i.rstrip("\n")
	l = i.split("	")
	t = []
	e = e+1
	for h in l:
		f = h.split(" ")
		t.extend(f)
	if t[1] in movidlist:
		datalist.append(t)
datadic = {}
for i in datalist:
	if not i[1] in datadic.keys():
		datadic[i[1]] = [i]
	else:
		x = datadic[i[1]]
		x.append(i)
data.close()

try:
	os.mkdir("filmList")###html files will be created
except:
	pass
for i in movidlist:
	f = open("filmList/"+i+".html","w",encoding='iso-8859-1')
	f.write("""<!DOCYTYPE html>
<html>
<head>
<meta charset="iso-8859-1">
<title>"""+itemdic[i][1]+"""</title>
</head>
<body>
<h1 style = "font-size:40; font-family:Times New Roman;  color:red; "><b>"""+itemdic[i][1]+"""</b>   </h1>
<p><b>Genre:</b>""")
	for k in itemdic[i][4:]:
		f.write(k+" ")
	f.write("""</p>
<p><b>IMBD Link: </b><a style="color:blue;" href=\""""+itemdic[i][3]+"\" target=\"_blank\" >"+itemdic[i][1]+"""</a> </p>
<p style = " color:black; font-family:Times New Roman"><b>Rewiew:</b></p>""")
	c = 0
	rewu = open(lfndic[itemdic[i][1]],"r")
	for l in rewu.readlines():
		l = l.rstrip("\n")
		if not c == 0:
			f.write("<p>"+l+"</p>")
		else:
			c = c+1
	total = 0
	rates = 0
	for q in datadic[i]:
		total = total+1
		rates = rates + int(q[2])
	rate = rates / total	
	f.write("	  <p><b>Total User: </b>"+ str(total))
	f.write(" / <b>Total Rate: </b>"+ str(rate))
	f.write(" </p>")

	for l in datadic[i]:
		f.write("""<p><b>User who rate the film: </b></p><p><b>User: </b>"""+str(l[0]))
		f.write("<b> Rate: </b>"+str(l[2]))
		f.write("""</p>
<p><b>User Detail: </b><b>Age: </b>"""+str(usdic[l[0]][1])+"<b> Gender: </b>"+str(usdic[l[0]][2])+"<b> Occupation: </b>"+str(usdic[l[0]][3])+"<b> Zip Code: </b>"+str(usdic[l[0]][4])+"</p>")
	f.write("</body></html> ")
	f.close()


item.close()
genre.close()
### stage 2
#unique words
words = open("stopwords.txt","r",encoding='iso-8859-1')
wordlist = []
for i in words.readlines():
	i = i.rstrip("\n")
	wordlist.append(i.casefold())
words.close()
uniq = {i : [] for i in genredic.values()}
punc = [",",".",":",";","?","!"]

for i in movidlist:
	name = itemdic[i][1]
	rewg = open(lfndic[name],"r")
	uqa = []
	for l in rewg.readlines()[1:]:
		l = l.rstrip("\n")
		uq = l.split(" ")
		for j in range(len(uq)):
			if uq[j][-1] in punc:
				uq[j] = uq[j].rstrip(uq[j][-1])
		for h in uq:
			h = h.casefold()
			if not h in uqa:
				if not h in wordlist:
					uqa.append(h)
	
	for h in itemdic[i][4:]:
		x = uniq[h]
		for j in uqa:
			if not j in x:
				x.append(j)
	rewg.close()

#uniq genrename : uniqwords     
gtl = [i for i in os.listdir("filmGuess")]
guess = open("filmGenre.txt","w",encoding='iso-8859-1')
guess.write("Guess Genres of Movie based on Movies\n")
guessdic = {}
uniqc = {i:0 for i in uniq.keys()}
for i in gtl:
	s = open("filmGuess/"+i,"r",encoding='iso-8859-1')
	slist = []
	filmnameg = s.readline().split("(")[0]
	filmnameg= filmnameg.rstrip(" ")
	guessdic[filmnameg] = []
	for l in s.readlines()[1:]:
		l = l.rstrip("\n")
		linelist = l.split(" ")
		for h in range(len(linelist)):
			if linelist[h][-1] in punc:
				linelist[h] = linelist[h].rstrip(linelist[h][-1])
				
		for h in range(len(linelist)):
			linelist[h] = linelist[h].casefold()
		for h in linelist:
			if not h in slist:
				slist.append(h)
	for l in slist:
		for h in uniq.keys():
			if l in uniq[h]:
				c = uniqc[h]
				c = c+1
				uniqc[h] = c
	for l in uniqc.keys():
		if uniqc[l] >= 20:
			q = guessdic[filmnameg]
			q.append(l) 
	s.close()
for i in guessdic.keys():
	out=""
	for l in guessdic[i]:
		out = l + " " + out
	guess.write(i+" : "+out+"\n")
	



guess.close()








