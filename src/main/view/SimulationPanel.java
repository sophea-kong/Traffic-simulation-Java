import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel {
    private List<Road> roads = new ArrayList<>();
    private List<TrafficLight> trafficLights = new ArrayList<>();
    private List<Car> vehicles = new ArrayList<>();
    private List<Stopline> stoplines = new ArrayList<>();

    public SimulationPanel() {

        setBackground(new Color(150, 150, 150)); // Gray background
        setPreferredSize(new Dimension(1000, 800));

        //create road object
        Road road1 = new Road(100, 400, Orientation.HORIZONTAL, Approach.SOUTH, 600, 200, 2, 200);
        Road road2 = new Road(900, 400, Orientation.HORIZONTAL, Approach.NORTH, 600, 200, 2, -200);
        Road road3 = new Road(500, 750, Orientation.VERTICAL, Approach.EAST, 500, 200, 2, -200);
        Road road4 = new Road(500, 50, Orientation.VERTICAL, Approach.WEST, 500, 200, 2, 200);


        roads.add(road1);
        roads.add(road2);
        roads.add(road3);
        roads.add(road4);

        //create vehicle obeject 
        Car car1 = new Car(Orientation.HORIZONTAL, 500, 450, 6.0, 0, road1, Car_load.ONE_PERSON);
        Car car2 = new Car(Orientation.HORIZONTAL, 600, 350, 6.0, 0, road2, Car_load.FOUR_PERSON);

        // Vehicles car4 = new Vehicles(Orientation.VERTICAL, 400, 150, 40, 30, 6, road4);
        


        vehicles.add(car1);
        vehicles.add(car2);
        // vehicles.add(car4);
        //create traffic light object
        TrafficLight light1 = new TrafficLight(new Coordinate(300, 550),road1, LightState.GREEN, 5000, 2000, 5000);
        TrafficLight light2 = new TrafficLight(new Coordinate(650, 250),road2, LightState.RED, 5000, 2000, 5000);
        TrafficLight light4 = new TrafficLight(new Coordinate(350, 250),road4, LightState.RED, 5000, 2000, 5000);
        TrafficLight light3 = new TrafficLight(new Coordinate(650, 550),road3, LightState.RED, 5000, 2000, 5000);


        trafficLights.add(light1);
        trafficLights.add(light2);
        trafficLights.add(light4);
        trafficLights.add(light3);


        Stopline stopline1 = new Stopline(new Coordinate(280, 420), Orientation.HORIZONTAL, road1);
        Stopline stopline4 = new Stopline(new Coordinate(450, 250), Orientation.VERTICAL, road4);
        Stopline stopline2 = new Stopline(new Coordinate(650, 350), Orientation.VERTICAL, road2);
        stoplines.add(stopline1);
        stoplines.add(stopline2);

        stoplines.add(stopline4);
        // Start animation timer
        Timer timer = new Timer(30, e -> {
            updateSimulation();
            repaint();
        });
        timer.start();
    }

    private void updateSimulation() {
        // Update traffic lights
        for (TrafficLight light : trafficLights) {
            light.update(30);
        }
        // make vehicle stop at red light and continue at green light
        // Simple red/green behavior: stop vehicles when a red light is within a threshold, resume on green, and advance vehicles.
        int stopDistance = 100; // pixels

        for (Car v : vehicles) {
            boolean stop = false;

            for (TrafficLight light : trafficLights) {
                if (light.getState() == LightState.RED && v.obeyLight(trafficLights) == light) {
                    Coordinate sl = v.obeyLine(stoplines).getPosition();
                    double dx = (double)(sl.getX() - v.getX());
                    double dy = (double)(sl.getY() - v.getY());
                    double distance = Math.hypot(dx, dy);

                    if (distance <= stopDistance) {
                        stop = true;
                        break;
                    }
                }
            }

            if (stop) {
                // the car is not fully stopped yet, so set speed to 0
                v.setSpeed(0);
            } else {
                // use .move() to continue moving
                v.setSpeed(v.getPreviousSpeed());
                if (v.getSpeed() == 0) {
                    v.move(1000,800,v.getOrientation(),v.road.approach);
                }
            }

            // Move vehicle according to its approach and current speed
            v.move(1000,800,v.getOrientation(),v.road.approach);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw roads
        for (Road road : roads) {
            DrawRoad.drawRoad(g2d, road);
        }

        // Draw traffic lights
        for (TrafficLight light : trafficLights) {
            DrawTrafficLight.drawTrafficLight(g2d, light);
        }

        // Draw vehicles
        for (Vehicles v : vehicles) {
            DrawVehicle.drawVehicle(g2d, v);
        }
    }
}
