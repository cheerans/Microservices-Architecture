#!/bin/bash

Container_ID=$1
echo $Container_ID
status=$(docker inspect -f {{.State.Health.Status}} $Container_ID)
echo $status
while [[ $status == "healthy" ]]
do
  echo "waiting for "$Container_ID" to start ....\r\n"
  echo $status
  status=$(docker inspect -f {{.State.Health.Status}} $Container_ID)
  sleep 1
done

echo $Container_ID "- is already running"
