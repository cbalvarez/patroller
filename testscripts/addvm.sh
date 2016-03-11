json="{\"vm\":\"$1\"}"
echo $json
curl --header 'Content-Type:application/json'  -X POST -d "$json" http://$2/patroller/svm

