import java.awt.Color;
import java.awt.Graphics2D;

public class DrawVehicle {
    public static void drawVehicle(Graphics2D g2d, Vehicles v) {
        g2d.setColor(Color.RED);
        g2d.fillRect((int)v.getX(), (int)v.getY(), v.getwidth(), v.getheight());
        
        // Draw windows
        g2d.setColor(Color.CYAN);
        g2d.fillRect((int)(v.getX() + 5), (int)(v.getY() + 5), 8, 8);
        g2d.fillRect((int)(v.getX() + v.getwidth() - 13), (int)(v.getY() + 5), 8, 8);
        
        // Draw wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(v.getX() + 5), (int)(v.getY() + v.getheight() - 8), 6, 6);
        g2d.fillOval((int)(v.getX() + v.getwidth() - 11), (int)(v.getY() + v.getheight() - 8), 6, 6);
    }
}
