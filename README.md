Engineering Difficulty and its Payoffs
=======================================

Someone who dropped out of high school thought that much technical knowledge was enough to sustain a high level in society. The fact is it isn't enough and anymuch is never enough is the fact, as you know. However it is impossible to establish a truth that a high school dropout cannot succeed in life. The industrial revolution marvel that happened 100 year ago has a micro or nano percenteage of such people and they where never responsible for the wave of industrial revolution, but just responsible for their own success in some industry. The idea they contributed was to success, which is what we are born with as a brain function, that causes depression otherwise. Hence psychology as argument  largely demerits the argument, technology can be achieved by human spirit alone and not time, nor education and neither lengthy reiterative research.

Technical degree is must to join a software development job, as it demonstrates morally conscient life discipline required by the job, in the field of intellectual endeavors alone and not include worldclass skill in mass organizing, a high school drop out usually has adequately. It is under the fair argument, the person dropped due to confidence in self.

A program made to make things smarter and faster, doesn't have to be faster or designed or thought off but only coded faster at the first try to get promoted at job is not an engineering argument. This scheme could be sustained by a ponzi scheme of doing next task faster, while previous things break and takes more time ot fix, or change is well understood. In long run, this becomes unsustaineable, spiralling the company down - as competitor can achieve products with rich features by clever engineering at FRACTIONAL COST.

We are here for studying modern ways to optimize things. eg: Taking years to study a simple process of writing by hand, but trying to automate it by printing - was the first machine. We used centuries of years to accomplish this feat, while 100,0000 of our population was jobless and had hands free to write. Engineering has a goal and effect, it is for that one person who cannot hire 100,000 people to print a book, but has the grand wisdom to write one worth read by 100,000. If you think free market is not possible without adeqaute engineering. Why ? Core Free market relies on that man printing that book about that idea.

High school failed resource teaching mathematics masters program in 3 sessions, by just slipping through all pages and stating I didn't have problem writing High school exams like this, is an accurate statement but is not statistically sufficient a statement !!!

The Dogma of A Complex System
===============================

I will try to explain microservices with a small story.

One day a man buys a rare breed expensive watch dog and feeds it and makes it big. The dog was trained in several wonderful skills, and his job was to catch the thief. One day a thief walks in and the dog chases the thief away, and injures him slightly in the pursuit from him falling down alone. With the help of this police is able to track the thief. The case goes to court and the prosecution argues that the dog should be hanged for chasing the thief. Was a dog chasing a man inhumane ? People said since the thief was a major person in the area, attacking the person is dogs crime and owning the dog, the mans crime. So the man get tried for aggravated assault! But since the man had enough money to raise a dog and build a house worth protecting with a dog, he was able to hire a lawyer of great reputation. This lawyer with all his might argues the case to release the man and not charge him with aggravated assault. However the dog was deemed dangerous and put to sleep and the thief was granted damages for medical treatment and defamation caused by the incident. 

What if a poor man buys a dog ? The poor man will be sent to jail! The dog will be handed over to the major man as damages. What will happen to the dog ? He will protect the major man's house. Now the poor man steals at major mans home and the dog chases him, the poor man is arrested and the dog gets share of big bones and is hailed as a hero! 

Poor man is Monolith architecture, Rich Major man is the Microservices architecture and the faithful dog is the Customer :) You can see that the only Happy scenario for a customer of a complex multi lot of users system to have a fast application, is to have microservices architecture, else the apllication will almost hang or have very slow response. I hope you like the concept. 

Microservices Architecture (Load balanced + High availability)
=============================================================

Microservice is a very abused term and Microservices are not supposed to be Micro in size. However they have the capability to be Micro, in case of a complete CICD requirement. Be warned this comes with a very high hardware price and automated testing overhead and fulfillment of complete Microservice independence requirement. Complete independence means, a database per Microservice. 

A better design is split Microservices per large homogeneous entity relationship. Eg: Order Entry System in one Microservice, Product Catalog and Inventory Management in another Microservice. Making Product Description iteself an independent microservice in Netflix model does provides ease of independent deployment and speed to market, but it involves large infrastructure. Each tiny Microservice  will have to run in it's own docker container.

Accessing the Microservice (Via ZUUL)
=====================================

When everything is running, hit the url as follows -

* Do the following command in docker (you got to understand docker is != localhost or 0.0.0.0)
  * `docker-machine ip` 
      * which usually yields 192.168.99.100. You will have to open this port on unix, to browse
  * In Browser - `http://192.168.99.100:8091/api-gateway/mls/getDecision1`
  * In Docker -`curl http://192.168.99.100:8091/api-gateway/mls/getDecision1`

![alt text](CICD.png)

# Conservative Microservices Architecture and achieving Dream performance

A conservative Microservices architecture allows us to scale our deployments properly and efficiently and structurally sound from aspects of failover and availability. That is all that Microservices focuses as a solution. CICD is a different animal than Microservices and do not belong in the species at all. Splitting Microservices properly allows us to scale the busier servcies to have more instances and scale up and down a group of services as per PRESENT TIME load and achieve cost effectiveness on hardware, as well as high availability and fault tolerance(failover). This is the real use of Microservices architecture. Microservices framework achieves fault tolerance using framework capabilities and server farms are integrated using provided mechanisms.

# East / West Servers - Config in real world

For the sake of demonstrations that would run on same machine, to simulate an East, West region, we use different port numbers for those servers. However in real life you can run on same port as in East on West as the IP Address is going ot be different. The only paramteter that needs to be input in that case is the host port of the main server.

The docker image has the default values for Eureka sever, Zipkin server, MSApiGateway server. If you configure your docker startup scripts to feed an IP and port to the main server, rest of the values can be hardcoded. What do I mean ? Run Eureka server always on port 8761,8762. Run ZipkinServer always on Port 9411. Run MSApiGateway always on port 8091. 

The Micro Service Server, SCALE UP OR DOWN up as many on port 8080, 8083,8085,8087,8089 ... etc

[Vertical/Horizontal Scaling](scaling.md)

Please click the link above for details. This document details our scaling strategies.

You can run the app quickly by doing the following

* git clone https://github.com/cheerans/Microservices-Architecture.git
* cd Microservices-Architecture
* chmod 755 run-docker-compose.sh
* ./run-docker-compose.sh

A cool place to run it would be, docker collaborated - https://labs.play-with-docker.com/

[Spring Eureka Server](MLEurekaServer/README.md)

Please click the link above for details. This server manages our service registry and discovery. Our Microservices registers with Eureka servers. The consumer applciations and ZUUL proxy queries Eureka server for discovery.
  
[Spring Boot Microservice](MLService/README.md)
  
Please click the link above for details. This is the Microservice implementation project. For demonstartion sake, one Microservice here calls another microservice. However the call is made through service discovery. Hence it demonstrates load balancing concept clearly. If you run the application, the output traces port number and it will show you that the server is calling through load balancer.

[Spring Zipkin Server](ZipkinServer/README.md)
  
  Please click the link above for details. Zipkin server logs all the service calsl and maintains traceid for one set of service calls, that are part of same call. This could come handy debugging performance or other issues.
  
[Zuul Proxy](MSApiGateway/README.md)
  
  Please click the link above for details. The idea is to implement an API Gateway. Why Gateway, several Microservices can be tailored to one API gateway and load balancing can never go wrong when a service is consumed via a gateway, plus security can be implemented easily hiding microservices behind a DMZ zone.
 
[Auto Scaler](AutoScaler/README.md)

 AutoScaler will scale the services running on the local docker. The details can be seen in this section
  
[Hystrix Circuit Breaker](MLService/README.md)
  
  Please click the link above for details. This area shows how we can avoid cascading failures and avoid certain circuit paths by resolving to return failure.
  
  ## Accessing metrics about a server
  
  Refer to this article - https://reflectoring.io/spring-boot-health-check/

    http://localhost:8761/eureka/apps/mlservice-east - The above shows the metrics about a server and all its instances
    
    http://localhost:8761/eureka/apps/mlservice-east/localhost:mlservice-east:8091 - The above shows the metrics about a server instance
    
    http://localhost:8091/actuator/health - The above shows the status of the server
    
    http://localhost:8091/actuator/prometheus -  shows CPU usage etc if you add followinf dependency
    
    <dependency>
		    <groupId>io.micrometer</groupId>
		    <artifactId>micrometer-registry-prometheus</artifactId>
		  </dependency>
    
    add the following entries in spring properties file
    
    management.endpoint.health.show-details=always
    management.endpoints.web.exposure.include=health,info,prometheus
  
  ## Accessing a port on Windows Docker
  
  The ports of a docker container is not automatically forwarded to the localhost:port. Hence we need to do the following instruction. The magic is done by `microsoft\iis` which spawns up IIS server on the windows machine and makes the docker container available. Here port 8080 on docker container is available. To build an image do the following
  
  `docker build -f Dockerfile.iissite -t santhoshcheeran/iissite .`
  
  `docker push santhoshcheeran/iissite`
  
  `docker run -d -p 8080:80 --name iissite_instance santhoshcheeran/iissite`  
  
  However again the intricacy is that this port is only available on the docker container IP Address and not on local host. The following will obtain docker containers IP Address. 
  
  `docker inspect -f {{ .NetworkSettings.IPAddress }} <container>`  
  
  Hence forth typing `dockerip:8080` in internet explorer provides the access to local docker container via browser.
  
   ## Accessing a port on Linux Docker
   
   On Linux the ports are forwarded to localhost or 127.0.0.1.

# Some useful GIT Bash commands

* git status
* git log - shows all the changes
* git reset - remove all changes
* git stash - stage and save the changes
* git reset
* git pull

# Some useful docker commands

* docker container ls
* docker container stop <hash> - Gracefully stop the specified container
* docker container kill <hash> - Force shutdown of the specified container
* docker container kill $(docker ps -q)
* docker container rm <hash> - Remove specified container from this machine
* docker container rm $(docker container ls -a -q) - Remove all containers
* docker image ls -a - List all images on this machine
* docker image rm <image id> - Remove specified image from this machine
* docker image rm $(docker image ls -a -q) - Remove all images from this machine
* docker login
* docker ps --filter "name=mlserver-east"

* docker exec -it mycontainer sh 
        -i means interactively 
        -t using tty opens a shell named mycontainer(just a dummy name
        
* docker run -P -d --name employee --link mycontainer microservicedemo/employee 
        --link links to an existing container
        -d means run as a daemon in background
        -P tells Docker to expose any container-declared port in the ephemeral range
        
* docker run -P -d –name mycontainer mongo
        Runs mongodb in mycontainer. There are ‘official’ Docker images of many popular software packages. 
        MongoDB’s is simply ‘mongo’ – you know it’s an official image by the lack of an owner name (e.g. owner/image)

* docker ps - shows process
* docker port - all docker ports
* docker inspect - all config dump
* docker logs -f "container name" - shows logs of the container
* docker stats [container name/ID] - 

* docker exec container_name curl http://localhost:8761 - does curl inside a container

# Happy coding!

Good Luck and spend time and code slow, but smart.

## Contribute

* Fork the project
* Create a feature branch based on the 'master' branch
* Create a PR and feel proud.

## License

MicroServicesArchitecture is released under the MIT license, see [LICENSE](https://github.com/cheerans/Microservices-Architecture/blob/master/LICENCE).

`# Microservices Architecture`
`# Microservices`
