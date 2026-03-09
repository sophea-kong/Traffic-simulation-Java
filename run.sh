javac -d bin src/main/classes/*.java src/main/view/*.java && xcopy "src/main/images" "bin/images" /E /I /Y
 
java -cp bin TrafficSimulationApp

  