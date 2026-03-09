import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.util.Random;
import java.awt.image.BufferedImage;

enum TurnDirection {
    STRAIGHT, LEFT, RIGHT
}

public abstract class Vehicle extends AnimatedObject implements Renderable, Updatable {
    // private static int vehicleCount = 1;
    // private Color color;
    protected BufferedImage sprite;
    private Coordinate spawnPosition;
    private double speed;
    private double curspeed;
    private Road road;
    private Road originalRoad;
    protected Orientation orientation;
    protected Approach approach;
    private TurnDirection turnDirection;
    private boolean hasTurned = false;
    private boolean isTurning = false;
    private double turnTargetCoord = 0;
    protected double currentAngle = 0;
    protected double targetAngle = 0;
    

    Vehicle(Orientation orientation, Approach approach, int x, int y, int width, int height, double speed, double curspeed, Road road) {
        super(new Coordinate(x, y), width, height);
        this.orientation = orientation;
        this.approach = approach;
        this.spawnPosition = new Coordinate(x, y);
        setSpeed(speed);
        setCurspeed(curspeed);
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    Vehicle(Road road) {
        super(null, 50, 30);
        this.orientation = (road.getId() == 1 || road.getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.approach = switch (road.getId()) {
            case 1 -> Approach.SOUTH;
            case 2 -> Approach.NORTH;
            case 3 -> Approach.WEST;
            default -> Approach.EAST;
        };
        double x = (road.getId() == 1) ? 500.0 : (road.getId() == 2) ? 600.0 : 500.0;
        double y = (road.getId() == 1) ? 450.0 : (road.getId() == 2) ? 400.0 : (road.getId() == 3) ? 750.0 : 50.0;
        this.position = new Coordinate(x, y);
        this.spawnPosition = new Coordinate(x, y);
        setSpeed(6.0);
        setCurspeed(0);
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    public abstract void loadSprite(); // each subclass will load its own sprite

    public Coordinate getSpawnPosition() { return spawnPosition; }
    public Road getOriginalRoad() { return originalRoad; }
    public TurnDirection getTurnDirection() { return turnDirection; }
    public void setTurnDirection(TurnDirection turnDirection) { this.turnDirection = turnDirection; }
    public boolean hasTurned() { return hasTurned; }
    public void setHasTurned(boolean b) { this.hasTurned = b; }
    public boolean isTurning() { return isTurning; }
    public void setTurning(boolean turning) { isTurning = turning; }
    public double getTurnTargetCoord() { return turnTargetCoord; }
    public void setTurnTargetCoord(double turnTargetCoord) { this.turnTargetCoord = turnTargetCoord; }
    public Orientation getOrientation() { return this.orientation; }
    public void setOrientation(Orientation orientation) { this.orientation = orientation; }
    public Road getRoad() { return road; }
    public void setRoad(Road road) { this.road = road; }
    public double getCurspeed() { return curspeed; }
    public void setCurspeed(double curspeed) {
        if(curspeed > speed) curspeed = speed;
        else if (curspeed < 0) curspeed = 0;
        this.curspeed = curspeed;
    }
    public double getSpeed() { return this.speed; }
    public void setSpeed(double pspeed) { this.speed = pspeed; }
    public int getwidth() { return super.getWidth(); }
    public int getheight() { return super.getHeight(); }

    @Override
    public void update(int deltaMs) {
        
    }
    
    @Override
    public void render(Graphics2D g2d) {

        if (sprite == null) return;
        AffineTransform old = g2d.getTransform();

        double cx = position.getX() + width / 2.0;
        double cy = position.getY() + height / 2.0;

        switch (approach) {

            case SOUTH: // left → right
                targetAngle = 0;
                break;

            case NORTH: // right → left
                targetAngle = Math.PI;
                break;

            case WEST: // up → down
                targetAngle = -Math.PI / 2;
                break;

            case EAST: // down → up
                targetAngle = Math.PI / 2;
                break;
    }

    currentAngle += (targetAngle - currentAngle) * 0.15;
    g2d.rotate(currentAngle, cx, cy);

    g2d.drawImage(
        sprite,
        (int) position.getX(),
        (int) position.getY(),
        width,
        height,
        null
    );

    g2d.setTransform(old);
}
}
        // @Override
    // public void render(Graphics2D g2d, boolean vertical) {
    //     // Save original transform
    //     AffineTransform oldTx = g2d.getTransform();

    //     // Compute center of the car to rotate around its center
    //     double cx = this.getX() + this.getwidth() / 2.0;
    //     double cy = this.getY() + this.getheight() / 2.0;

    //     // Rotate 90 degrees (π/2) when vertical; 0 when horizontal
    //     double angle = vertical ? Math.PI / 2.0 : 0.0;
    //     g2d.rotate(angle, cx, cy);

//         // Draw body
//         g2d.setColor(this.color != null ? this.color : Color.RED);
//         g2d.fillRect((int) this.getX(), (int) this.getY(), this.getwidth(), this.getheight());

//         // Draw windows
//         g2d.setColor(Color.CYAN);
//         g2d.fillRect((int) (this.getX() + 5), (int) (this.getY() + 5), 8, 8);
//         g2d.fillRect((int) (this.getX() + this.getwidth() - 13), (int) (this.getY() + 5), 8, 8);

//         // Draw wheels
//         g2d.setColor(Color.BLACK);
//         g2d.fillOval((int) (this.getX() + 5), (int) (this.getY() + this.getheight() - 8), 6, 6);
//         g2d.fillOval((int) (this.getX() + this.getwidth() - 11), (int) (this.getY() + this.getheight() - 8), 6, 6);

//         // Restore original transform
//         g2d.setTransform(oldTx);
//     }
// }
