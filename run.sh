#!/bin/bash
mkdir -p bin
javac -d bin src/main/classes/*.java src/main/view/*.java
if [ -d "src/main/images" ]; then
    cp -R src/main/images bin/
fi
java -cp bin TrafficSimulationApp