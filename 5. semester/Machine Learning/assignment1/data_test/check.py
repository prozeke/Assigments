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
##chekcs whether argument number correct or not. You save to add with the order User_info Book_ratings
if(len(sys.argv) != 3):
	print("takes arguments user file and rating file")
	quit()
##Actual code
csv_user = pd.read_csv(sys.argv[1] , sep = ";", usecols = ["Location"], encoding = "latin-1")
csv_ratings = pd.read_csv(sys.argv[2] , sep = ";", usecols = ["User-ID"], encoding = "latin-1")
country = ""
country_set = set()
for i in csv_ratings["User-ID"]:
	country = find_country(csv_user, i)
	if(country != "usa" and country != "canada"):
		print("wrong country " + country)
		country_set.add(country)
print("--------------------------------------")
print(country_set)
