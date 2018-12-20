# MSApiGateway

`Uses Zuul Proxy`

This project implements the concept of Zuul Gateway. All microservices are exposed here from one baseurl of the gateway. The methos is desireable when you want to expose one global URL. The load balancing is done by Zuul. Each call will round robin fashion hit a new server. The Gateway is configured to listen to the Eureka server.

docker build -f docker/DockerFile.mlservice -t santhoshcheeran/mlservicerepo .

Docker image can be built using above command. All you need is the dockerfile, the instructions in the file will do the rest. So get the dockerfile and prepare the image, if you need to.

Login to docker, before pushing image using the following command - docker login

docker push santhoshcheeran/mlservicerepo

### East Server

`docker run --name=mlserver-instance-east1 -it --rm -p 8091:8091 -e "SERVER-PORT=8080" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8761" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8762" -P santhoshcheeran/msapigateway`

### West Server

`docker run --name=mlserver-instance-east1 -it --rm -p 8091:8091 -e "SERVER-PORT=8080" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8763" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8764" -P santhoshcheeran/msapigateway`
