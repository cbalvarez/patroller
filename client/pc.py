import urllib2
import json
import subprocess
import time
import datetime
import os
import re

class TcpdumpLine:
	def __init__(self, vm, srcIp, srcPort, dstIp, dstPort):
		self.host = "host"
		self.vm  = vm
		self.srcIp = srcIp
		self.srcPort = srcPort
		self.dstIp = dstIp
		self.dstPort = dstPort
		self.timestamp = datetime.datetime.fromtimestamp(time.time()).strftime('%d-%m-%Y %H:%M:%S') 

	def __eq__(self, other):
		if (isinstance(other, self.__class__)):
			return (self.srcIp == other.srcIp and self.srcPort == other.srcPort and self.dstIp == other.dstIp and self.dstPort == other.dstPort)
		else:
			return False
			
	def __hash__(self):
		return hash (self.srcIp + self.srcPort + self.dstIp + self.dstPort)

	def __str__(self):
		return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent = 4) 
	
	

def log(msg):
	print msg

def ask(url):
	req = urllib2.Request(url)	
	res = urllib2.urlopen(req)	
	s = res.readlines()
	return json.loads(s[0])


def performCollection(json_trace):
	params = buildProcess(json_trace)
	ttl = json_trace['timeToTraceInMinutes'] * 60
	runTcpdump(params, 30)

def buildProcess(json_trace):
	return ["/usr/sbin/tcpdump", "-i", "any",json_trace['tcpdumpParams'],"-w","pcap"]

def runTcpdump(commands,tts):
	log("about to run tcpdump " + str(commands[1:]))
	pid = subprocess.Popen(commands).pid
	log("started " + str(pid))
	time.sleep(tts)
	os.kill(pid, 9)	

def getVm(ipA, ipB):
	return "vm-prueba-00"

def processLine(l):
	g = re.search(".+ IP (([0-9]{1,3}\.){3}[0-9]{1,3})\.([0-9]+) > (.+)\.([0-9]+)\:", l)
	if (g == None):
		return None
	origIp = g.group(1)
	origPort = g.group(3)
	dstIp = g.group(4)
	dstPort = g.group(5)
	return TcpdumpLine(getVm(origIp, dstIp), origIp, origPort, dstIp, dstPort)

def processFile():
	output = subprocess.Popen(["/usr/sbin/tcpdump","-n","-r","pcap"], stdout = subprocess.PIPE)	
	out = output.stdout.read()
	return set(map (lambda x:processLine(x), out.split("\n")))

def sendData(dataToSend,url):
	#c = httplib.HTTPConnection(server)
	headers = {"Content-type": "application/json", "Accept": "text/plain"}
	for d in dataToSend:
		if (d != None):
			data = json.dumps(d, default=lambda o: o.__dict__, sort_keys=True, indent = 4) 
			req = urllib2.Request(url, data, {'Content-Type': 'application/json'})
			urllib2.urlopen(req)
			
	
#json_trace = ask("http://localhost:9290/patroller/trace/aaa")
#if json_trace['shouldTrace'] == True:
#	performCollection(json_trace)		
r = processFile()
sendData(r, 'http://localhost:9290/patroller/report')
