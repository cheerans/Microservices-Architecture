# Auto Scaler

`Uses Python`

* It scales services on the local docker machine. This app runs on the docker machine and monitors the docker cpu usage as a percentage and scales the application, up and down when threshold is crossed. It is easy to read the code from there on. Please click here for config file <a href="properties/ScaleServicesConfig.yml" target="_blank">properties/ScaleServicesConfig.yml</a>. The heart of the code is here <a href="com/autoscaler/service/AutoScaleStrategy.py" target="_blank">com/autoscaler/service/AutoScaleStrategy.py</a>.

Please type `docker-machine ls`

it typically yields the following

NAME      ACTIVE   DRIVER       STATE     URL                         SWARM   DOCKER        ERRORS

default   *        virtualbox   Running   tcp://192.168.99.100:2376           v18.06.0-ce

Now type `docker -H tcp://192.168.99.100:2376 ps`


curl --unix-socket /var/run/docker.sock http:/v1.24/containers/json
