#!/bin/bash

git stash
git pull
chmod 755 *.sh
dos2unix *.sh
alias git="./gitpull.sh"
alias k="./kill.sh"
cmd="echo -n -e "\x00\x00\x0A\x00        1      012601234${x}12345\x00" | nc -w1 -u localhost 30000"
echo $cmd
$cmd
