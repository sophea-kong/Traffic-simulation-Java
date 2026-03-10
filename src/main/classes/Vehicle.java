import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.awt.image.BufferedImage;

enum TurnDirection {
    STRAIGHT, LEFT, RIGHT
}

public abstract class Vehicle extends AnimatedObject implements Renderable, Updatable {
    protected BufferedImage sprite;


    private Coordinate spawnPosition;
    private Road road;
    private Road originalRoad;
    private TurnDirection turnDirection;
    private boolean hasTurned = false;
    private boolean isTurning = false;
    private double turnTargetCoord = 0;
    protected double currentAngle = 0;
    protected double targetAngle = 0;
    protected Coordinate position;
    protected Approach approach;
    

    Vehicle(Orientation orientation, Approach approach, int x, int y, int width, int height, double speed, double curspeed, Road road) {
        super(height, width, speed, curspeed);
        this.orientation = orientation;
        this.approach = approach;
        this.position = new Coordinate(x, y);
        this.spawnPosition = new Coordinate(x, y);
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    Vehicle(Orientation orientation, int height, int width, double speed, double curspeed, Road road) {
        super(height, width, speed, curspeed);
        this.orientation = orientation;
        this.road = road;
        this.originalRoad = road;
        this.approach = road.getApproach();
        
        // Spawn logic
        double x = (road.getId() == 1) ? 500.0 : (road.getId() == 2) ? 600.0 : 500.0;
        double y = (road.getId() == 1) ? 450.0 : (road.getId() == 2) ? 400.0 : (road.getId() == 3) ? 750.0 : 50.0;
        this.position = new Coordinate(x, y);
        this.spawnPosition = new Coordinate(x, y);
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    Vehicle(Road road) {
        super(30, 50, 6.0, 0); 
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
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    public abstract void loadSprite(); 

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
    public Road getRoad() { return road; }
    public void setRoad(Road road) { 
        this.road = road; 
        if (road != null) {
            this.approach = road.getApproach();
        }
    }
    public int getwidth() { return super.getWidth(); }
    public int getheight() { return super.getHeight(); }
    public boolean isEmergency() { return false; }

    @Override
    public void update(int deltaMs) {
    }
    
    @Override
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        this.position = pos; 
        if (sprite == null) return;
        AffineTransform old = g2d.getTransform();

        double cx = pos.getX() + width / 2.0;
        double cy = pos.getY() + height / 2.0;

        switch (approach) {
            case SOUTH: 
                targetAngle = 0;
                break;
            case NORTH: 
                targetAngle = Math.PI;
                break;
            case WEST: 
                targetAngle = -Math.PI / 2;
                break;
            case EAST: 
                targetAngle = Math.PI / 2;
                break;
        }

        double diff = targetAngle - currentAngle;
        while (diff > Math.PI) diff -= 2 * Math.PI;
        while (diff < -Math.PI) diff += 2 * Math.PI;
        currentAngle += diff * 0.15;
        
        g2d.rotate(currentAngle, cx, cy);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return hasTurned == vehicle.hasTurned && isTurning == vehicle.isTurning && Double.compare(vehicle.turnTargetCoord, turnTargetCoord) == 0 && Double.compare(vehicle.currentAngle, currentAngle) == 0 && Double.compare(vehicle.targetAngle, targetAngle) == 0 && java.util.Objects.equals(spawnPosition, vehicle.spawnPosition) && java.util.Objects.equals(road, vehicle.road) && java.util.Objects.equals(originalRoad, vehicle.originalRoad) && turnDirection == vehicle.turnDirection && java.util.Objects.equals(position, vehicle.position) && approach == vehicle.approach;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", height=" + height +
                ", width=" + width +
                ", spawnPosition=" + spawnPosition +
                ", road=" + road +
                ", turnDirection=" + turnDirection +
                ", hasTurned=" + hasTurned +
                ", isTurning=" + isTurning +
                ", approach=" + approach +
                '}';
    }
}
