public class Vehicles{
    int x;
    int y;
    int width;
    int height;
    int speed;
    int lane;

    public Vehicles(int x, int y, int width, int height, int speed, int lane) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.lane = lane;
    }

    public void update(int windowsWidth) {
        x += speed;
        if(x > windowsWidth) {
            x = -width;
        }
    }
}