#!/bin/bash

git stash
git pull
chmod 755 *.sh
dos2unix *.sh
cmd='echo -n -e alias git="./gitpull.sh'
$cmd
cmd='echo -n -e alias k="./kill.sh"'
$cmd
