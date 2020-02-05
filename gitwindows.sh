./kill.sh
docker rmi -f $(docker images -q)
docker rm -f $(docker ps -a -q)
alias git="git"
git stash
git pull
rebuild_docker_images.sh
