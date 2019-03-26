#!/bin/bash

cp target/client-1.0-SNAPSHOT.jar client.jar
cp target/server-1.0-SNAPSHOT.jar server.jar
java -jar dispatcher.jar
