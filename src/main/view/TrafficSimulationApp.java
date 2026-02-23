import javax.swing.*;

public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // create road object
        // Road road1 = new Road(100, 400, Orientation.HORIZONTAL, Approach.SOUTH, 600, 200, 2, 200);
        // Road road2 = new Road(900, 400, Orientation.HORIZONTAL, Approach.NORTH, 600, 200, 2, -200);
        // Road road3 = new Road(500, 750, Orientation.VERTICAL, Approach.EAST, 500, 200, 2, -200);
        // Road road4 = new Road(500, 50, Orientation.VERTICAL, Approach.WEST, 500, 200, 2, 200);

        // // create vehicle obeject
        // Car car1 = Car.create_car(1);
        // Car car2 = Car.create_car(2);
        // Car car3 = Car.create_car(3);
        // Car car4 = Car.create_car(4);

        // TrafficLight light1 = new TrafficLight(1);
        // TrafficLight light2 = new TrafficLight(2);
        // TrafficLight light4 = new TrafficLight(3);
        // TrafficLight light3 = new TrafficLight(4);



        
        SimulationPanel panel = new SimulationPanel(1000,800);
        panel.addnewRoad(800, 400, Approach.NORTH);
        panel.addnewRoad(100, 400, Approach.SOUTH);
        panel.addnewRoad(500, 100, Approach.WEST);
        panel.addnewRoad(500, 700, Approach.EAST);

        panel.addstopline(new Coordinate(280, 420), 1);
        panel.addstopline(new Coordinate(650, 350), 2);
        panel.addstopline(new Coordinate(550, 550), 3);
        panel.addstopline(new Coordinate(450, 250), 4);



        panel.addVehicle(1);
        panel.addVehicle(2);
        panel.addVehicle(3);
        panel.addVehicle(4);

        panel.addLight(300, 550, 1, LightState.GREEN, 5000, 2000, 5000);
        panel.addLight(650, 250, 2, LightState.RED, 5000, 2000, 5000);
        panel.addLight(350, 250, 3, LightState.RED, 5000, 2000, 5000);
        panel.addLight(650, 550, 4, LightState.RED, 5000, 2000, 500);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSimulationApp());
    }
}
