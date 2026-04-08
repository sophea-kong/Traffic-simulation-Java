package children;

import parents.Vehicle;
import utils.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ambulance extends Vehicle {
    private long sirenTimer = 0;

    public Ambulance(Orientation orientation, Approach approach, double speed, double curspeed, Road road) {
        super(orientation, approach, 60, 30, speed * 1.5, curspeed, road); 
        loadSprite();
    }

    @Override
    public boolean isEmergency() { return true; }

    @Override
    public double getAccelerationRate() {
        return 0.2;
    }

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
        
        if (sprite == null) return;

        // Draw siren lights on top
        sirenTimer = (sirenTimer + 1) % 20;
        
        AffineTransform old = g2d.getTransform();
        double cx = pos.getX() + width / 2.0;
        double cy = pos.getY() + height / 2.0;
        
        // Rotate the siren lights with the vehicle
        g2d.rotate(currentAngle, cx, cy);

        if (sirenTimer < 10) {
            g2d.setColor(new Color(255, 0, 0, 200)); // Flash Red
            g2d.fillOval((int) pos.getX() + width / 4, (int) pos.getY() + height / 3, width / 5, height / 5);
        } else {
            g2d.setColor(new Color(0, 0, 255, 200)); // Flash Blue
            g2d.fillOval((int) pos.getX() + (width * 3) / 5, (int) pos.getY() + height / 3, width / 5, height / 5);
        }
        
        g2d.setTransform(old);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ambulance ambulance = (Ambulance) o;
        return sirenTimer == ambulance.sirenTimer;
    }


    @Override
    public String toString() {
        return "Ambulance{" +
                "id=" + id +
                ", sirenTimer=" + sirenTimer +
                ", approach=" + approach +
                '}';
    }
}
