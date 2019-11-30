docker network create ml-cloud-network-east
docker volume create mlservicedatadir-east
docker-compose -f "$PWD//docker-compose-east-framework.yml" up msapigateway-east-server1
docker-compose -f "$PWD//docker-compose-east.yml" up mlserver-east
#docker network create ml-cloud-network-west
#docker volume create mlservicedatadir-west
#docker-compose -f "$PWD//docker-compose-west-framework.yml" up msapigateway-west-server1
#docker-compose -f "$PWD//docker-compose-west.yml" up mlserver-west
