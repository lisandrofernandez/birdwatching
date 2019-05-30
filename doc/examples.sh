# get all birds (tuples)
curl -v localhost:8080/api/v1/birds | jq '.'

# get all natural reserves (tuples)
curl -v localhost:8080/api/v1/reserves | jq '.'
# get natural reserve
curl -v localhost:8080/api/v1/reserves/1 | jq '.'
# create natural reserve
curl -v -X POST -H 'Content-Type: application/json' -d '{"name": "Odiel Marses", "regionId": 1}' localhost:8080/api/v1/reserves | jq '.'
# update natural reserve
curl -v -X PUT -H 'Content-Type: application/json' -d '{"name": "Odiel Marshes", "regionId": 1}' localhost:8080/api/v1/reserves/4 | jq '.'
# delete natural reserve
curl -v -X DELETE localhost:8080/api/v1/reserves/4 | jq '.'

# get chances by month
curl -v localhost:8080/api/v1/chances?date=2019-06-01 | jq '.'

# get all regions
curl -v localhost:8080/api/v1/regions | jq '.'
