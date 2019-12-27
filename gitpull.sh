#!/bin/bash

shopt -s expand_aliases
git stash
git pull
chmod 755 *.sh
dos2unix *.sh
alias run="./run-docker-compose.sh"
alias git="./gitpull.sh"
alias k="./kill.sh"
alias rmi='docker rmi -f $(docker images -q)'
alias rmc='docker rm -f $(docker ps -a -q)'
