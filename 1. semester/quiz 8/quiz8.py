file = open("paths.txt","r")
global dpath = {}
for i in file.readlines():
	i = i.rstrip("\n")
	l = i.split(":")
	dpath[l[0]] = l[1].split(" ")
def  path_analyze(d,x):
	ans = []
	if x == 0:
		return [d]
	else:
		try:
			for i in dpath[d]:
				ans.extend(path_analyze(i,x-1))
		except KeyError:
			return []
			