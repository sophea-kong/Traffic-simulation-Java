enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicles {
    private Car_load load;

    Car(Orientation orientation, int x, int y, double speed, double curspeed, Road road, Car_load load, Approach exit) {
        super(orientation, x, y, 50, 30, speed, curspeed, road, exit);
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
}