import sys
import pandas as pd
import numpy as np
import time

class User:
	def __init__(self, _ind = 0 ,_user_id = 0 , _loc = " ", _age = 0, _rates = None): 
		self.ind = _ind # index of the user in the user_list
		self.id = _user_id
		self.loc = _loc
		self.age = _age
		if(_rates != None):
			self.rates = _rates ##_rates will be ind to rate.
		else:
			self.rates = dict()
		
	def __eq__(self, other):
		if(type(self) != type(other)):
			return False
		return self.id == other.id
	
	def add_rate(self, isbn_str, rate):
		if(rate != 0):
			self.rates[isbn_str] = rate
	
	## Finds euclidian distance for the rates
    ## user != None would finds length only of the intersection of the vectors.
    ## isbn_str != None would consider the length except that element. Neccesary for validation
    ## this is neccesary for pearson coorelation
	def	rates_len(self, isbn_str = None, user = None):
		ret = 0
		if(user):
			for key in self.rates.keys():
				if(key != isbn_str):
					if(user.rates.get(key)):
						val = self.rates[key]
						ret += val * val
			return np.sqrt(ret)	 
		for key in self.rates.keys():
			if(key != isbn_str):
				val = self.rates[key]
				ret += val * val
		return np.sqrt(ret)	

	## regular dot product between vectors.
	## if isbn_str is not None then considers only shared elements between teo vector
	def dot(self, other, isbn_str = None):
		ret = 0
		for key in self.rates:
			if(key != isbn_str):
				other_val = other.rates.get(key)
				if(other_val):
					self_val = self.rates.get(key)
					ret += self_val * other_val
		return ret
	
	## returns the cosine similarity of two ratings
	## if intersect = True, returns cosine similarity as if there are only common elements in both vectors
	def cos_sim(self, other,isbn_str = None, intersect = False):
		dot_product =  self.dot(other, isbn_str)
		if(dot_product == 0):	## This if prevents divide by zero error
			return 0	
		elif(intersect):
			return dot_product /(self.rates_len(isbn_str,other) * other.rates_len(user = self)) 
		else:
			return dot_product /(self.rates_len(isbn_str) * other.rates_len()) 
	
	## finds the average value of the vector
	def average(self):
		ret = 0
		num = 0
		for val in self.rates.values():
			ret += val
			if(val != 0):
				num += 1
		if(num == 0):
			return 0
		return ret / num
		
	## normalizes vector for pearson coorelation.
	def normalize(self):
		n1 = dict()
		av = self.average()
		for key in self.rates.keys():
			if self.rates[key] != 0:
				n1[key] = self.rates[key] - av
		return n1
		
    ## returns the pearson coorelation value for two vectors
	def pearson_corr(self, other, isbn_str = None):
		n1 = self.normalize()
		n2 = other.normalize()
		u1 = User(_rates = n1)
		u2 = User(_rates = n2)
		return u1.cos_sim(other = u2, isbn_str = isbn_str, intersect = True)


class Min_heap:
	class El:
		def __init__(self, user = None, rate = -1):
			self.user = user
			self.rate = rate
		
		## defined less than and equal operators
		## I want to be able to sort the list
		def __lt__(self,other):
			return self.rate < other.rate
			
		def __eq__(self, other):
			return self.rate == other.rate
			
	def __init__(self, leng):	## leng is the fixed size of the heap. Will be equal to k	##
		self.lis = []			## when finding k_nn users									##	
		self.leng = leng
		e = self.El()
		for i in range(leng):
			self.lis.append(e)
	
	def swap(self, i, j):
		aux = self.lis[i]
		self.lis[i] = self.lis[j]
		self.lis[j] = aux	

	def sink(self, i):
		while(2*i + 1 < self.leng):
			k = 2*i + 1
			if( k + 1 < self.leng and self.lis[k+1] < self.lis[k]):
				k = k+1
			if(self.lis[k] < self.lis[i]):
				self.swap(k,i)
			i = k
	
	def min(self):
		return self.lis[0].rate
	
	def push(self, user, rate):
		p = self.El(user = user,rate = rate )
		self.lis[0] = p
		self.sink(0)
	
	## returns a list of el's which doesn't has a user equal to None
	def get_lis(self):
		ret = []
		for e in self.lis:
			if(e.user == None):
				pass
			else:
				ret.append(e)
		return ret

##reads the users from DataFrame object of pandas. Organizes dictionary as str user_id -> User user
def read_users(user_csv, user_dict): 
	user_csv["Age"].fillna("35",inplace = True )
	user_ind = 0
	user_list = []
	user = None
	loc = ""
	age = 0
	user_id = 0
	for row in user_csv.itertuples():
		user_id = int(row[1])	
		if(not pd.isnull(user_id)):
			user_id = int(user_id)
			loc = row[2]
			if( (not "usa" in loc) and (not "canada" in loc)):		## taking only usa and canada
				continue
			age = row[3]
			if(not pd.isnull(age)):
				age = int(age)
			else:
				age = 35
			if(user_dict.get(user_id)):
				continue
			user = User(user_ind, user_id,loc, age)
			user_list.append(user)
			user_dict[user_id] = user
			user_ind += 1	
		else:
			return user_list
	return user_list

## In rates.csv, some of the isbn numbers don't have some of their leading zero digits.
## This function is used to fix this issue 
def fill_zeros(isbn_str):
	if (len(isbn_str) >= 10):
		return	isbn_str
	else:
		isbn_str = "0" + isbn_str
		return fill_zeros(isbn_str)
		
def read_rates(rates_csv, user_dict):
	user_id = 0; rate = 0.0; isbn_str = "";  
	for row in rates_csv.itertuples():
		user_id = row[1]
		if(pd.isnull(user_id)):
			return
		user_id = int(user_id)
		user = user_dict.get(user_id)
		if(not user):	## if can't reach user from user_dict, pass that row
			continue
		isbn_str = row[2].strip()
		isbn_str = fill_zeros(isbn_str)
		rate = float(row[3])
		if(rate == 0):	## For not taking the rates that has a value of zeros 
			continue
		user_dict[user_id].add_rate(isbn_str, rate)
	

## finds k nearest neighboor with respect to pearson correlation.
def knn_users(user_list, user, isbn_str, k):
	heap = Min_heap(leng = k)
	for e in user_list:
		rate = e.rates.get(isbn_str)
		if(not rate):
			continue
		corr = user.pearson_corr(e, isbn_str)
		if(corr > heap.min()):
			heap.push(e, corr)
	ret = heap.get_lis()
	return ret

## predicts with repect to knn algorithm. knn is the list of k nearest neighboor, isbn_str is books isbn.
def knn_predict(knn, isbn_str):
	av = 0
	for user in knn:
		av += user.rates[isbn_str]	## total sum of knn users prediction for this book
	if(av != 0):
		return av / len(knn) ## returns the average of the of total sum 
	else:			
		return 0 ## prevents divide by zero error

## weighted knn prediction
def w_knn_predict(user, knn, isbn_str = None):
	weight_sum = 0
	weighted_sum = 0
	weight = 0
	for u in knn:
		weight = user.pearson_corr(u,isbn_str)
		weight_sum += weight
		weighted_sum += weight * u.rates[isbn_str]
	if(weight_sum == 0):
		return 0	## prevents divide by zero error
	ret =  weighted_sum / weight_sum
	if(ret < 0):
		print("prediction is less then zero. Is %d"%ret)
	return weighted_sum / weight_sum
	
## Defining mean absoulete error.	
def mae(predict_list, target_list):
	if( len(predict_list) != len(target_list) ):
		print("predict_list and target_list must have equal lengths")
		quit()
	abs_sum = 0
	length = len(predict_list)
	if(length == 0):
		print("ERROR!!!\nlength = 0 in function mae")
		return 0
	for i in range(length):
		abs_sum += abs(target_list[i] - predict_list[i])
	return abs_sum / length		

## Defining mean squared error
def mse(predict_list, target_list):
	if( len(predict_list) != len(target_list) ):
		print("predict_list and target_list must have equal lengths")
		quit()
	abs_squared_sum = 0
	length = len(predict_list)
	if(length == 0):
			print("ERROR!!!\nlength = 0 in function mse")
			return 0	
	for i in range(length):
		pred = predict_list[i]
		target = target_list[i]
		squared = target - pred;	squared = squared * squared
		abs_squared_sum += squared
	return abs_squared_sum / length

## creates a two dimensinol array	
def d2_array(i):
	i = int(i)
	ret = []
	for j in range(i):
		ret.append([])
	return ret
	
## for each k, validation is done for k as treshold
## for each treshold mae and mse values are predicted from k = 1 to k = treshold 
## results are kept in a lower triangular matrix, where tresholds are row numbers and k valuesare column numbers for mae and mse
## These two matrix are represented as a 1 dimensional format
## ret[0] is mae matrix, ret[1] is mse matrix
def validation(train_list, validation_list, w_knn = True, k = 1):
	predict_list = d2_array( (k*(k+1)) / 2)
	target_list = d2_array(k)
	predict_num = np.zeros(k)
	k_preds = []
	for user in validation_list:
		for isbn_str in user.rates.keys():
			how_many = 0	
			knn_lis = []
			knn = knn_users(train_list, user, isbn_str, k)
			knn.sort()##!!
			for i in range(len(knn)):
				knn[i] = knn[i].user
			for i in range(1,k + 1):
				if(len(knn) >= i):
					knn_lis.append(knn[-i :])
					target_list[i - 1].append(user.rates[isbn_str])
					how_many += 1
			for i in range(1, how_many + 1):
				x = i-1
				predict_num[x] += 1
				for j in range(i):
					last = j + 1
					if(w_knn):
						predict_list[int((x*(x+1)) / 2) + j].append(w_knn_predict(user = user, knn = knn_lis[x][-last:], isbn_str = isbn_str))		
					else:
						predict_list[int((x*(x+1)) / 2) + j].append(knn_predict(knn = knn_lis[x][-last:], isbn_str = isbn_str))

	ret = np.zeros([2,int((k*(k+1)) / 2)])
	for i in range(1,k +1):
		x = i - 1 
		for j in range(i):
			ret[0][int((x*(x+1)) / 2 + j)] = mae(predict_list[int((x*(x+1)) / 2 + j)], target_list[x])
			ret[1][int((x*(x+1)) / 2 + j)] = mse(predict_list[int((x*(x+1)) / 2 + j)], target_list[x])
	return ret


## validation for each partition. Finds the average of the errors for each set
def cross_validation(user_list, w_knn = True, k = 1, fold = 5):	
	mae_errors = np.zeros(int((k*(k+1)) / 2))
	mse_errors = np.zeros((int((k*(k+1)) / 2)))
	buff = len(user_list)//fold
	for i in range(fold):
		if(i == 0):
			vali_list = user_list[0:buff ]
			train_list = user_list[buff:]
		elif(i != fold - 1):
			vali_list = user_list[buff * i: buff*(i + 1)]
			train_list = user_list[0:buff*i] + user_list[buff*(i+1):]
		else:
			vali_list = user_list[-buff*i:]
			train_list = user_list[:buff*i]
		errors = validation(train_list = train_list, validation_list = vali_list, w_knn = w_knn, k = k)
		mae_errors = mae_errors + errors[0]	
		mse_errors = mse_errors + errors[1]
	mae_errors = mae_errors / fold
	mse_errors = mse_errors / fold
	print("fold = %d"%fold)
	for i in range(1,k +1):
		x = i - 1 
		print("k = " + str(i))
		for j in range(i):
			print("k for this set = %d"%(j + 1))
			print("mae--->\t" + str(mae_errors[int(x*(x+1) // 2 + j)]))
			print("mse--->\t" + str(mse_errors[int(x*(x+1) // 2 + j)]))

## START MAIN FUNCTION ##
## argv[1] should be user_info.csv      ##
## argv [2] should be book_ratings.csv  ##

## READING FROM FILE AND CREATING INITIALIZING DATA STRUCTERS  ##
## argv[1] should be user_info.csv
user_csv = pd.read_csv(sys.argv[1], sep = ";", encoding = "latin-1")
rates_csv = pd.read_csv(sys.argv[2], encoding = "latin-1", sep = ";", usecols = ["User-ID"])
user_csv = pd.merge(user_csv, rates_csv, on = ["User-ID"])

user_dict = dict()
user_list = read_users(user_csv, user_dict)
print(len(user_list))
## user_list and user_dictionary cereated

## reads rates and creates user  rates
rates_csv = pd.read_csv(sys.argv[2], encoding = "latin-1", sep = ";")
read_rates(rates_csv, user_dict)

##	READING IS DONE!	##

start_time = time.time()
## VALIDATION ##
start_time = time.time()
cross_validation(user_list = user_list , w_knn= True, k = 10, fold = 5)
end_time = time.time()
print( "total "+ str(abs(start_time - end_time))+ " second passed")
## END MAIN FUNCTION
















