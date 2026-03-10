import javax.swing.*;

public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // create road object
        Road road1 = new Road(Orientation.HORIZONTAL, Approach.SOUTH, 600, 200, 2, 200);
        Road road2 = new Road(Orientation.HORIZONTAL, Approach.NORTH, 600, 200, 2, -200);
        Road road3 = new Road(Orientation.VERTICAL, Approach.WEST, 500, 200, 2, -200);
        Road road4 = new Road(Orientation.VERTICAL, Approach.EAST, 500, 200, 2, 200);

        // create vehicle obeject
        SimulationPanel panel = new SimulationPanel(1000, 800);

        // Road 1: South (Right)
        // panel.addVehicle(new Car(Orientation.HORIZONTAL, 6.0, 6.0, road1, Car_load.ONE_PERSON), new Coordinate(50, 450));
        // panel.addVehicle(new Car(Orientation.HORIZONTAL, 4.0, 4.0, road1, Car_load.FOUR_PERSON), new Coordinate(-50, 450));
        // panel.addVehicle(new Car(Orientation.HORIZONTAL, 5.5, 5.5, road1, Car_load.TWO_PERSON), new Coordinate(-150, 450));
        // panel.addVehicle(new Car(Orientation.HORIZONTAL, 7.0, 7.0, road1, Car_load.ONE_PERSON), new Coordinate(-300, 450));
        // panel.addVehicle(new Car(Orientation.HORIZONTAL, 4.5, 4.5, road1, Car_load.THREE_PERSON), new Coordinate(-450, 450));
        
        // Road 2: North (Left)
        panel.addVehicle(new Car(Orientation.HORIZONTAL, 5.5, 5.5, road2, Car_load.THREE_PERSON), new Coordinate(950, 350));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, 7.5, 7.5, road2, Car_load.ONE_PERSON), new Coordinate(1100, 350));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, 4.2, 4.2, road2, Car_load.FOUR_PERSON), new Coordinate(1250, 350));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, 6.8, 6.8, road2, Car_load.TWO_PERSON), new Coordinate(1400, 350));
        panel.addVehicle(new Car(Orientation.HORIZONTAL, 5.0, 5.0, road2, Car_load.ONE_PERSON), new Coordinate(1550, 350));

        // Road 3: West (Up)
        panel.addVehicle(new Car(Orientation.VERTICAL, 5.0, 5.0, road3, Car_load.FOUR_PERSON), new Coordinate(550, 750));
        panel.addVehicle(new Car(Orientation.VERTICAL, 6.5, 6.5, road3, Car_load.TWO_PERSON), new Coordinate(550, 950));
        panel.addVehicle(new Car(Orientation.VERTICAL, 4.8, 4.8, road3, Car_load.THREE_PERSON), new Coordinate(550, 1150));
        panel.addVehicle(new Car(Orientation.VERTICAL, 7.2, 7.2, road3, Car_load.ONE_PERSON), new Coordinate(550, 1350));

        // Road 4: East (Down)
        panel.addVehicle(new Car(Orientation.VERTICAL, 4.5, 4.5, road4, Car_load.THREE_PERSON), new Coordinate(450, 50));
        panel.addVehicle(new Car(Orientation.VERTICAL, 8.0, 8.0, road4, Car_load.ONE_PERSON), new Coordinate(450, -150));
        panel.addVehicle(new Car(Orientation.VERTICAL, 5.2, 5.2, road4, Car_load.FOUR_PERSON), new Coordinate(450, -350));
        panel.addVehicle(new Car(Orientation.VERTICAL, 6.0, 6.0, road4, Car_load.TWO_PERSON), new Coordinate(450, -550));

        // road 1 add ambulance
        //Orientation orientation, Approach approach, int x, int y, double speed, double curspeed, Road road) {
        panel.addVehicle(new Ambulance(Orientation.HORIZONTAL, Approach.SOUTH, -600, 450, 8.0, 8.0, road1), new Coordinate(-600, 450));

        // Add motorcycle to road 2
        panel.addVehicle(new Motorcycle(Orientation.HORIZONTAL, Approach.NORTH, 1650, 350, 7.0, 7.0, road2), new Coordinate(1650, 350));

        // Synchronized Timings
        TrafficLight light1 = new TrafficLight(road1, LightState.GREEN, 7000, 2000, 9000);
        TrafficLight light2 = new TrafficLight(road2, LightState.GREEN, 7000, 2000, 9000);
        TrafficLight light3 = new TrafficLight(road3, LightState.RED, 7000, 2000, 9000);
        TrafficLight light4 = new TrafficLight(road4, LightState.RED, 7000, 2000, 9000);

        Stopline stopline1 = new Stopline(road1);
        Stopline stopline2 = new Stopline(road2);
        Stopline stopline3 = new Stopline(road3);
        Stopline stopline4 = new Stopline(road4);

        panel.addRoad(road1, new Coordinate(100, 400));
        panel.addRoad(road2, new Coordinate(900, 400));
        panel.addRoad(road3, new Coordinate(500, 750));
        panel.addRoad(road4, new Coordinate(500, 50));

        panel.addTrafficLight(light1, new Coordinate(300, 550));
        panel.addTrafficLight(light2, new Coordinate(650, 250));
        panel.addTrafficLight(light3, new Coordinate(650, 550));
        panel.addTrafficLight(light4, new Coordinate(350, 250));

        panel.addstopline(stopline1, new Coordinate(280, 420));
        panel.addstopline(stopline2, new Coordinate(650, 350));
        panel.addstopline(stopline3, new Coordinate(550, 550));
        panel.addstopline(stopline4, new Coordinate(450, 250));

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSimulationApp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrafficSimulationApp that = (TrafficSimulationApp) o;
        return java.util.Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getTitle());
    }

    @Override
    public String toString() {
        return "TrafficSimulationApp{" +
                "title='" + getTitle() + '\'' +
                '}';
    }
}
