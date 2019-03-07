#!/bin/bash

searchFor1=$1
searchFor2=$2

if [ "$searchFor1" = "" ]
then
	echo "*** NO Params Provided *** Example Usage of command: ./kilProcess.sh MLService java 
	exit 1
fi

KILLED_PROCESS=$(ps -ef | grep $searchFor1 | grep $searchFor2 | awk '{ print $2 }')

echo "killing " $KILLED_PROCESS

kill $KILLED_PROCESS
echo -n "Wait till the process is done ..."
while ps -p "$KILLED_PROCESS" > /dev/null 2>&1; do
	sleep 1
	WAIT_LOOP=$((WAIT_LOOP+1))
	if [ "$WAIT_LOOP" = "60" ]; then
		break

	fi
	echo -n "."
done
echo ""
echo "Killed the process"
