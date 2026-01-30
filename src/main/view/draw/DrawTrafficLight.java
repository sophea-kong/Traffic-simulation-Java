import java.awt.Color;
import java.awt.Graphics2D;

public class DrawTrafficLight {
    public static void drawTrafficLight(Graphics2D g2d, TrafficLight light) {
        Color lightColor = switch (light.state) {
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
        };
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)light.position.x - 15, (int)light.position.y - 45, 40, 100);
        
        g2d.setColor(lightColor);
        g2d.fillOval((int)light.position.x - 5, (int)light.position.y - 35 + (light.state.ordinal() * 30), 20, 20);
    }
}
