public class AnimatedObject extends SimObject{
    private double speed;
    private double curspeed;
    protected Orientation orientation;
    

    public AnimatedObject(SimObject object, double speed, double curspeed) {
        super(object.getHeight(), object.getWidth());
        this.speed = speed;
        this.curspeed = curspeed;
    }
    public AnimatedObject(int height, int width, double speed, double curspeed) {
        super(height, width);
        this.speed = speed;
        this.curspeed = curspeed;
    }

    public double getCurspeed() { return curspeed; }
    public void setCurspeed(double curspeed) {
        if(curspeed > speed) curspeed = speed;
        else if (curspeed < 0) curspeed = 0;
        this.curspeed = curspeed;
    }
    public double getSpeed() { return this.speed; }
    public void setSpeed(double pspeed) { this.speed = pspeed; }
    public Orientation getOrientation() { return this.orientation; }
    public void setOrientation(Orientation orientation) { this.orientation = orientation; }

    public boolean canMoving() { return true; };
}
