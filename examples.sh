# get birds (tuples)
curl -v localhost:8080/api/v1/birds/tuples | jq '.'

# get natural reserve
curl -v localhost:8080/api/v1/reserves/1 | jq '.'
# get natural reserves (tuples)
curl -v localhost:8080/api/v1/reserves/tuples | jq '.'
# create natural reserve
curl -v -X POST -H 'Content-Type: application/json' -d '{"name": "Hyde Park", "regionId": 1}' localhost:8080/api/v1/reserves | jq '.'
# update natural reserve
curl -v -X POST -H 'Content-Type: application/json' -d '{"name": "Central Park", "regionId": 1}' localhost:8080/api/v1/reserves/1 | jq '.'
# delete natural reserve
curl -v -X DELETE localhost:8080/api/v1/reserves/1 | jq '.'

# get chances by month
curl -v localhost:8080/api/v1/chances?date=2019-06-01 | jq '.'
