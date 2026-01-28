

public class Vehicles{
    Coordinate position;
    int width;
    int height;
    int speed;
    int lane;
    Orientation orientation;
    

    Vehicles(Orientation orientation, int x, int y, int width, int height, int speed, int lane) {
        this.orientation = orientation;
        this.position = new Coordinate(x, y);
        this.width = width;
        this.height = height;
        this.speed = validate_speed(speed);
        this.lane = lane;
    }

    public void move(int windowsWidth, int windowHeight, Orientation orientation) {
        
    }

    private int validate_speed(int pspeed){
        if (pspeed < 0){
            pspeed =  -pspeed;
        }
        // speed limit
        if (pspeed > 200){
            pspeed = 199;
        }
        return pspeed;
    }
}