# MSApiGateway

`Uses Load Balanced Rest Controller`

This project implements the concept of a Microservice. All microservices are exposed here from one baseurl of the gateway. The method is desireable when you want to expose one global URL. The load balancing is done by Zuul. Each call will round robin fashion hit a new server. The Gateway is configured to listen to the Eureka server.

docker build -f docker/DockerFile.mlservice -t santhoshcheeran/mlservicerepo .

Docker image can be built using above command. All you need is the dockerfile, the instructions in the file will do the rest. So get the dockerfile and prepare the image, if you need to.

Login to docker, before pushing image using the following command - docker login

docker push santhoshcheeran/mlservicerepo

For running on local box in Eclipse, with VM arguments, find all the -e arguments in the section below and do a replace as follows

`-e "EUREKA-PEER-SERVER-HOST=localhost" - Replace it as -DEUREKA-PEER-SERVER-HOST=localhost`

### East Server

`docker run --name=mlservice-east -it --rm -p 8091:8091 -e "ZUULPROXYADDRESS=http://localhost:8091" -e "SERVER-PORT=8080" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8761" -e "EUREKA-SERVER2-HOST=localhost -DEUREKA-SERVER2-PORT=8762" -P santhoshcheeran/mlservicerepo`

### West Server

`docker run --name=mlservice-west -it --rm -p 8092:8092 -e "ZUULPROXYADDRESS=http://localhost:8092" -e "SERVER-PORT=8081" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8763" -e "EUREKA-SERVER2-HOST=localhost -DEUREKA-SERVER2-PORT=8764" -P santhoshcheeran/mlservicerepo`
