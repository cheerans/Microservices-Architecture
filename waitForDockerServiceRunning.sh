#!/bin/bash

Container_ID=$1
echo $Container_ID
while ! $(docker inspect -f {{.State.Health.Status}} $Container_ID) | grep "healthy" 
do
  echo "waiting for " + $Container_ID + " to start ....\r\n"
  sleep 1
done

echo $Container_ID "- is already running"
