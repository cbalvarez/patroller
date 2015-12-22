json="{\"idExplain\":\"$1\", \"start\":\"01-01-2000 00:00:00\", \"end\":\"01-01-2030 00:00:00\" }"
curl --header 'Content-Type:application/json'  -X POST -d "$json" http://localhost:9290/patroller/testexplainbulk

