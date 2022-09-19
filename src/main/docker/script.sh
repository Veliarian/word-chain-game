#! /bin/bash

docker-compose down
docker rmi words-game:latest

cd ../../../.
./mvnw clean package -DskipTests
cp target/words-game-0.0.1-SNAPSHOT.jar src/main/docker
cd src/main/docker
docker-compose up