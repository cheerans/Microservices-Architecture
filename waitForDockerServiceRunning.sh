#!/bin/bash

Container_ID=$1
while [ $(docker inspect -f {{.State.Health.Status}} $Container_ID) -ne "healthy" ]
do
  echo "waiting for " + $Container_ID + " to start ....\r\n"
  sleep 60
done

echo $Container_ID + "- is already running"
