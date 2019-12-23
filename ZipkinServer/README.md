
# Zipkin Server

`Uses Spring Zipkin Server`

Zipkin Server listens on localhost and can log events. Any event that involves a restfull microservice invocation, or Zull proxy ivoke is logged by Zipkin server.  Zipkin server also maintains the context of a call. This actually means the following

Let us discuss a scenario where several microservices are present. They run on three servers named as below

* Server1 hosts Microservice MS1
* Server2 hosts Microservice MS2
* Server3 hosts Microservice MS3

`Server1:MS1` -> `Server2:MS2` -> `Server3:MS3`

`Server1:MS1` invokes `Server2:MS2` which invokes `Server3:MS3`

Zipkin will log this as one Span or call, it will include child spans for individual servers running Microservice

`docker build -f docker/DockerFile.zipkinserver -t santhoshcheeran/zipkinserverrepo .`

Docker image can be built using above command. All you need is the dockerfile, the instructions in the file will do the rest. So get the dockerfile and prepare the image, if you need to.

Login to docker, before pushing image using the following command - `docker login`

`docker push santhoshcheeran/zipkinserverrepo`

For running on local box in Eclipse, with VM arguments, find all the -e arguments in the section below and do a replace as follows

`-e "EUREKA-PEER-SERVER-HOST=localhost" - Replace it as -DEUREKA-PEER-SERVER-HOST=localhost`

### East Server 1,2

The application can be run as follows

`docker run --name=zipkinserver-east-server1 -it --rm -p 9411:9411 -e "SERVER-PORT=9411" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8761" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8762" -P santhoshcheeran/zipkinserverrepo`

### West Server 1,2

The application can be run as follows

`docker run --name=zipkinserver-west-server1 -it --rm -p 9412:9412 -e "SERVER-PORT=9412" -e "SERVER-HOST=localhost" -e "REGION=West" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8763" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8764" -P santhoshcheeran/zipkinserverrepo`
