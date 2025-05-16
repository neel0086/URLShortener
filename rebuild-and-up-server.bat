@echo off
echo Shutting down existing containers and volumes...
docker-compose down -v --remove-orphans

echo.
echo Rebuilding Docker images...
docker-compose build kafka zookeeper redis

echo.
echo Starting containers...
docker-compose up kafka zookeeper redis
