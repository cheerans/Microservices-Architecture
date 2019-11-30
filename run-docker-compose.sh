docker volume create --name=mlservicedatadir
docker-compose -f $PWD//docker-compose-east-framework.yml up --force-recreate app
docker-compose -f $PWD//docker-compose-east.yml up --force-recreate app
#docker-compose -f $PWD//docker-compose-west-framework.yml up  --force-recreate app
#docker-compose -f $PWD//docker-compose-west.yml up --force-recreate app
