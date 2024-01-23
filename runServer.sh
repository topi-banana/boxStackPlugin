#!/bin/bash

cp ./build/libs/* ./run/plugins/
cd ./run
java -Xms2G -Xmx2G -jar paper-1.20.1-196.jar --nogui