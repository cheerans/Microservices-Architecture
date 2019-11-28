The framework consists of the backbone eureka server, zipkinserver,apigateway. You do not scale these components. Only the actual Microservice is scaled. Hence there will be a base framework dcoker compose and then the scaling components yaml.


docker-compose can be used to scale the application vertically. Vertical Scaling means, many instances on a given machine.


docker-compose scale MLService=5
