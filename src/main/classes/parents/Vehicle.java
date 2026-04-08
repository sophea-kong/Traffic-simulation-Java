package parents;

import interfaces.Renderable;
import utils.*;
import children.Road;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;


import java.awt.image.BufferedImage;


public abstract class Vehicle extends AnimatedObject{
    protected BufferedImage sprite;


    private Road road;
    private Road originalRoad;
    private TurnDirection turnDirection;
    private boolean hasTurned = false;
    private boolean isTurning = false;
    private LaneType laneType = LaneType.OUTER;
    private double turnTargetCoord = 0;

    // Circular Turning Fields
    private Coordinate turnCenter;
    private double turnRadius;
    private double currentTurnAngle;
    private double targetTurnAngle;
    private int turnDirectionFactor; // 1 for CCW, -1 for CW

    protected double currentAngle = 0;
    protected double targetAngle = 0;
    protected Approach approach;
    

    public Vehicle(Orientation orientation, Approach approach, int width, int height, double speed, double curspeed, Road road) {
        super(height, width, speed, curspeed);
        this.orientation = orientation;
        this.approach = approach;
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    public Vehicle(Orientation orientation, int height, int width, double speed, double curspeed, Road road) {
        super(height, width, speed, curspeed);
        this.orientation = orientation;
        this.road = road;
        this.originalRoad = road;
        this.approach = road.getApproach();
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    public Vehicle(Road road) {
        super(30, 50, 6.0, 0); 
        this.orientation = (road.getId() == 1 || road.getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.approach = switch (road.getId()) {
            case 1 -> Approach.SOUTH;
            case 2 -> Approach.NORTH;
            case 3 -> Approach.WEST;
            default -> Approach.EAST;
        };
        setRoad(road);
        this.originalRoad = road;
        this.turnDirection = TurnDirection.values()[new Random().nextInt(3)];
    }

    public abstract void loadSprite(); 

    public abstract double getAccelerationRate();

    public Road getOriginalRoad() { return originalRoad; }
    public TurnDirection getTurnDirection() { return turnDirection; }
    public void setTurnDirection(TurnDirection turnDirection) { this.turnDirection = turnDirection; }
    public boolean hasTurned() { return hasTurned; }
    public void setHasTurned(boolean b) { this.hasTurned = b; }
    public boolean isTurning() { return isTurning; }
    public void setTurning(boolean turning) { isTurning = turning; }
    public double getTurnTargetCoord() { return turnTargetCoord; }
    public void setTurnTargetCoord(double turnTargetCoord) { this.turnTargetCoord = turnTargetCoord; }
    public LaneType getLaneType() { return laneType; }
    public void setLaneType(LaneType laneType) { this.laneType = laneType; }
    
    // Circular Turn Getters/Setters
    public Coordinate getTurnCenter() { return turnCenter; }
    public void setTurnCenter(Coordinate turnCenter) { this.turnCenter = turnCenter; }
    public double getTurnRadius() { return turnRadius; }
    public void setTurnRadius(double turnRadius) { this.turnRadius = turnRadius; }
    public double getCurrentTurnAngle() { return currentTurnAngle; }
    public void setCurrentTurnAngle(double currentTurnAngle) { this.currentTurnAngle = currentTurnAngle; }
    public double getTargetTurnAngle() { return targetTurnAngle; }
    public void setTargetTurnAngle(double targetTurnAngle) { this.targetTurnAngle = targetTurnAngle; }
    public int getTurnDirectionFactor() { return turnDirectionFactor; }
    public void setTurnDirectionFactor(int turnDirectionFactor) { this.turnDirectionFactor = turnDirectionFactor; }

    public Road getRoad() { return road; }
    public void setRoad(Road road) { 
        this.road = road; 
        if (road != null) {
            this.approach = road.getApproach();
        }
    }
    public int getwidth() { return super.getWidth(); }
    public int getheight() { return super.getHeight(); }
    public abstract boolean isEmergency();

    @Override
    public void update(int deltaMs) {
    }
    
    @Override
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
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
        return hasTurned == vehicle.hasTurned && isTurning == vehicle.isTurning && laneType == vehicle.laneType && Double.compare(vehicle.turnTargetCoord, turnTargetCoord) == 0 && Double.compare(vehicle.currentAngle, currentAngle) == 0 && Double.compare(vehicle.targetAngle, targetAngle) == 0 && java.util.Objects.equals(road, vehicle.road) && java.util.Objects.equals(originalRoad, vehicle.originalRoad) && turnDirection == vehicle.turnDirection && approach == vehicle.approach;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", height=" + height +
                ", width=" + width +
                ", road=" + road +
                ", turnDirection=" + turnDirection +
                ", hasTurned=" + hasTurned +
                ", isTurning=" + isTurning +
                ", laneType=" + laneType +
                ", approach=" + approach +
                '}';
    }
}
