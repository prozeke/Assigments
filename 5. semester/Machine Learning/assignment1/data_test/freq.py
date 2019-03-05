import sys
import pandas as pd

def find_country(csv, id):
	lstr = csv["Location"][id - 1]
	lstr = lstr.strip()
	lstr = lstr.strip("\"")
	str = lstr.split(",")[2]
	str = str.strip()
	str.strip("\"")
	str = str.strip()
	return str
##Give users data as argv[1]
users = pd.read_csv(sys.argv[1], sep = ";", encoding = "latin-1")
#Give Book Ratings as argv[2]
csv_ratings = pd.read_csv(sys.argv[2] , sep = ";", usecols = ["User-ID"], encoding = "latin-1")
freqs = dict()
user_set = set()
for i in csv_ratings["User-ID"]:
	country = find_country(users, i)
	num = freqs.get(country)
	if(num):
		if(i not in user_set):
			freqs[country] = num + 1
			user_set.add(i)
	else:
		freqs[country] = 1
		user_set.add(i)
for i in freqs.keys():
	print("Country:\t" + i + "\nFrequensy:\t" + str(freqs[i]))
	print("-----------------------------------")
sum = 0
for i in freqs.values():
	sum += i
print("sum = "+ str(sum) )
print("usa + canada = " + str(freqs["usa"] + freqs["canada"]))
