import java.util.List;
import java.awt.Graphics2D;

public class Vehicles implements Renderable {
    private static int vehicleCount = 1;
    private int vehicleId;
    private Coordinate position;
    private int width;
    private int height;
    private double speed;
    private double curspeed;
    private Road road;
    private Orientation orientation;

    Vehicles(Orientation orientation, int x, int y, int width, int height, double speed, double curspeed, Road road) {
        this.orientation = orientation;
        this.position = new Coordinate(x, y);
        setWidth(width);
        setHeight(height);
        setSpeed(speed);
        setCurspeed(curspeed);
        setRoad(road);
        this.vehicleId = vehicleCount++;
        this.previousSpeed = this.speed;
    }

    Vehicles(Road road) {
        this.orientation = (road.getId() == 1 || road.getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.position = new Coordinate((road.getId() == 1) ? 500.0 : (road.getId() == 2) ? 600.0 : 500.0,
                (road.getId() == 1) ? 450.0 : (road.getId() == 2) ? 400.0 : (road.getId() == 3) ? 750.0 : 50.0);
        setWidth(50);
        setHeight(30);
        setSpeed(6.0);
        setCurspeed(0);
        setRoad(road);
        this.vehicleId = vehicleCount++;
        this.previousSpeed = this.speed;
    }

    Vehicles(Road road, double speed) {
        this.orientation = (road.getId() == 1 || road.getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.position = new Coordinate((road.getId() == 1) ? 500.0 : (road.getId() == 2) ? 600.0 : 500.0,
                (road.getId() == 1) ? 450.0 : (road.getId() == 2) ? 400.0 : (road.getId() == 3) ? 750.0 : 50.0);
        setWidth(50);
        setHeight(30);
        setSpeed(speed);
        setCurspeed(0);
        setRoad(road);
        this.vehicleId = vehicleCount++;
        this.previousSpeed = this.speed;
    }

    public void move(int windowsWidth, int windowHeight) {
        this.setCurspeed(this.getCurspeed() + accelerate());
        if (this.getCurspeed() > this.getSpeed()) {
            this.setCurspeed(this.getSpeed());
        }
        for(int i = 0; i < (int)this.getCurspeed(); i++) {
            if (this.getRoad().getApproach() == Approach.SOUTH) {
            //use setX method to move left to right and wrap around
            setX(this.getPosition().getX() - this.getCurspeed());
            if (this.getPosition().getX() > windowsWidth) {
                setX(-this.getwidth()); // Wrap around to the left
            }
        } else if (this.getRoad().getApproach() == Approach.EAST){
            setY(this.getPosition().getY() + this.getCurspeed());
            if (this.getPosition().getY() > windowHeight) {
                setY(-this.getheight()); // Wrap around to the top
            }
        } else if (this.getRoad().getApproach() == Approach.WEST){
            setY(this.getPosition().getY() - this.getCurspeed());
            if (this.getPosition().getY() < 0) {
                setY(this.getheight()); // Wrap around to the bottom
            }
        } else {
            setX(this.getPosition().getX() + this.getCurspeed());
            if (this.getPosition().getX() < 0) {
                setX(this.getwidth()); // Wrap around to the right
            }
        }
        }
    }

    double accelerate() {
        // calculate how much to increase speed
        return 0;
    }

    void breaking() {
        // breaking logic
    }

    // getter and setter
    private void setRoad(Road road) {
        if (road == null) {
            return;
        }
        this.road = road;
    }

    public int getVehicleId() {
        return this.vehicleId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public double getCurspeed() {
        return curspeed;
    }

    public void setCurspeed(double curspeed) {
        if (curspeed > speed) {
            curspeed = speed;
        } else if (curspeed < 0) {
            curspeed = 0;
        }
        this.curspeed = curspeed;
    }


    public void setSpeedToZero() {
        this.setCurspeed(0);
    }

    
    public int getwidth() {
        return width;
    }

    private void setWidth(int width) {
        if (width < 10) {
            width = 10;
        } else if (width > 200) {
            width = 200;
        }
        this.width = width;
    }

    private void setHeight(int height) {
        if (height < 10) {
            height = 10;
        } else if (height > 200) {
            height = 200;
        }
        this.height = height;
    }

    public int getheight() {
        return height;
    }

    public Road getRoad() {
        return road;
    }

    public void stop() {
        this.curspeed = 0;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getX() {
        return this.position.getX();
    }

    public void setX(double x) {
        if (x < 0) {
            x = 1000;
        }
        if (x > 1000) {
            x = 0;
        }
        this.position.setX(x);
    }

    public double getY() {
        return this.position.getY();
    }

    public void setY(double y) {
        if (y < 1) {
            y = 800;
        } else if (y > 800) {
            y = 0;
        }
        this.position.setY(y);
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setSpeed(double pspeed) {
        if (pspeed < 0) {
            pspeed = -pspeed;
        }
        // speed limit
        if (pspeed > 200) {
            pspeed = 199;
        }
        this.speed = pspeed;
    }

    public TrafficLight obeyLight(List<TrafficLight> all_TrafficLight) {
        if (this.road == null)
            return null;
        for (TrafficLight light : all_TrafficLight) {
            if (light == null)
                continue;
            Road lroad = light.getRoad();
            if (lroad == null)
                continue;
            // match either by the same Road instance or by the same Approach
            if (this.road == lroad || this.road.getId() == lroad.getId()) {
                return light;
            }
        }
        return null;
    }

    public Stopline obeyLine(List<Stopline> all_line) {
        for (Stopline line : all_line) {
            if (line.getRoad().getId() == this.road.getId()) {
                return line;
            }
        }
        return null;
    }

    private double previousSpeed;

    public void setPreviousSpeed(double speed) {
        this.previousSpeed = speed;
    }

    public double getPreviousSpeed() {
        return this.previousSpeed;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vehicles other = (Vehicles) obj;
        return vehicleId == other.vehicleId;
    }


    @Override
    public String toString() {
        return "Vehicle " + vehicleId + " at (" + position.getX() + ", " + position.getY() + ") with speed " + speed;
    }



    public void render(Graphics2D g2d, boolean vertical) {
    //     // Save original transform
    //     AffineTransform oldTx = g2d.getTransform();

    //     // Compute center of the car to rotate around its center
    //     double cx = this.getX() + this.width / 2.0;
    //     double cy = this.getY() + this.height / 2.0;

    //     // Rotate 90 degrees (π/2) when vertical; 0 when horizontal
    //     double angle = vertical ? Math.PI / 2.0 : 0.0;
    //     g2d.rotate(angle, cx, cy);

    //     // Draw body
    //     g2d.setColor(Color.RED);
    //     g2d.fillRect((int) this.getX(), (int) this.getY(), this.width, this.height);

    //     // Draw windows
    //     g2d.setColor(Color.CYAN);
    //     g2d.fillRect((int) (this.getX() + 5), (int) (this.getY() + 5), 8, 8);
    //     g2d.fillRect((int) (this.getX() + this.width - 13), (int) (this.getY() + 5), 8, 8);

    //     // Draw wheels
    //     g2d.setColor(Color.BLACK);
    //     g2d.fillOval((int) (this.getX() + 5), (int) (this.getY() + this.height - 8), 6, 6);
    //     g2d.fillOval((int) (this.getX() + this.width - 11), (int) (this.getY() + this.height - 8), 6, 6);

    //     // Restore original transform
    //     g2d.setTransform(oldTx);
    }

}