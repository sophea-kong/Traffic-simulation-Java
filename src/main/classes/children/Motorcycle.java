package children;

import parents.Vehicle;
import utils.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Motorcycle extends Vehicle {

    public Motorcycle(Orientation orientation, Approach approach, double speed, double curspeed, Road road) {
        super(orientation, approach, 40, 20, speed * 1.2, curspeed, road);
        loadSprite();
    }

    @Override
    public boolean isEmergency() { return false; }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResource("/images/motorcycle/redMotorcycle.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public double getAccelerationRate() {
        return 0.4;
    }

    @Override
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        if (sprite == null) return;
        
        AffineTransform old = g2d.getTransform();
        double cx = pos.getX() + width / 2.0;
        double cy = pos.getY() + height / 2.0;

        // Apply lean to the whole graphics context if turning
        if (isTurning()) {
            double lean = (getTurnDirection() == TurnDirection.LEFT) ? -0.15 : 0.15;
            g2d.rotate(lean, cx, cy);
        }

        // super.render will apply the base rotation (currentAngle) and draw the sprite
        super.render(g2d, vertical, pos);

        g2d.setTransform(old);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }


    @Override
    public String toString() {
        return "Motorcycle{" +
                "id=" + id +
                ", approach=" + approach +
                '}';
    }
}