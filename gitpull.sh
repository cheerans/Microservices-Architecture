#!/bin/bash

git stash
git pull
chmod 755 *.sh
dos2unix *.sh
$(echo -n -e alias git="./gitpull.sh")
$(echo -n -e alias k="./kill.sh")
