# MSApiGateway

`Uses Zuul Proxy`

This project implements the concept of Zuul Gateway. All microservices are exposed here from one baseurl of the gateway. The methos is desireable when you want to expose one global URL. The load balancing is done by Zuul. Each call will round robin fashion hit a new server. The Gateway is configured to listen to the Eureka server.

### East Server

`-DREGION=East -DSERVER-PORT=8091 -DSERVER-HOST=localhost -DEUREKA-SERVER1-HOST=localhost -DEUREKA-SERVER1-PORT=8761 -DEUREKA-SERVER2-HOST=localhost -DEUREKA-SERVER2-PORT=8762`

### West Server

`-DREGION=West -DSERVER-PORT=8092 -DSERVER-HOST=localhost -DEUREKA-SERVER1-HOST=localhost -DEUREKA-SERVER1-PORT=8763 -DEUREKA-SERVER2-HOST=localhost -DEUREKA-SERVER2-PORT=8764`
