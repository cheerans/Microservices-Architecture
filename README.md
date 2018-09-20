Microservices Architecture (Load balanced + High availability)
=============================================================

Microservice is a very abused term and Microservices are not supposed to be Micro in size. However they have the capability to be Micro, in case of a complete CICD requirement. Be warned this comes with a very high hardware price and automated testing overhead and fulfillment of complete Microservice independence requirement. Complete independence means, a database per Microservice. 

A better design is split Microservices per large homogeneous entity relationship. Eg: Order Entry System in one Microservice, Product Catalog and Inventory Management in another Microservice. Making Product Description iteself an independent microservice in Netflix model and provides ease of independent deployment and speed to market, but it involves large infrastructure. Each tiny Microservice  will have to run in it's own docker container.

![alt text](CICD.png)

# Conservative Microservices Architecture and achieving Dream performance

A conservative Microservices architecture allows us to scale our deployments properly and efficiently and structurally sound from aspects of failover and availability. That is all that Microservices focuses as a solution. CICD is a different animal than Microservices and do not belong in the species at all. Splitting Microservices properly allows us to scale the busier servcies to have more instances and scale up and down a group of services as per PRESENT TIME load and achieve cost effectiveness on hardware, as well as high availability and fault tolerance(failover). This is the real use of Microservices architecture. Microservices framework achieves fault tolerance using framework capabilities and server farms are integrated using provided mechanisms.

The components of this design are

[Spring Eureka Server](MLEurekaServer/README.md)

Please click the link above for details. This server manages our service registry and discovery. Our Microservices registers with Eureka servers. The consumer applciations and ZUUL proxy queries Eureka server for discovery.
  
[Spring Boot Microservice](MLService/README.md)
  
Please click the link above for details. This is the Microservice implementation project. For demonstartion sake, one Microservice here calls another microservice. However the call is made through service discovery. Hence it demonstrates load balancing concept clearly. If you run the application, the output traces port number and it will show you that the server is calling through load balancer.

[Spring Zipkin Server](ZipkinServer/README.md)
  
  Please click the link above for details. Zipkin server logs all the service calsl and maintains traceid for one set of service calls, that are part of same call. This could come handy debugging performance or other issues.
  
[Zuul Proxy](MSApiGateway/README.md)
  
  Please click the link above for details. The idea is to implement an API Gateway. Why Gateway, several Microservices can be tailored to one API gateway and load balancing can never go wrong when a service is consumed via a gateway, plus security can be implemented easily hiding microservices behind a DMZ zone.
  
[Hystrix Circuit Breaker](MLService/README.md)
  
  Please click the link above for details. This area shows how we can avoid cascading failures and avoid certain circuit paths by resolving to return failure.
  
  ## Accessing a port on Windows Docker
  
  The ports of a docker container is not automatically forwarded to the localhost:port. Hence we need to do the following instruction. The magic is done by `microsoft\iis` which spawns up IIS server on the windows machine and makes the docker container available. Here port 8080 on docker container is available. 
  
  `docker run -d -p 8080:8080 microsoft/iis`  
  
  However again the intricacy is that this port is only available on the docker container IP Address and not on local host. The following will obtain docker containers IP Address. 
  
  `docker inspect --format '{{ .NetworkSettings.Networks.nat.IPAddress }}' <container>`  
  
  Hence forth typing `dockerip:8080` in internet explorer provides the access to local docker container via browser.
  
   ## Accessing a port on Linux Docker
   
   On Linux the ports are forwarded to localhost or 127.0.0.1.
 

A small story to enjoy, till I fill more details

The Dogma of World Legal System
===============================

One day a man buys a dog and feeds it and makes it big. The dog was trained in several wonderful skills. The dog was fed good and his job was to catch the thief. One day a thief walks in and the dog bites the thief, and injures him not mortally. With the help of this injury police is able to catch the thief. The case goes to court and the prosecution argues that the dog should be hanged for biting the thief. Was a dog biting a man inhumane ? People said since the thief was a major person in the area, attacking the person is dogs crime and owning the dog, the mans crime. So the man get tried for hanging! But since the man had enough money to raise a dog and build a house worth protecting with a dog, he was able to hire a lawyer of great reputation. This lawyer with all his might argues the case to release the man and not charge him with man slaughter. However the dog was hanged and the thief was granded damages for medical treatment and defamation caused by the incident. 

What if a poor man buys a dog ? The poor man will be hanged! The dog will taken by the major person as damages. What will happen to the dog ? He will protect the major man's house. Now the poor man steals at major mans home and the dog bites him, the poor man is arrested and the dog gets share of big bones and is hailed as a hero! 

# Happy coding!

Good Luck and spend time and code slow. It is not the size of the docker whale, it is the motion in the ocean - Santhosh 

## Contribute

* Fork the project
* Create a feature branch based on the 'master' branch
* Create a PR and feel proud.


## License

MicroServicesArchitecture is released under the MIT license, see [LICENSE](https://github.com/BonifyByForteil/react-native-piwik/blob/master/LICENSE).

`# Microservices Architecture`
`# Microservices`
