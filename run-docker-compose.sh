  1 #!/bin/bash
  2 
  3 export COMPOSE_CONVERT_WINDOWS_PATHS=1
  4 docker network create "ml-cloud-network-east"
  5 docker volume create "userdata"
  6 docker-compose -f "$PWD//docker-compose-east-framework.yml" up -d msapigateway-east-server1
  7 docker-compose -f "$PWD//docker-compose-east.yml" up -d mlserver-east
  8 #docker network create ml-cloud-network-west
  9 #docker volume create mlservicedatadir-west
 10 #docker-compose -f "$PWD//docker-compose-west-framework.yml" up msapigateway-west-server1
 11 #docker-compose -f "$PWD//docker-compose-west.yml" up mlserver-west
