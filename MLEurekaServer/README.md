# Eureka Server

`Uses Spring Eureka Server`

* Eureka Sever holds Microservice Registry
* Eureka Server is configured with a peer server for failover 


```
spring:
    application:
        name: mleurekaserver
server:
    port: ${EUREKA-SERVER-PORT}
    contextPath: /  
eureka:
    instance:
        hostname: ${EUREKA-SERVER-HOST}
    client:
        registerWithEureka: false
        fetchRegistry: false 
        region: ${REGION} 
        availabilityZones: 
            ${REGION}: http://${EUREKA-SERVER-HOST}:${EUREKA-SERVER-PORT}/eureka/,http://${EUREKA-PEER-SERVER-HOST}:${EUREKA-PEER-SERVER-PORT}/eureka/ 
        service-url:
            defaultZone: http://${EUREKA-PEER-SERVER-HOST}:${EUREKA-PEER-SERVER-PORT}/eureka/
```
Eureka Server is configured with a peer and has a peer server - per region. For Demo purposes we call the region east. Docker image can be built as follows

`docker build -t santhoshcheeran/mlrepo .`

`docker push santhoshcheeran/mlrepo`


Servers are run with following VM arguments.

### Eureka East Server 1,2

Run as follows

`docker run --name=eurekaserver-instance-east1 -it --rm -p 8761:8761 -e "REGION=East"  -e "EUREKA-SERVER-HOST=localhost" -e "EUREKA-SERVER-PORT=8761" -e "EUREKA-PEER-SERVER-HOST=localhost" -e "EUREKA-PEER-SERVER-PORT=8762" -P santhoshcheeran/mlrepo`

`docker run --name=eurekaserver-instance-east2 -it --rm -p 8762:8762 -e "REGION=East"  -e "EUREKA-SERVER-HOST=localhost" -e "EUREKA-SERVER-PORT=8762" -e "EUREKA-PEER-SERVER-HOST=localhost" -e "EUREKA-PEER-SERVER-PORT=8761" -P santhoshcheeran/mlrepo`

### Eureka West Server 3,4

Run as follows

`docker run --name=eurekaserver-instance-west1 -it --rm -p 8763:8763 -e "REGION=West"  -e "EUREKA-SERVER-HOST=localhost" -e "EUREKA-SERVER-PORT=8763" -e "EUREKA-PEER-SERVER-HOST=localhost" -e "EUREKA-PEER-SERVER-PORT=8764" -P santhoshcheeran/mlrepo`

`docker run --name=eurekaserver-instance-west2 -it --rm -p 8764:8764 -e "REGION=West"  -e "EUREKA-SERVER-HOST=localhost" -e "EUREKA-SERVER-PORT=8764" -e "EUREKA-PEER-SERVER-HOST=localhost" -e "EUREKA-PEER-SERVER-PORT=8763" -P santhoshcheeran/mlrepo`
