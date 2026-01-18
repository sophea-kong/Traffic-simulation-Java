import javax.swing.*;

public class TrafficSimulationApp extends JFrame {
    public TrafficSimulationApp() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        SimulationPanel panel = new SimulationPanel();
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSimulationApp());
    }
}
