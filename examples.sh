curl -v localhost:8080/api/v1/birds/tuples | jq '.'

curl -v localhost:8080/api/v1/reserves/tuples | jq '.'

curl -v localhost:8080/api/v1/chances?date=2019-06-30 | jq '.'
