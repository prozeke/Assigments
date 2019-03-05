import sys
f = open(sys.argv[1], "r")
s = "not found\n"
try :
	s = f.readline()
except:
	print("exception 1")
	pass
print(s, end = '')
i = 20
num = 0
while(i > 0):
	print(s, end = "")
	try:
		s = f.readline()
		i -= 1
	except:
		print(s, end = "")
		num += 1
		pass
print(num)
