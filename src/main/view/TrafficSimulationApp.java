import javax.swing.*;

public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // create road object
        Road road1 = new Road(200, 400, Orientation.HORIZONTAL, Approach.EAST, 400, 200, 2, 180);
        Road road2 = new Road(800, 400, Orientation.HORIZONTAL, Approach.WEST, 400, 200, 2, -180);
        Road road3 = new Road(500, 650, Orientation.VERTICAL, Approach.NORTH, 300, 200, 2, -130);
        Road road4 = new Road(500, 150, Orientation.VERTICAL, Approach.SOUTH, 300, 200, 2, 130);

        // create vehicle obeject
        Car car1 = new Car(Orientation.HORIZONTAL, 0, 450, 6.0, 0, road1, Car_load.ONE_PERSON, Approach.NORTH);
        Car car2 = new Car(Orientation.HORIZONTAL, 1000, 350, 6.0, 0, road2, Car_load.FOUR_PERSON, Approach.WEST);
        Car car3 = new Car(Orientation.VERTICAL, 500, 750, 6.0, 0, road3, Car_load.THREE_PERSON, Approach.WEST);
        Car car4 = new Car(Orientation.VERTICAL, 450, 50, 6.0, 0, road4, Car_load.TWO_PERSON, Approach.SOUTH);

        TrafficLight light1 = new TrafficLight(new Coordinate(380, 550), road1, LightState.GREEN, 5000, 2000, 5000);
        TrafficLight light2 = new TrafficLight(new Coordinate(620, 250), road2, LightState.RED, 5000, 2000, 5000);
        TrafficLight light3 = new TrafficLight(new Coordinate(620, 520), road3, LightState.RED, 5000, 2000, 5000);
        TrafficLight light4 = new TrafficLight(new Coordinate(380, 280), road4, LightState.RED, 5000, 2000, 5000);

        Stopline stopline1 = new Stopline(new Coordinate(400, 450), Orientation.VERTICAL, road1);
        Stopline stopline2 = new Stopline(new Coordinate(600, 350), Orientation.VERTICAL, road2);
        Stopline stopline3 = new Stopline(new Coordinate(550, 500), Orientation.HORIZONTAL, road3);
        Stopline stopline4 = new Stopline(new Coordinate(450, 300), Orientation.HORIZONTAL, road4);

        SimulationPanel panel = new SimulationPanel(1000,800);

        panel.addRoad(road1);
        panel.addRoad(road2);
        panel.addRoad(road3);
        panel.addRoad(road4);

        panel.addVehicle(car1);
        panel.addVehicle(car2);
        panel.addVehicle(car3);
        panel.addVehicle(car4);

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
