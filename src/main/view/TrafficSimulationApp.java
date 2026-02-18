import javax.swing.*;

public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // create road object
        Road road1 = new Road(100, 400, Orientation.HORIZONTAL, Approach.SOUTH, 600, 200, 2, 200);
        Road road2 = new Road(900, 400, Orientation.HORIZONTAL, Approach.NORTH, 600, 200, 2, -200);
        Road road3 = new Road(500, 750, Orientation.VERTICAL, Approach.EAST, 500, 200, 2, -200);
        Road road4 = new Road(500, 50, Orientation.VERTICAL, Approach.WEST, 500, 200, 2, 200);

        // create vehicle obeject
        Car car1 = Car.create_car(road1);
        Car car2 = Car.create_car(road2);
        Car car3 = Car.create_car(road3);
        Car car4 = Car.create_car(road4);

        TrafficLight light1 = new TrafficLight(new Coordinate(300, 550), road1, LightState.GREEN, 5000, 2000, 5000);
        TrafficLight light2 = new TrafficLight(new Coordinate(650, 250), road2, LightState.RED, 5000, 2000, 5000);
        TrafficLight light4 = new TrafficLight(new Coordinate(350, 250), road4, LightState.RED, 5000, 2000, 5000);
        TrafficLight light3 = new TrafficLight(new Coordinate(650, 550), road3, LightState.RED, 5000, 2000, 5000);

        Stopline stopline1 = new Stopline(new Coordinate(280, 420), road1);
        Stopline stopline2 = new Stopline(new Coordinate(650, 350), road2);
        Stopline stopline3 = new Stopline(new Coordinate(550, 550), road3);
        Stopline stopline4 = new Stopline(new Coordinate(450, 250), road4);

        
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
