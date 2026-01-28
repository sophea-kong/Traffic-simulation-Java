import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel {
    private List<Road> roads = new ArrayList<>();
    private List<TrafficLight> trafficLights = new ArrayList<>();
    private List<Vehicles> vehicles = new ArrayList<>();

    public SimulationPanel() {
        setBackground(new Color(150, 150, 150)); // Gray background
        setPreferredSize(new Dimension(1000, 800));

        roads.add(new Road(100, 400, Orientation.HORIZONTAL, Approach.SOUTH, 600, 200, 2, 200));
        roads.add(new Road(900, 400, Orientation.HORIZONTAL, Approach.NORTH, 600, 200, 2, -200));

        roads.add(new Road(500, 750, Orientation.VERTICAL, Approach.EAST, 500, 200, 2, -200));
        roads.add(new Road(500, 750, Orientation.VERTICAL, Approach.EAST, 500, 200, 2, -200));

        vehicles.add(new Vehicles(Orientation.HORIZONTAL, 500, 320, 40, 30, 2, 0));
        vehicles.add(new Vehicles(Orientation.VERTICAL, 500, 150, 40, 30, 2, 0));

        trafficLights.add(new TrafficLight(300, 250, Approach.SOUTH, LightState.RED, 5000, 2000, 5000));

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
        // Update vehicle positions
        vehicles.get(0).update(getWidth(), getHeight(), Orientation.HORIZONTAL);
        vehicles.get(1).update(getWidth(), getHeight(), Orientation.VERTICAL);
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
