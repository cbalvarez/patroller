json="{\"reason\":\"$1\", \"filterValue\":\"$2\", \"filterType\":\"$3\" }" 
echo $json
curl --header 'Content-Type:application/json'  -X POST -d "$json" http://$4/patroller/allowed

