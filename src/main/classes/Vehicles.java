import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;


public class Vehicles {
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
    }

    public Vehicles(Orientation orientation2, Object object, int i, int j, double d, int k, Road road2) {
        //TODO Auto-generated constructor stub
    }

    public void move(int windowsWidth, int windowHeight, Orientation orientation, Approach approach) {
        // the curspeed is used to move the vehicle
        if (approach == Approach.SOUTH) {
            // use setX method to move left to right and wrap around
            setX(position.getX() + curspeed);
            if (position.getX() > windowsWidth) {
                setX(-width); // Wrap around to the left
            }
        } else if (approach == Approach.EAST) {
            setY(position.getY() + curspeed);
            if (position.getY() > windowHeight) {
                setY(-height); // Wrap around to the top
            }
        } else if (approach == Approach.WEST) {
            setY(position.getY() - curspeed);
            if (position.getY() < 0) {
                setY(windowHeight); // Wrap around to the bottom
            }
        } else {
            setX(position.getX() - curspeed);
            if (position.getX() < 0) {
                setX(windowsWidth); // Wrap around to the right
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
        if(curspeed > speed) {
            curspeed = speed;
        } else if (curspeed < 0) {
            curspeed = 0;
        }
        this.curspeed = curspeed;
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
        if (x < 0 && this.road.getApproach() == Approach.NORTH) {
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
            if (this.road == lroad || this.road.getApproach() == lroad.getApproach()) {
                return light;
            }
        }
        return null;
    }

    public Stopline obeyLine(List<Stopline> all_line) {
        for (Stopline line : all_line) {
            if (line.getRoad().getApproach() == this.road.getApproach()) {
                return line;
            }
        }
        return null;
    }




    public static void drawVehicle(Graphics2D g2d, Vehicles v) {
        g2d.setColor(Color.RED);
        g2d.fillRect((int)v.getX(), (int)v.getY(), v.width, v.height);
        
        // Draw windows
        g2d.setColor(Color.CYAN);
        g2d.fillRect((int)(v.getX() + 5), (int)(v.getY() + 5), 8, 8);
        g2d.fillRect((int)(v.getX() + v.width - 13), (int)(v.getY() + 5), 8, 8);
        
        // Draw wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(v.getX() + 5), (int)(v.getY() + v.height - 8), 6, 6);
        g2d.fillOval((int)(v.getX() + v.width - 11), (int)(v.getY() + v.height - 8), 6, 6);
    }

}