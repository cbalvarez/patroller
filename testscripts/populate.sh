curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"10.70.0.0/16\", \"filterType\":\"net\", \"reason\":\"red interna    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"10.2.0.0/16\", \"filterType\":\"net\", \"reason\":\"red balance    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"192.168.0.0/16\", \"filterType\":\"net\", \"reason\":\"red nat    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"10.1.0.0/16\", \"filterType\":\"net\", \"reason\":\"red interna no openstack  \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"172.16.0.0/16\", \"filterType\":\"net\", \"reason\":\"vpn juniper    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"50.31.164.0/24\", \"filterType\":\"net\", \"reason\":\"new relic    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"38.99.237.40\", \"filterType\":\"host\", \"reason\":\"despegar.com     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"169.254.169.254\", \"filterType\":\"host\", \"reason\":\"broadcast     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"127.0.0.1\", \"filterType\":\"host\", \"reason\":\"localhost     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"208.72.9.0/24\", \"filterType\":\"net\", \"reason\":\"navitaire     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"208.72.8.0/24\", \"filterType\":\"net\", \"reason\":\"navitaire     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"82.150.224.0/21\", \"filterType\":\"net\", \"reason\":\"Amadeus     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"151.193.48.0/20\", \"filterType\":\"net\", \"reason\":\"Sabre     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"10.11.0.0/16\", \"filterType\":\"net\", \"reason\":\"red interna    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"255.255.255.255\", \"filterType\":\"host\", \"reason\":\"broadcast     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"169.254.255.255\", \"filterType\":\"host\", \"reason\":\"broadcast     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"10.77.0.0/16\", \"filterType\":\"net\", \"reason\":\"red iscsi    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"216.113.128.0/19\", \"filterType\":\"net\", \"reason\":\"travelport     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"107.14.32.0/20\", \"filterType\":\"net\", \"reason\":\"google     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"216.58.192.0/19\", \"filterType\":\"net\", \"reason\":\"google     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"216.22.100.0/24\", \"filterType\":\"net\", \"reason\":\"despegar     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"64.76.243.0/24\", \"filterType\":\"net\", \"reason\":\"despegar     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"38.99.237.0/24\", \"filterType\":\"net\", \"reason\":\"despegar     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"46.16.252.17\", \"filterType\":\"host\", \"reason\":\"globall collect    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"200.124.123.52\", \"filterType\":\"host\", \"reason\":\"visa     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"209.133.193.138\", \"filterType\":\"host\", \"reason\":\"keycdn\"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"200.82.126.12\", \"filterType\":\"host\", \"reason\":\"payment service platform    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"209.107.217.192\", \"filterType\":\"host\", \"reason\":\"akamai ssl    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"63.148.206.0/23\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.202.36.0/23\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"184.51.150.0/23\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.3.48.0/20\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.15.5.0/24\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.202.16.0/20\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.203.100.0/22\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"69.64.187.29\", \"filterType\":\"host\", \"reason\":\"safetypay     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"204.2.187.0/24\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"209.107.217.177\", \"filterType\":\"host\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"173.194.219.0/24\", \"filterType\":\"net\", \"reason\":\"google     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"165.254.49.143\", \"filterType\":\"host\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"113.197.123.2\", \"filterType\":\"host\", \"reason\":\"american express    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"54.149.20.228\", \"filterType\":\"host\", \"reason\":\"datastaxs     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.202.48.0/20\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.208.80.0/20\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"23.76.112.0/20\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"108.166.0.205\", \"filterType\":\"host\", \"reason\":\"payulatam     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"174.35.32.147\", \"filterType\":\"host\", \"reason\":\"cdnetworks     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"172.19.200.6\", \"filterType\":\"host\", \"reason\":\"visa cobros    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"63.141.192.0/23\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"165.254.0.32\", \"filterType\":\"host\", \"reason\":\"akamai ssl    \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"165.254.0.41\", \"filterType\":\"host\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"165.254.0.8\", \"filterType\":\"host\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"165.254.49.148\", \"filterType\":\"host\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"208.86.144.203\", \"filterType\":\"host\", \"reason\":\"encompass     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"52.10.188.176\", \"filterType\":\"host\", \"reason\":\"datastax     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"74.125.0.0/16\", \"filterType\":\"net\", \"reason\":\"google     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"165.254.49.157\", \"filterType\":\"host\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"54.77.37.118\", \"filterType\":\"host\", \"reason\":\"bidder - clicks   \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"63.151.118.0/23\", \"filterType\":\"net\", \"reason\":\"akamai     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"54.236.170.121\", \"filterType\":\"host\", \"reason\":\"flightstats     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"157.189.192.67\", \"filterType\":\"host\", \"reason\":\"terracota     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"200.185.163.210\", \"filterType\":\"host\", \"reason\":\"voegol     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"201.131.2.103\", \"filterType\":\"host\", \"reason\":\"vivaaerobus     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"177.71.197.20\", \"filterType\":\"host\", \"reason\":\"sete     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"54.94.130.253\", \"filterType\":\"host\", \"reason\":\"passaredo     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"54.94.165.143\", \"filterType\":\"host\", \"reason\":\"sete     \"}" http://localhost:9290/patroller/allowed
curl --header 'Content-type:application\/json' -X POST -d "{ \"filterValue\":\"54.94.209.191\", \"filterType\":\"host\", \"reason\":\"passaredo     \"}" http://localhost:9290/patroller/allowed
