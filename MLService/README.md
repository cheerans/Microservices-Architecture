
# MLService

`Uses Spring Boot, Loadbalanced Rest Template, Eureka Client, Hystrix Circuit Breaker`

This project holds the service layer. There is only one microservice here. However, the sample is done in such a way that invoking another microservice is done through Eureka Server and Load balnced Rest template. Hence each service here acts like a separate microservice, for demonstration pruposes. The sample will help you design fully scalable microservices, fully functional. 

Only change that has to be done in production is, same module microservices chain calls has to go through camel routes, or request forwarding and not allowed to hit the load balanced resttemplate. An efficient and deisgn approved way of implementing it is via a proxy, that would redirect calls to method calls or rest template deciding which services we host. I agree, need not complicate it that much, can harcode internal calls as plain java function calls and be done with it.

Clients of Eureka servers, hence have to configure with both servers for a region. If in case of failover, one of the Eureka servers must be available and service discovery will continue uninterrupted. This is called high availability model. Below is a client configuration for Microservices to connect to their Eureka server to discover,the microserver ip:port/service url.

`eureka: client: serviceUrl: defaultZone: http://${EUREKA-SERVER1-HOST}:${EUREKA-SERVER1-PORT}/eureka/,http://${EUREKA-SERVER2-HOST}:${EUREKA-SERVER2-PORT}/eureka/`

`docker build -f docker/DockerFile.mlservice -t santhoshcheeran/mlservicerepo .`

Docker image can be built using above command. All you need is the dockerfile, the instructions in the file will do the rest. So get the dockerfile and prepare the image, if you need to.

`docker login`
Login to docker, before pushing image

`docker push santhoshcheeran/mlservicerepo`

Servers are run with following VM arguments.

### East Server 1,2

The application can be run with following VM arguments 

`docker run --name=mlserver-instance-east1 -it --rm -p 8080:8080 -e "ZUULPROXYADDRESS=http://localhost:8091" -e "SERVER-PORT=8080" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8761" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8762" -P santhoshcheeran/mlservicerepo`

`docker run --name=mlserver-instance-east2 -it --rm -p 8083:8083 -e "ZUULPROXYADDRESS=http://localhost:8091" -e "SERVER-PORT=8083" -e "SERVER-HOST=localhost" -e "REGION=East" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8761" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8762" -P santhoshcheeran/mlservicerepo`

### West Server 1,2

The application can be run with following VM arguments 

`docker run --name=mlserver-instance-west1 -it --rm -p 8085:8085 -e "ZUULPROXYADDRESS=http://localhost:8092" -e "SERVER-PORT=8085" -e "SERVER-HOST=localhost" -e "REGION=West" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8763" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8764" -P santhoshcheeran/mlservicerepo`

`docker run --name=mlserver-instance-west2 -it --rm -p 8087:8087 -e "ZUULPROXYADDRESS=http://localhost:8092" -e "SERVER-PORT=8087" -e "SERVER-HOST=localhost" -e "REGION=West" -e "EUREKA-SERVER1-HOST=localhost" -e "EUREKA-SERVER1-PORT=8763" -e "EUREKA-SERVER2-HOST=localhost" -e "EUREKA-SERVER2-PORT=8764" -P santhoshcheeran/mlservicerepo`
