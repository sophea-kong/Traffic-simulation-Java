import java.util.List;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.util.Random;

enum TurnDirection {
    STRAIGHT, LEFT, RIGHT
}

enum Action {
    StopAtTrafficLight, RespectOtherVehicle
}

public class Vehicles extends AnimatedObject implements Renderable {
    private static int vehicleCount = 1;
    private Road road;
    private Road originalRoad;
    private TurnDirection turnDirection;



    private boolean hasTurned = false;
    private boolean isTurning = false;
    private double turnTargetCoord = 0;
    private Color color;

    Vehicles(Orientation orientation, int width, int height, double speed, double curspeed, Road road) {
        super(width, height, speed, curspeed);
        this.orientation = orientation;
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    Vehicles(Road road) {
        super(50, 30, 6.0, 0);
        this.orientation = (road.getId() == 1 || road.getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    public Road getOriginalRoad() { return originalRoad; }
    public TurnDirection getTurnDirection() { return turnDirection; }
    public void setTurnDirection(TurnDirection turnDirection) { this.turnDirection = turnDirection; }
    public boolean hasTurned() { return hasTurned; }
    public void setHasTurned(boolean b) { this.hasTurned = b; }
    public boolean isTurning() { return isTurning; }
    public void setTurning(boolean turning) { isTurning = turning; }
    public double getTurnTargetCoord() { return turnTargetCoord; }
    public void setTurnTargetCoord(double turnTargetCoord) { this.turnTargetCoord = turnTargetCoord; }
    public Road getRoad() { return road; }
    public void setRoad(Road road) { this.road = road; }
    public int getwidth() { return super.getWidth(); }
    public int getheight() { return super.getHeight(); }
    public boolean isLawEnforced( Action action ) { return false; }

    @Override
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        // Save original transform
        AffineTransform oldTx = g2d.getTransform();

        // Compute center of the car to rotate around its center
        double cx = pos.getX() + this.getwidth() / 2.0;
        double cy = pos.getY() + this.getheight() / 2.0;

        // Rotate 90 degrees (π/2) when vertical; 0 when horizontal
        double angle = vertical ? Math.PI / 2.0 : 0.0;
        g2d.rotate(angle, cx, cy);

        // Draw body
        g2d.setColor(this.color != null ? this.color : Color.RED);
        g2d.fillRect((int) pos.getX(), (int) pos.getY(), this.getwidth(), this.getheight());

        // Draw windows
        g2d.setColor(Color.CYAN);
        g2d.fillRect((int) (pos.getX() + 5), (int) (pos.getY() + 5), 8, 8);
        g2d.fillRect((int) (pos.getX() + this.getwidth() - 13), (int) (pos.getY() + 5), 8, 8);

        // Draw wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int) (pos.getX() + 5), (int) (pos.getY() + this.getheight() - 8), 6, 6);
        g2d.fillOval((int) (pos.getX() + this.getwidth() - 11), (int) (pos.getY() + this.getheight() - 8), 6, 6);

        // Restore original transform
        g2d.setTransform(oldTx);
    }
}
