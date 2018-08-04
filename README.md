This is a work in progress, but most parts are stable

# Zipkin Server

Zipkin Server listens on localhost and can log events. Any event that involves a restfull microservice invocation, or Zull proxy ivoke is logged by Zipkin server.  Zipkin server also maintains the context of a call. This actually means the following

Let us discuss a scenario where several microservices are present. They run on three servers name as below

1. Server1 
2. Server2
3. Server3

Server1:MS1 -> Server2:MS2 -> Server3:MS3

Server1:MS1 invokes Server2:MS2 which invokes Server3:MS3

Zipkin will log this as one Span or call, it will include child spans for individual servers




# Eureka Server
1. Eureka Sever is fully functional
2. Eureka Server does failover 
3. Eureka Server takes Peer Info as environment variable



# Happy coding! 
"# Microservices Architecture"
"# Microservices"


Adios - Santhosh Cheeran
