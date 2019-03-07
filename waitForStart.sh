#!/bin/bash

echo -n "Wait till tomcat is fully started ..."
sleep 2
while ! tail -15 /usr/bin/apache-tomcat-8.0.18/logs/catalina.out | grep "org.apache.catalina.startup.Catalina.start Server startup in" > /dev/null 2>&1; do
	sleep 1
	WAIT_LOOP=$((WAIT_LOOP+1))
	if [ "$WAIT_LOOP" = "120" ]; then
		break

	fi
	echo -n "."
done
