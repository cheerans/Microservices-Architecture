#!/bin/bash

git stash
git pull
chmod 755 *.sh
dos2unix *.sh
alias git="./gitpull.sh"
alias k="./kill.sh"
