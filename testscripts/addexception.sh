json="{\"reason\":\"$1\", \"filterValue\":\"$2\", \"filterType\":\"$3\" }" 
echo $json
curl --header 'Content-Type:application/json'  -X POST -d "$json" http://localhost:9290/patroller/allowed

