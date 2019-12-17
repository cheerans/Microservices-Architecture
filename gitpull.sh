#!/bin/bash

git stash
git pull
chmod 755 *.sh
dos2unix *.sh
$('echo alias git="./gitpull.sh"')
$('echo 'alias k="./kill.sh"')
