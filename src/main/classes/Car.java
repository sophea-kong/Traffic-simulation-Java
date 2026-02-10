enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicles {
    private Car_load load;

    Car(Orientation orientation, int x, int y, double speed, double curspeed, Road road, Car_load load) {
        super(orientation, x, y, 50, 30, speed, curspeed, road);
        this.load = load;
    }

    @Override
    double accelerate() {
        double speedIncrease = 0.0;
        switch (load) {
            case ONE_PERSON:
                speedIncrease = 0.2;
                break;
            case TWO_PERSON:
                speedIncrease = 1.7;
                break;
            case THREE_PERSON:
                speedIncrease = 1.5;
                break;
            case FOUR_PERSON:
                speedIncrease = 0.1;
                break;
        }
        if (this.getCurspeed() >= this.getSpeed()) {
            speedIncrease = 0.0;
        }
        return speedIncrease;
    }

    @Override
    public void move(int windowsWidth, int windowHeight, Orientation orientation, Approach approach) {
        this.setCurspeed(this.getCurspeed() + accelerate());
        if (this.getCurspeed() > this.getSpeed()) {
            this.setCurspeed(this.getSpeed());
        }
        for(int i = 0; i < (int)this.getCurspeed(); i++) {
            if (approach == Approach.SOUTH) {
            //use setX method to move left to right and wrap around
            setX(this.getPosition().getX() + this.getCurspeed());
            if (this.getPosition().getX() > windowsWidth) {
                setX(-this.getwidth()); // Wrap around to the left
            }
        } else if (approach == Approach.EAST){
            setY(this.getPosition().getY() - this.getCurspeed());
            if (this.getPosition().getY() > windowHeight) {
                setY(-this.getheight()); // Wrap around to the top
            }
        } else if (approach == Approach.WEST){
            setY(this.getPosition().getY() + this.getCurspeed());
            if (this.getPosition().getY() < 0) {
                setY(windowHeight); // Wrap around to the bottom
            }
        } else {
            setX(this.getPosition().getX() - this.getCurspeed());
            if (this.getPosition().getX() < 0) {
                setX(windowsWidth); // Wrap around to the right
            }
        }
        }

    }

    public void setSpeedToZero() {
        this.setCurspeed(0);
    }


    // save the previous speed before stopping ( becuase stopping in main file mean that the speed is 0 so without saving previous speed we cant resume to previous speed)
    private double previousSpeed = this.getSpeed();

    public void setPreviousSpeed(double speed) {
        this.previousSpeed = speed;
    }

    public double getPreviousSpeed() {
        return this.previousSpeed;
    }

    public static Car create_car(Road road){
        if (road.getApproach() == Approach.NORTH) {
            return new Car(Orientation.VERTICAL, 1000, 350, 5.0, 5.0, road, Car_load.ONE_PERSON);
        } else if (road.getApproach() == Approach.SOUTH) {
            return new Car(Orientation.HORIZONTAL, 0, 450, 5.0, 5.0, road, Car_load.FOUR_PERSON);
        } else if (road.getApproach() == Approach.EAST) {
            return new Car(Orientation.VERTICAL, 550, 800, 5.0, 5.0, road, Car_load.TWO_PERSON);
        } else {
            return new Car(Orientation.HORIZONTAL, 450, 0, 5.0, 5.0, road, Car_load.THREE_PERSON);
        }
    }

}