

public class Vehicles{
    int x;
    int y;
    int width;
    int height;
    int speed;
    int lane;
    Orientation orientation;
    

    public Vehicles(Orientation orientation, int x, int y, int width, int height, int speed, int lane) {
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = validate_speed(speed);
        this.lane = lane;
    }

    public void update(int windowsWidth, int windowHeight, Orientation orientation) {
        if (orientation == Orientation.VERTICAL) {
            y += speed;
            if (y > windowHeight) {
                y = -height;
            }
        } else {
            x += speed;
            if (x > windowsWidth) {
                x = -width;
            }
        }
    }

    int validate_speed(int pspeed){
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