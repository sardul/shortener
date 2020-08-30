Run application using below cmds
1. ./gradlew build
2. ./gradlew bootRun

Short URL using below API:
curl --location --request POST 'localhost:8080/shortener' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url":"www.medium.com",
    "cid":"1"
}'

Hit count :
curl --location --request POST 'localhost:8080/hitcount' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url":"www.medium.com"
}'

