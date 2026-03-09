javac -d bin src/main/classes/*.java src/main/view/*.java && cp -r "src/main/images" "bin/images" /E /I /Y
 
java -cp bin TrafficSimulationApp

  