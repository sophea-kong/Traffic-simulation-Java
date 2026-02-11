import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel {
    private List<Road> roads = new ArrayList<>();
    private List<TrafficLight> trafficLights = new ArrayList<>();
    private List<Car> vehicles = new ArrayList<>();
    private List<Stopline> stoplines = new ArrayList<>();

    public SimulationPanel(int windowWidth, int windowHeight) {

        setBackground(new Color(150, 150, 150)); // Gray background
        setPreferredSize(new Dimension(windowWidth, windowHeight));

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
        int stopDistance = 50; // pixels

        for (Car v : vehicles) {
            boolean stop = false;

            TrafficLight matchedLight = v.obeyLight(trafficLights);
            Stopline matchedLine = v.obeyLine(stoplines);

            if (matchedLight != null && matchedLine != null && matchedLight.getState() == LightState.RED) {
                Coordinate sl = matchedLine.getPosition();
                double dx = (double) (sl.getX() - v.getX());
                double dy = (double) (sl.getY() - v.getY());
                double distance = Math.hypot(dx, dy);

                if (distance <= stopDistance) {
                    stop = true;
                }
            }

            if (stop) {
                // the car is not fully stopped yet, so set speed to 0
                v.setSpeed(0);
            } else {
                // restore previous speed
                v.setSpeed(v.getPreviousSpeed());
            }

            v.move(1000, 800, v.getOrientation(), v.getRoad().getApproach());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw roads
        for (Road road : roads) {
            Road.drawRoad(g2d, road);
        }

        // Draw traffic lights
        for (TrafficLight light : trafficLights) {
            TrafficLight.drawTrafficLight(g2d, light);
        }

        // Draw vehicles
        for (Vehicles v : vehicles) {
            Vehicles.drawVehicle(g2d, v);
        }
    }

    void addRoad(Road road) {
        roads.add(road);
    }

    void addTrafficLight(TrafficLight light) {
        trafficLights.add(light);
    }

    void addVehicle(Car car) {
        vehicles.add(car);
    }

    void addstopline(Stopline line) {
        stoplines.add(line);
    }
}
// sophea