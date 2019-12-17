#!/bin/bash

git stash
git pull
chmod 755 *.sh
dos2unix *.sh
sh alias git="./gitpull.sh"
sh alias k="./kill.sh"
