import javax.swing.*;
import java.awt.*;



public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        SimulationPanel panel = new SimulationPanel(1000,800);
        panel.addnewRoad(800, 400, Approach.NORTH);
        panel.addnewRoad(100, 400, Approach.SOUTH);
        panel.addnewRoad(500, 100, Approach.WEST);
        panel.addnewRoad(500, 700, Approach.EAST);

        panel.addstopline(new Coordinate(280, 420), 1);
        panel.addstopline(new Coordinate(650, 350), 2);
        panel.addstopline(new Coordinate(500, 550), 3);
        panel.addstopline(new Coordinate(450, 250), 4);



        panel.addVehicle(1, Color.BLUE);
        panel.addVehicle(2, Color.PINK);
        panel.addVehicle(3, Color.MAGENTA);
        panel.addVehicle(4, Color.ORANGE);

        panel.addLight(300, 550, 1, LightState.GREEN);
        panel.addLight(650, 250, 2, LightState.RED);
        panel.addLight(350, 250, 4, LightState.RED);
        panel.addLight(650, 550, 3, LightState.RED);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSimulationApp());
    }
}
