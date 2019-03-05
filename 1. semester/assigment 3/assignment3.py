#BBM103 , assigment 3, binarians
#Zekeriya Onur Yakışkan , 18/11/2016 
import sys

def bntodc(x):
	d = 0
	c = 0
	while x !=0:
		if x % 10 == 1:
			d = d + (2**c)
			c = c + 1
			x = x // 10
		else:
			c = c + 1
			x = x // 10
	return d

def dctobn(x):
	l = []
	while x != 0:
		if x % 2 == 1:
			l.append(1)
		else:
			l.append(0)
		x = x // 2
	bs=""
	for i in l[-1::-1]:
		bs = bs + str(i)
	bn = int(bs)
	return bn

def read_dictionary(x):
	dict = {}
	for i in x.readlines():
		l = i.split(" ")
		dict[l[0]] = l[1]
	return dict

def binarian_to_english(text, dict):
	text = text.rstrip("\n")
	lt = []
	l = text.split(" ")
	for word in l:
		if word in dict.keys():
			worde = dict[word]
			lt.append(worde)
		elif word[0] == "1":
			binnmb = int(word)
			decnmb = bntodc(binnmb)
			lt.append(str(decnmb))
		else:
			lt.append(word)
	tr = ""
	for t in lt:
		tr = tr + " " + t
	tr = tr.strip(" ")
	print(tr)
	return(tr)

def eng_to_bin(text, dict):
	wstrip = ["," ,".",":",";","?","!","'"]
	dict2 = {}
	for w in dict.keys():
		dict2[dict[w]] = w
	lnmb = []
	for nmb in range(10):
		lnmb.append(str(nmb))
	lt = []
	text = text.rstrip("\n")
	text = text.rstrip(" ")
	l = text.split(" ")
	for word in l:
		if word[-1] in wstrip:
			word = word.rstrip(word[-1])
		if word.casefold() in dict2.keys():
			worde = dict2[word.casefold()]
			lt.append(worde)
		elif word[0] in lnmb:
			z = str(dctobn(int(word)))
			lt.append(z)
		else:
			lt.append(word)
	tr = ''
	for t in lt:
		tr = tr + " " + t
	tr = tr.strip(" ")
	print(tr)
	return(tr)

def ly_to_km(x):
	k = x * 9.4607
	c = 12
	if k > 1:
		while k > 10:
			c = c + 1
			k = k / 10
	else:
		while k < 1:
			c = c - 1
			k = f * 10
	km = str(k) + "e+" + str(c) + " km"
	return(km)
dictionary = open(sys.argv[1] , "r")
binarian = open(sys.argv[2] , "r")
peace = open(sys.argv[3] , "r")
msg = open("message.txt", "w")
msgbn = open("binarian_message.txt" , "w")
cmp = open("computations.txt", "w")

dict = read_dictionary(dictionary)
for i in binarian.readlines():
	if i[0] != "+" and i[0] != "#" :
			msgbn.write(binarian_to_english(i, dict) + "\n")
	elif i[0] == "+":
		i = i.rstrip("\n")
		i = i.rstrip(" ")
		i = i.strip("+")
		i = i.strip(" ")
		l = i.split(" ")
		for word in l:
			if word in dict.keys():
				if dict[word] == "distance":
					for wordi in l:
						if wordi[0] == "1":
							nmbd = str(ly_to_km(bntodc(int(wordi))))
				elif dict[word] == "temperature":
					for wordi in l:
						if wordi[0] == "1":
							nmbt = str(bntodc(int(wordi)))
				elif dict[word] == "orbital-speed":
					for wordi in l:
						if wordi[0] == "1":
							nmbs = str(bntodc(int(wordi)))
print("Data about Binarian planet:\nDistance from the Earth:", nmbd , "\nPlanet temperature:" ,nmbt, "degrees Celsius\nOrbital speed:",nmbs ,"km/s")
cmp.write("Data about Binarian planet:\nDistance from the Earth: "+ nmbd + "\nPlanet temperature: " + nmbt + " degrees Celsius\nOrbital speed: "+ nmbs +"km/s")					
for i in peace.readlines():
	msg.write(eng_to_bin(i,dict) + "\n")

msgbn.close()
cmp.close()
dictionary.close()
binarian.close()
peace.close()
msg.close()

			
	
	
		
	

					
