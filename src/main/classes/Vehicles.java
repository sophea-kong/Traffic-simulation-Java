public class Vehicles{
    Coordinate position;
    float width;
    float height;
    float speed;
    int lane;
    Orientation orientation;

    public Vehicles(Orientation orientation, float x, float y, float width, float height, float speed, int lane) {
        this.orientation = orientation;
        this.position = new Coordinate(x, y);
        this.width = width;
        this.height = height;
        this.speed = validateSpeed(speed);
        this.lane = lane;
    }
    
    public void move(int windowWidth, int windowHeight) {
        if (orientation == Orientation.HORIZONTAL) {
            position.translate(speed, 0);
        } else {
            position.translate(0, speed);
        }
    }
    
    private float validateSpeed(float pspeed){
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