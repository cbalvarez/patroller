import urllib2
import json

def ask(url):
	req = urllib2.Request(url)	
	res = urllib2.urlopen(req)	
	s = res.readlines()
	return json.loads(s[0])

def buildProcess(json_trace):
	return "/usr/sbin/tcpdump -i any \'" + json_trace['tcpdumpParams'] + "\' -w pcap"

json_trace = ask("http://localhost:9290/patroller/trace/aaa")
if json_trace['shouldTrace'] == True:
	print buildProcess(json_trace)
