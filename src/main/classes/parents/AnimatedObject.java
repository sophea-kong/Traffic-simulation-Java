package parents;

import interfaces.Renderable;
import interfaces.Updatable;
import utils.Orientation;


public abstract class AnimatedObject extends SimObject implements Renderable, Updatable {
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

    public boolean canMove() { return true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AnimatedObject that = (AnimatedObject) o;
        return Double.compare(that.speed, speed) == 0 && Double.compare(that.curspeed, curspeed) == 0 && orientation == that.orientation;
    }
    
    @Override
    public String toString() {
        return "AnimatedObject{" +
                "id=" + id +
                ", height=" + height +
                ", width=" + width +
                ", speed=" + speed +
                ", curspeed=" + curspeed +
                ", orientation=" + orientation +
                '}';
    }
}
