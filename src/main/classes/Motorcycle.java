import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Motorcycle extends Vehicle {

    public Motorcycle(Orientation orientation, Approach approach, int x, int y, double speed, double curspeed, Road road) {
        super(orientation, approach, x, y, 20, 40, speed * 1.2, curspeed, road);
        loadSprite();
    }

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
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        if (sprite == null) return;
        AffineTransform old = g2d.getTransform();

        double cx = pos.getX() + width / 2.0;
        double cy = pos.getY() + height / 2.0;

        // Base angle from Vehicle class
        switch (approach) {
            case SOUTH: targetAngle = 0; break;
            case NORTH: targetAngle = Math.PI; break;
            case WEST: targetAngle = -Math.PI / 2; break;
            case EAST: targetAngle = Math.PI / 2; break;
        }

        double diff = targetAngle - currentAngle;
        while (diff > Math.PI) diff -= 2 * Math.PI;
        while (diff < -Math.PI) diff += 2 * Math.PI;
        currentAngle += diff * 0.15;
        
        g2d.rotate(currentAngle, cx, cy);

        // Add a lean effect when turning
        if (isTurning()) {
            double lean = (getTurnDirection() == TurnDirection.LEFT) ? -0.2 : 0.2;
            g2d.rotate(lean, cx, cy);
        }

        g2d.drawImage(
            sprite,
            (int) pos.getX(),
            (int) pos.getY(),
            width,
            height,
            null
        );

        g2d.setTransform(old);
    }
}