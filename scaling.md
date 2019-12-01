Framework Components
=====================

The framework consists of the backbone eureka server, zipkinserver,apigateway. You do not scale these components. Only the actual Microservice is scaled. Hence there will be a base framework dcoker compose and then the scaling components yaml.

In design you setup many horizontal scale, say 10 machines 5 per region. However the microservice instances on one machine can go up or down. 

The framework will run on 2 machines in prod main on one machine. Peer on another. Hence Prod framework will be split in to two files, 
master and peer. One Erureka master will run on master machine and peer eureka server on peer machine, same with api gateway. Rest of the machines of the region will not run the framework. They will just refer to the IP addresses from the microservice.
 
docker-compose can be used to scale the application vertically. Vertical Scaling means, scale up / down instances on a given machine.

You can run the app quickly by doing the following

* git clone https://github.com/cheerans/Microservices-Architecture.git
* cd Microservices-Architecture
* chmod 755 run-docker-compose.sh
* ./run-docker-compose.sh

A cool place to run it would be, docker collaborated - https://labs.play-with-docker.com/

The following will scale an service
docker-compose scale mlserver-east=5
