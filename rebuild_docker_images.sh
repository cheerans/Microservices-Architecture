#!/bin/bash

docker build -f MLEurekaServer/docker/DockerFile.eureka -t santhoshcheeran/mleurekaserverrepo .
docker push santhoshcheeran/mleurekaserverrepo
docker build -f MLService/docker/DockerFile.mlservice -t santhoshcheeran/mlservicerepo .
docker push santhoshcheeran/mlservicerepo
docker build -f MSApiGateway/docker/DockerFile.msapigateway -t santhoshcheeran/msapigatewayrepo .
docker push santhoshcheeran/msapigatewayrepo
docker build -f ZipkinServer/docker/DockerFile.zipkinserver -t santhoshcheeran/zipkinserverrepo .
docker push santhoshcheeran/zipkinserverrepo
docker build -f AutoScaler/docker/DockerFile.autoscaler -t santhoshcheeran/autoscalerrepo .
docker push santhoshcheeran/autoscalerrepo
#docker build -f NginxConf/docker/DockerFile.nginx -t santhoshcheeran/nginxrepo .
#docker push santhoshcheeran/nginxrepo
