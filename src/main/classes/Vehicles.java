import java.util.List;



public class Vehicles {
    static int idCounter = 0;  
    int id;
    Coordinate position;
    int width,height;
    double speed;
    double curspeed;
    Road road;
    Orientation orientation;
    
    double turn_angle_north_to_west = 0;
    double turnRadius = 150;
    double INTERSECTION_X = 600;
    double INTERSECTION_Y = 550;

    Vehicles(int x, int y, Orientation orientation,int width, int height, double speed,double curspeed, Road road) {
        this.position = new Coordinate(x, y);
        this.orientation = orientation;
        this.width = width;
        this.height = height;
        this.speed = validate_speed(speed);
        this.curspeed = curspeed;
        this.road = road;
        this.id = idCounter++;
    }

    public void move(int windowsWidth, int windowHeight, Orientation orientation, Approach approach) {
        // the curspeed is used to move the vehicle
        if (approach == Approach.SOUTH) {
            //use setX method to move left to right and wrap around
            setX(position.x + curspeed);
            if (position.x > windowsWidth) {
                setX(-width); // Wrap around to the left
            }
        } else if (approach == Approach.EAST){
            setY(position.y + curspeed);
            if (position.y > windowHeight) {
                setY(-height); // Wrap around to the top
            }
        } else if (approach == Approach.WEST){
            setY(position.y - curspeed);
            if (position.y < 0) {
                setY(windowHeight); // Wrap around to the bottom
            }
        } else {
            setX(position.x - curspeed);
            if (position.x < 0) {
                setX(windowsWidth); // Wrap around to the right
            }
        }
    }

    double accelerate() {
        //calculate how much to increase speed
        return 0;
    }

    void breaking(){
        // breaking logic
    }


    public void stop() {
        this.speed = 0;
    }

    public void setSpeed(double speed) {
        this.speed = validate_speed(speed);
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getX() {
        return this.position.x;
    }

    public void setX(double x) {
        if (x < 0 && this.road.approach == Approach.NORTH) {
            x = 1000;
        }
        if (x > 1000) {
            x = 0;
        }
        this.position.x = x;
    }

    public double getY() {
        return this.position.y;
    }

    public void setY(double y) {
        if (y < 1) {
            y = 800;
        } else if (y > 800) {
            y = 0;
        }
        this.position.y = y;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }
    public int getId() { return id; }
    public Coordinate getPosition() { return position; }
    public Road getRoad() { return road; }

    private double validate_speed(double pspeed) {
        if (pspeed < 0) {
            pspeed = -pspeed;
        }
        // speed limit
        if (pspeed > 200) {
            pspeed = 199;
        }
        return pspeed;
    }


    public TrafficLight obeyLight(List<TrafficLight> all_TrafficLight){
        for(TrafficLight light : all_TrafficLight){
            if(this.road == light.road){
                return light;
            }
        }
        return null;
    }

    public Stopline obeyLine(List<Stopline> all_line){
        for(Stopline line : all_line){
            if(line.road == this.road){
                return line;
            }
        }
        return null;
    }
}