import urllib2
import json
import subprocess
import time
import datetime
import os
import re
import platform
import ConfigParser
import logging

Config = ConfigParser.ConfigParser()
Config.read("./pc.ini")

user = Config.get("Cloudia","usr")
passwd = Config.get("Cloudia","passwd") 
cloudUrl = Config.get("Cloudia","url")
patrollerUrl = Config.get("Patroller","url")
fileLog = Config.get("Logging","file")

logging.basicConfig(filename = fileLog , level = logging.DEBUG, format='%(asctime)s %(message)s')


class TcpdumpLine:
	def __init__(self, vm, srcIp, srcPort, dstIp, dstPort):
		self.host = platform.node ()
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
	
	

def getClusters(url):
	auth_handler = urllib2.HTTPPasswordMgrWithDefaultRealm()
	auth_handler.add_password(None, url, 'perfdb@cgp', 'perfdb')
	handler = urllib2.HTTPBasicAuthHandler(auth_handler)
	opener = urllib2.build_opener(handler)
	res = opener.open(url)
	s = res.read()
	return json.loads(s)
	

def instanceName(instance):
	return "%s-%02d" % (instance['cluster'], instance['index']) 


def buildVmDict(url):
	clustersJson = getClusters(url)
	r = {}
	for cluster in clustersJson:
		for i in cluster['instances']:
			r.update ({i['adminAddr'] : instanceName(i)}) 
			r.update ({i['trafficAddr'] : instanceName(i)}) 
	return r
	


def log(msg):
	logging.info(msg)	

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
	return ["/usr/sbin/tcpdump", "-i", "any",'(tcp or udp ) and ('  + json_trace['tcpdumpParams'] + ')',"-w","pcap"]

def runTcpdump(commands,tts):
	log("about to run tcpdump " + str(commands[1:]))
	pid = subprocess.Popen(commands).pid
	log("started " + str(pid) + " waiting for " + str(tts))
	#time.sleep(tts)
	time.sleep(120)
	os.kill(pid, 9)	

def getVm(ipA, ipB):
	if (vmDictionary.get(ipA) != None):
		return vmDictionary.get(ipA)	
	if (vmDictionary.get(ipB) != None):
		return vmDictionary.get(ipB)
	return "Unkwnown"

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
	headers = {"Content-type": "application/json", "Accept": "text/plain"}
	for d in dataToSend:
		if (d != None):
			log("to send" + str(d))
			data = json.dumps(d, default=lambda o: o.__dict__, sort_keys=True, indent = 4) 
			req = urllib2.Request(url, data, {'Content-Type': 'application/json'})
			urllib2.urlopen(req)
			
	
log("starting... ")
json_trace = ask(patrollerUrl + "/trace/" + platform.node() )
log("trace 2" + str(json_trace))
if json_trace['shouldTrace'] == True:
	log("staring to trace ")
	vmDictionary =  buildVmDict(cloudUrl)
	log("instances dictionary loaded")
	log(json_trace)
	performCollection(json_trace)		
	log("collected. starting to process file")
	r = processFile()
	log("file processed. starting to send")
	sendData(r,patrollerUrl + "/report")
	log("data send. ")
	os.remove("./pcap")
else:
	log("nothing to do for now")

