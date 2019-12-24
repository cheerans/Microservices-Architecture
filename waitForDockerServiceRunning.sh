#!/bin/bash

Container_ID=$1
echo $Container_ID
status=
echo $status
while status=$(docker inspect  -f {{.State.Health.Status}} --filter name=^/$Container_ID);
do--filter name=^/
  if [ "$status" = "healthy" ]; then
    break
  else
    echo "waiting for "$Container_ID" to start ...."
    echo $(docker container ls)
    sleep 20
  fi
done
echo $Container_ID "- is already running"
