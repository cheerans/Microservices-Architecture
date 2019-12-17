#!/bin/bash

shopt -s expand_aliases
git stash
git pull
chmod 755 *.sh
dos2unix *.sh
shopt -s expand_aliases
eval $(alias git="source ./gitpull.sh")
alias git="source ./gitpull.sh"
alias k="./kill.sh"
