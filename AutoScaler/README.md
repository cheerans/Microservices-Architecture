# Auto Scaler

`Uses Python`

* It scales services on the local docker machine

Please type `docker-machine ls`

it typically yields the following

NAME      ACTIVE   DRIVER       STATE     URL                         SWARM   DOCKER        ERRORS

default   *        virtualbox   Running   tcp://192.168.99.100:2376           v18.06.0-ce

Now type `docker -H tcp://192.168.99.100:2376 ps`
