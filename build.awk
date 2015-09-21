{	
	filterValue = $3
	filterType = $2
	reason = $4 " " $5 " " $6 " " $7 " " $8 " " $ 9 
	json = "\"{ \\\"filterValue\\\":\\\"" filterValue "\\\", \\\"filterType\\\":\\\"" filterType "\\\", \\\"reason\\\":\\\"" reason "\\\"}\""
	print "curl --header 'Content-type:application\/json' -X POST -d " json " http://localhost:9290/patroller/allowed"
}
