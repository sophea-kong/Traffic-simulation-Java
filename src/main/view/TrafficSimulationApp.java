import javax.swing.*;

public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // create road object
        Road road1 = new Road(100, 400, Orientation.HORIZONTAL, Approach.SOUTH, 600, 200, 2, 200);
        Road road2 = new Road(900, 400, Orientation.HORIZONTAL, Approach.NORTH, 600, 200, 2, -200);
        Road road3 = new Road(500, 750, Orientation.VERTICAL, Approach.WEST, 500, 200, 2, -200);
        Road road4 = new Road(500, 50, Orientation.VERTICAL, Approach.EAST, 500, 200, 2, 200);

        // create vehicle obeject
        SimulationPanel panel = new SimulationPanel(1000, 800);

        // Road 1: South (Right)
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.SOUTH, 50, 450, 6.0, 6.0, road1, Car_load.ONE_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.SOUTH, -50, 450, 4.0, 4.0, road1, Car_load.FOUR_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.SOUTH, -150, 450, 5.5, 5.5, road1, Car_load.TWO_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.SOUTH, -300, 450, 7.0, 7.0, road1, Car_load.ONE_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.SOUTH, -450, 450, 4.5, 4.5, road1, Car_load.THREE_PERSON));
        panel.addVehicle(new Motorcycle(Orientation.HORIZONTAL, Approach.SOUTH, -200, 450, 7.0, 7.0, road1));
        panel.addVehicle(new Ambulance(Orientation.HORIZONTAL, Approach.SOUTH, -250, 450, 8.0, 8.0, road1));
        // Road 2: North (Left)
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.NORTH, 950, 350, 5.5, 5.5, road2, Car_load.THREE_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.NORTH, 1100, 350, 7.5, 7.5, road2, Car_load.ONE_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.NORTH, 1250, 350, 4.2, 4.2, road2, Car_load.FOUR_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.NORTH, 1400, 350, 6.8, 6.8, road2, Car_load.TWO_PERSON));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, Approach.NORTH, 1550, 350, 5.0, 5.0, road2, Car_load.ONE_PERSON));
        
        // Road 3: West (Up)
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.WEST, 550, 750, 5.0, 5.0, road3, Car_load.FOUR_PERSON));
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.WEST, 550, 950, 6.5, 6.5, road3, Car_load.TWO_PERSON));
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.WEST, 550, 1150, 4.8, 4.8, road3, Car_load.THREE_PERSON));
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.WEST, 550, 1350, 7.2, 7.2, road3, Car_load.ONE_PERSON));

        // Road 4: East (Down)
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.EAST, 450, 50, 4.5, 4.5, road4, Car_load.THREE_PERSON));
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.EAST, 450, -150, 8.0, 8.0, road4, Car_load.ONE_PERSON));
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.EAST, 450, -350, 5.2, 5.2, road4, Car_load.FOUR_PERSON));
        panel.addVehicle(new Car(Orientation.VERTICAL, Approach.EAST, 450, -550, 6.0, 6.0, road4, Car_load.TWO_PERSON));

        // Synchronized Timings
        TrafficLight light1 = new TrafficLight(new Coordinate(300, 550), road1, LightState.GREEN, 7000, 2000, 9000);
        TrafficLight light2 = new TrafficLight(new Coordinate(650, 250), road2, LightState.GREEN, 7000, 2000, 9000);
        TrafficLight light3 = new TrafficLight(new Coordinate(650, 550), road3, LightState.RED, 7000, 2000, 9000);
        TrafficLight light4 = new TrafficLight(new Coordinate(350, 250), road4, LightState.RED, 7000, 2000, 9000);
        Stopline stopline1 = new Stopline(new Coordinate(300, 550), road1);
        Stopline stopline2 = new Stopline(new Coordinate(650, 250), road2);
        Stopline stopline3 = new Stopline(new Coordinate(550, 550), road3);
        Stopline stopline4 = new Stopline(new Coordinate(450, 250), road4);

        panel.addRoad(road1);
        panel.addRoad(road2);
        panel.addRoad(road3);
        panel.addRoad(road4);

        panel.addTrafficLight(light1);
        panel.addTrafficLight(light2);
        panel.addTrafficLight(light3);
        panel.addTrafficLight(light4);

        panel.addstopline(stopline1);
        panel.addstopline(stopline2);
        panel.addstopline(stopline3);
        panel.addstopline(stopline4);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSimulationApp());
    }
}
