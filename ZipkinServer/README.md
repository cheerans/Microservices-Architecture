
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

### East Server

`-DSERVER-PORT=9411 -DREGION=East -DSERVER-HOST=localhost -DEUREKA-SERVER1-HOST=localhost -DEUREKA-SERVER1-PORT=8761 -DEUREKA-SERVER2-HOST=localhost -DEUREKA-SERVER2-PORT=8762`

### West Server

`-DSERVER-PORT=9412 -DREGION=West -DSERVER-HOST=localhost -DEUREKA-SERVER1-HOST=localhost -DEUREKA-SERVER1-PORT=8763 -DEUREKA-SERVER2-HOST=localhost -DEUREKA-SERVER2-PORT=8764`
