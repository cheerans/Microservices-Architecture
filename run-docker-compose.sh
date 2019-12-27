#!/bin/bash

export COMPOSE_CONVERT_WINDOWS_PATHS=1
docker network create "ml-cloud-network-east"
docker volume create "userdata"

docker-compose --compatibility -f "docker-compose-east-framework.yml" up -d eureka-east-server1
./waitForDockerServiceRunning.sh  microservices-architecture_eureka-east-server1_1

#docker-compose  --compatibility -f "docker-compose-east-framework.yml" up -d zipkinserver-east-server1
#./waitForDockerServiceRunning.sh microservices-architecture_zipkinserver-east-server1_1

docker-compose  --compatibility -f "docker-compose-east-framework.yml" up -d msapigateway-east-server1
./waitForDockerServiceRunning.sh microservices-architecture_msapigateway-east-server1_1

docker-compose  --compatibility -f "docker-compose-east.yml" up -d mlserver-east
./waitForDockerServiceRunning.sh mlserver_east

#docker network create ml-cloud-network-west
#docker volume create mlservicedatadir-west
#docker-compose  --compatibility -f "docker-compose-west-framework.yml" up msapigateway-west-server1
#docker-compose  --compatibility -f "docker-compose-west.yml" up mlserver-west
