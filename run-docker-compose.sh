docker-compose -f $PWD//docker-compose-east-framework.yml up
docker-compose exec msapigateway-east-server1
docker-compose -f $PWD//docker-compose-east.yml up
docker-compose exec mlserver-east
#docker-compose -f $PWD//docker-compose-west-framework.yml up msapigateway-west-server1
#docker-compose -f $PWD//docker-compose-west.yml up mlserver-west
