import sys

def find_k(s):
	s = s.split("=")
	s = s[1]
	s = s.strip("\n")
	s = s.strip(" ")
	return int(s)

def find_err(s):
	s = s.split(">")
	s = s[1]
	s = s.strip("\n")
	s = s.strip(" ")
	s = s.strip("\t")
	print(s)
	return float(s)
	
	
f = open(sys.argv[2], "w")
r = open(sys.argv[1], "r")
lines = r.readlines()
k = 10
f.write("TRESHOLD;K;MAE;MSE\n")
lines = lines[2:]
i = 1
pos = 0
while(i<=k):
	tresh = find_k(lines[pos])
	print("***********%d*************"%tresh)
	pos += 1
	i += 1
	for j in range(tresh):
		print(pos)
		k_val = find_k(lines[pos]); pos+=1
		mae = find_err(lines[pos]); pos+=1
		mse = find_err(lines[pos]); pos+=1
		f.write("%d;%d;%.4f;%.4f\n"%(tresh,k_val,mae,mse))
	f.write(";;;\n")
f.close()
r.close()
		
		
		
		
		
		
		
		
		
		
		
		
		
		
