#!/bin/bash
mkdir -p bin
# Find all .java files in src/main and compile them
javac -d bin $(find src/main -name "*.java")

if [ -d "src/main/images" ]; then
    cp -R src/main/images bin/
fi
java -cp bin view.TrafficSimulationApp
