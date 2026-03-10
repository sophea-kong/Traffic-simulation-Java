import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ambulance extends Vehicle {
    private long sirenTimer = 0;

    public Ambulance(Orientation orientation, Approach approach, int x, int y, double speed, double curspeed, Road road) {
        super(orientation, approach, x, y, 60, 70, speed * 1.5, curspeed, road); 
        loadSprite();
    }

    @Override
    public boolean isEmergency() { return true; }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResource("/images/emergency/ambulance.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        super.render(g2d, vertical, pos);
        
        // Draw siren lights
        sirenTimer = (sirenTimer + 1) % 20;
        
        AffineTransform old = g2d.getTransform();
        double cx = pos.getX() + width / 2.0;
        double cy = pos.getY() + height / 2.0;
        g2d.rotate(currentAngle, cx, cy);

        if (sirenTimer < 10) {
            g2d.setColor(new Color(255, 0, 0, 180)); // Red
            g2d.fillOval((int) pos.getX() + width / 4, (int) pos.getY() + height / 4, width / 4, height / 4);
        } else {
            g2d.setColor(new Color(0, 0, 255, 180)); // Blue
            g2d.fillOval((int) pos.getX() + (width * 2) / 4, (int) pos.getY() + height / 4, width / 4, height / 4);
        }
        
        g2d.setTransform(old);
    }
}
