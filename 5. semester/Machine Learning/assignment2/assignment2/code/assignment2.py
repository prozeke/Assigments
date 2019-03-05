## input files are defined as global variables
REAL_FILE = "clean_real-Train.txt"
FAKE_FILE = "clean_fake-Train.txt"


import math
import heapq
from sklearn.feature_extraction.text import ENGLISH_STOP_WORDS

class Word:
	## Word initialization. logPr numbers will be firstly, the frequency of the word for each class. 
	## They are initialized to one for laplace smoothing.
	## Then, they will be converted to probablitiies of the words given class.
	## Ex: logPrFake = Pr(word | fake).
	## At last, they will be kept as log probabilites.
	## Actual word (_word) is only kept for displaying it later on.
	def __init__(self, _word):
		self.word = _word
		self.prReal = 0.0
		self.prFake = 0.0
		self.logPrFake = 1.0
		self.logPrReal = 1.0
		
class Bow:
	## Bow stands for bag of words.
	## WordNum variables will be used when conditional probabilities are found.
	## Word initialization. logPr numbers will be firstly, the frequency of each class. 
	## Then, logPr* values will be converterd to be the probability of an entry is real or fake.
	## Ex. logPrReal = pr(Real).
	## At last, they will be kept as log probabilites.
	## It has the same variable name with Word attributes. DO NOT CONFUSE THEM!!!
	def __init__(self):
		self.realWordNum = 0
		self.fakeWordNum = 0
		self.prReal = 0.0
		self.prFake = 0.0
		self.logPrReal = 0.0
		self.logPrFake = 0.0
		self.words = dict()

	def get(self, word):
		return words.get(word)
		
	def addWord(self,_word,isReal):
		el = self.words.get(_word)	## El is equal to word object retrieved from dictionary
		if(el == None):		## If there is no object with that string, create one.
			el = Word(_word)
			self.words[_word] = el			
			self.realWordNum += 1	## this line is for laplace smoothing
			self.fakeWordNum += 1	## It makes the assumption that each class has a non-visible entry which contains all the words. 
		if(isReal):
			self.realWordNum += 1
			el.logPrReal += 1	## logPrReal is now frequency. Notice that this pr stands for conditional probability
		else:
			self.fakeWordNum += 1
			el.logPrFake += 1	## logPrFake is now frequency. Notice that this pr stands for conditional probability.
					
	def addEntry(self, entry,isReal):
		if(len(entry) == 0):
			return
		if(isReal):
			self.logPrReal += 1			
		else:
			self.logPrFake += 1			
		for _word in entry:
			self.addWord(_word, isReal)

	## Sets actual log probabilities and probabilities to neccesary fields.
	## It is a must to call this method after adding all the entries that our model has.
	def findPr(self):
		entryNum = self.logPrReal + self.logPrFake
		self.prReal = self.logPrReal / entryNum
		self.prFake = self.logPrFake / entryNum
		self.logPrReal = math.log(self.logPrReal / entryNum)
		self.logPrFake = math.log(self.logPrFake / entryNum)
		for key in self.words.keys():
			self.words[key].prReal = self.words[key].logPrReal / self.realWordNum
			self.words[key].prFake = self.words[key].logPrFake / self.fakeWordNum
			self.words[key].logPrReal = math.log(self.words[key].logPrReal / self.realWordNum)
			self.words[key].logPrFake = math.log(self.words[key].logPrFake / self.fakeWordNum)
			
	## Predicts entry. If predicts real news, return True. Otherwise, returns False		
	def predict(self, entry):
		real = self.logPrReal
		fake = self.logPrFake
		for i in entry:
			w = self.words.get(i)				
			if(w == None):
				continue
			else:
				real += w.logPrReal
				fake += w.logPrFake
		if(real > fake):
			return True
		else:
			return False

	## Finds the probability of the class given word.
	## Pr(C|word) so to say
	## word is an instance of Word. isReal is boolean.
	def presence(self, word, isReal):
		if(isReal):
			prGivenClass = word.prReal
			prGivenOther = word.prFake
			prClass = self.prReal
			prOther = self.prFake
		else:
			prGivenClass = word.prFake
			prGivenOther = word.prReal
			prClass = self.prFake
			prOther = self.prReal
		prWord = prGivenClass * prClass + prGivenOther * prOther
		return (prGivenClass * prClass) / prWord

	
	## Finds the probability of the class given the absence of the word.
	## Pr(C|word') so to say
	## word is an instance of Word. isReal is boolean.
	def absence(self, word, isReal):
		if(isReal):
			prGivenClass = 1 - word.prReal
			prGivenOther = 1 - word.prFake
			prClass = self.prReal
			prOther = self.prFake
		else:
			prGivenClass = 1 - word.prFake
			prGivenOther = 1 - word.prReal
			prClass = self.prFake
			prOther = self.prReal
		prWord = prGivenClass * prClass + prGivenOther * prOther
		return (prGivenClass * prClass) / prWord
		
	## Returns n words whose presence most strongly predicts that the news is real.
	## If isReal == False, then it returns the same words but for fake news.
	def nPresence(self,n,isReal):
		ret = []
		wordList = self.words.values()
		num = 0
		for word in wordList:
			if(num < n):
				el = El(word, self.presence(word,isReal))
				heapq.heappush(ret,el)
			else:
				el = El(word, self.presence(word,isReal))
				if(el > ret[0]):
					heapq.heappop(ret)
					heapq.heappush(ret,el)
			num += 1
		return ret

	## Returns n words whose absence most strongly predicts that the news is real.
	## If isReal == False, then it returns the same words but for fake news.
	def nAbsence(self,n,isReal):
		ret = []
		wordList = self.words.values()
		num = 0
		for word in wordList:
			if(num < n):
				el = El(word, self.absence(word,isReal))
				heapq.heappush(ret,el)
			else:
				el = El(word, self.absence(word,isReal))
				if(el > ret[0]):
					heapq.heappop(ret)
					heapq.heappush(ret,el)
			num += 1
		return ret

class Data:
	## It is used to hold data with label
	def __init__(self,_line, _label):
		self.line = _line
		self.label = _label

class El:
	## El stands for element.
    ## word is an instance of class Word and pr is the probability of that word(P(word)).
	## Will be used to find "most smt. 10" word problems.
	## It is created comparable to be compatable with python build-in heap structure 
	def __init__(self, _word, _pr):
		self.word = _word
		self.pr = _pr
	
	def __lt__(self,other):
		return self.pr < other.pr
		
	def __gt__(self,other):
		return self.pr > other.pr
		
	def __eq__(self, other):
		return self.pr > other.pr
		
	def __le__(self,other):
		return self.pr <= other.pr
		
	def __str__(self):
		return self.word.word

## Returns the entry for unigram implementation
def unigramEntry(line):
	line = line.split(" ")
	return line

## Returns entry for bigram implementation
## If line contains of only one word, then it is same as unigramEntry			
def bigramEntry(line):
	line = line.split(" ")
	ret = []				
	if(len(line) == 0):
		return ret
	if(len(line) == 1):
		return line
	for i in range(len(line) - 1):
		ret.append(line[i] + " " + line[i + 1])
	return ret		
		
## clears all stop words from line with respect to dictionary of stopWords			
def clearStopWords(line,stopWords):
	line = line.split(" ")
	ret = ""
	for i in line:
		if(stopWords.get(i) == None):
			ret = ret + i + " "
		else:
			continue
	ret = ret.strip()
	return ret

## Clears uneccesary information from line. Also clears stop words.
def clearLine(line,stopWords = dict()):
	line = line.strip("\n")
	line = line.strip("\r")
	line = line.strip()
	line = line.lower()
	line = clearStopWords(line,stopWords)
	return line

def readAll(stopWords = dict()):
	filePtr = open(REAL_FILE, "r" )	## Real_FILE is a global variable		
	isReal = True		
	bows = [Bow(), Bow()]	## bows[0] is for Unigram bow and bows[1] is for bigram bow.		
	read(filePtr,stopWords,bows,isReal)		
	filePtr.close()
	filePtr = open(FAKE_FILE, "r" )	## FAKE_FILE is a global variable			
	isReal = False		
	read(filePtr,stopWords,bows,isReal)	
	filePtr.close()		
	bows[0].findPr()	## This is a must to call function after initializing
	bows[1].findPr()	## It sets the probability related attributes to correct values
	return bows

def read(filePtr, stopWords, bows, isReal):
	line = filePtr.readline()
	while(line):
		line = clearLine(line,stopWords)
		if(len(line) == 0):
			line = filePtr.readline()
			continue	
		uEntry = unigramEntry(line)
		biEntry = bigramEntry(line)
		bows[0].addEntry(uEntry,isReal)
		bows[1].addEntry(biEntry,isReal)
		line = filePtr.readline()
		
## bows[0] is unigram bow and bows[1] is bigram bow.
## dataList is labeled data with label and line.
## Returns accuracy for unigram and bigram
## ret[0] = accuracy of unigram. ret[1] is accuracy of bigram.
def test(bows, dataList, stopWords = dict()):
	trueUni = 0
	falseUni = 0
	trueBi = 0
	falseBi = 0	
	for data in dataList:
		line = clearLine(data.line, stopWords)
		uniEntry = unigramEntry(line)
		biEntry = bigramEntry(line)
		if(bows[0].predict(uniEntry) == data.label):
			trueUni += 1
		else:
			falseUni += 1
		if(bows[1].predict(biEntry) == data.label):
			trueBi += 1
		else:
			falseBi += 1
	ret = []
	ret.append(float(trueUni) / (falseUni + trueUni))
	ret.append(float(trueBi) / (trueBi + falseBi))
	ret[0] *= 100
	ret[1] *= 100
	return ret

## Will be used for test error generation.
## Sentences in txtFile must have only words. All punctations are considered as part of words.
## Sentences in txt file will be later used as vectors for prediction.
def getData(txtFile, dataList, isReal):
	filePtr = open(txtFile,"r")
	line = filePtr.readline()
	while(line):
		line = clearLine(line)
		if(len(line) == 0):
			line = filePtr.readline()
			continue
		data = Data(line,isReal)
		dataList.append(data)
		line = filePtr.readline()
	filePtr.close()
	return

## Reads data from a csv file.
## HeadLines are first column and labels are second column
def getDataCsv(csvFile, dataList):
	testCsv = pd.read_csv(csvFile,sep = ",")
	for row in testCsv.itertuples():
		line = row[1]
		if(row[2] == "real"):
			isReal = True
		else:
			isReal = False
		line = clearLine(line)
		if(len(line) == 0):
			continue
		data = Data(line,isReal)
		dataList.append(data)
		
def wordListToStr(l):
	ret = []
	if(len(l) == 0 or l == None):
		return ""
	for i in l:
		ret.append(i.word.word)
	return str(ret)

## Adds words from wordTxt to the dictionary, stopWords 
def addToDict(wordSet, stopWords):
	for word in wordSet:
		stopWords[word] = True



stopWords = dict()
#addToDict(ENGLISH_STOP_WORDS, stopWords)
bows = readAll(stopWords)
dataList = []
getData(REAL_FILE, dataList, True)			
getData(FAKE_FILE,dataList, False)
accuracy = test(bows, dataList, stopWords)
print(accuracy)
print("real top ten presence unigram:"); print(wordListToStr(bows[0].nPresence(10,True)))
print()
print("fake top ten presence unigram:"); print(wordListToStr(bows[0].nPresence(10,False)))
print()
print("real top ten absence unigram:"); print(wordListToStr(bows[0].nAbsence(10,True)))
print()
print("fake top ten absence unigram:"); print(wordListToStr(bows[0].nAbsence(10,False)))			
print()
print()
print("real top ten presence bigram:"); print(wordListToStr(bows[1].nPresence(10,True)))
print()
print("fake top ten presence bigram:"); print(wordListToStr(bows[1].nPresence(10,False)))
print()
print("real top ten absence bigram:"); print(wordListToStr(bows[1].nAbsence(10,True)))
print()
print("fake top ten absence bigram:"); print(wordListToStr(bows[1].nAbsence(10,False)))	
			
			
			
			







