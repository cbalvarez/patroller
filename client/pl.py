import re

def buildDict(groups):
	print groups
	return dict(map ( lambda i:(groups[i] , groups[0]), range(1,4)))


def processLine(l):
	g =  map ( lambda x: buildDict(re.search(".*\| (\S+) +\| ACTIVE \| private=(\S+), (\S+), (\S+)",x).groups()), filter( lambda x: "private" in x  ,open("listado").readlines()))
	

def processVmList():
	map ( lambda x: processLine(x), filter( lambda x: "private" in x  ,open("listado").readlines()))

processVmList()
