import sys
import pandas as pd

def fill_zeros(isbn_str):
	if(len(isbn_str) >= 10):
		return	isbn_str
	else:
		isbn_str = "0" + isbn_str
		return fill_zeros(isbn_str)
## argv[1] is rates
rates_csv = pd.read_csv(sys.argv[1], sep = ";", encoding = "latin-1", usecols = ["ISBN"])
book_set = set()
num = 0
for row in rates_csv["ISBN"]:
	num += 1
	if(pd.isnull(row)):
		print("break")
		break
	row = fill_zeros(row)
	print("smt")
	if(row in book_set):
		continue
	else:
		book_set.add(row)
print(len(book_set))
print(num)
