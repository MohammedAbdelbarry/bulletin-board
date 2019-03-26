#!/bin/bash 

cp target/client-rmi-1.0-SNAPSHOT.jar client.jar
cp target/server-rmi-1.0-SNAPSHOT.jar server.jar
java -jar dispatcher.jar
