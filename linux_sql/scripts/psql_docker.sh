#!/bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Start docker
# Make sure you understand the double pipe operator
sudo systemctl status docker || sudo systemctl start docker

# Check container status (try the following cmds on terminal)
docker container inspect jrvs-psql
container_status=$?

# User switch case to handle create|stop|start opetions
case $cmd in
create)

  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1
  fi

  # Check # of CLI arguments
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

# Create container
docker volume pgta
# Start the container
docker run --name jrvs-psql -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

 # Check if the container was successfully created
  if [ $? -eq 0 ]; then
    echo 'Container created and started successfully'
  else
    echo 'Failed to create container'
    exit 1
  fi
  ;;

start|stop)
# Check instance status; exit 1 if container has not been created
  if [ $container_status -eq 0 ]; then
    # Container exists, proceed with start/stop
    if [ "$cmd" = "start" ]; then
      docker container start jrvs-psql # Start
    elif [ "$cmd" = "stop" ]; then
      docker container stop jrvs-psql # Stop
    fi
    exit $?
  else
    echo 'Container doesnt exist.'
    exit 1
  fi
  ;;

*)
echo 'Illegal command'
echo 'Commands: start|stop|create'
exit 1
;;
esac
