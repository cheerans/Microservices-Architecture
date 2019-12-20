#!/bin/bash

Container_ID=$1
echo $Container_ID
status=
echo $status
while status=$(docker inspect -f {{.State.Health.Status}} $Container_ID);
do
  if [ "$status" = "healthy" ]; then
    break
  else
    echo "waiting for "$Container_ID" to start ...."
    sleep 1
  fi
done
echo $Container_ID "- is already running"
