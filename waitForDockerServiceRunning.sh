#!/bin/bash

Container_ID=$1
echo $Container_ID
status=$(docker inspect -f {{.State.Health.Status}} $Container_ID)
while [ $status == "healthy" ]
do
  echo "waiting for "$Container_ID" to start ....\r\n"
  status=$(docker inspect -f {{.State.Health.Status}} $Container_ID)
  sleep 1
done

echo $Container_ID "- is already running"
