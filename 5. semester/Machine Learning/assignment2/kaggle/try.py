import pandas as pd

test_csv = pd.read_csv("test.csv",sep = ",")
for row in test_csv.itertuples():
	print(row[2])
