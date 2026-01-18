import java.awt.Color;
import java.awt.Graphics2D;

public class DrawVehicle {
    public static void drawVehicle(Graphics2D g2d, Vehicles v) {
        g2d.setColor(Color.RED);
        g2d.fillRect(v.x, v.y, v.width, v.height);
        
        // Draw windows
        g2d.setColor(Color.CYAN);
        g2d.fillRect(v.x + 5, v.y + 5, 8, 8);
        g2d.fillRect(v.x + v.width - 13, v.y + 5, 8, 8);
        
        // Draw wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval(v.x + 5, v.y + v.height - 8, 6, 6);
        g2d.fillOval(v.x + v.width - 11, v.y + v.height - 8, 6, 6);
    }
}
