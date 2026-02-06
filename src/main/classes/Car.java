enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicles {
    Car_load load;

    Car(int x, int y, Orientation orientation,Road road, Car_load load) {
        super(x, y, orientation, 50, 30, 6.0, 0, road);
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
        if (this.curspeed >= this.speed) {
            speedIncrease = 0.0;
        }
        return speedIncrease;
    }

    @Override
    public void move(int windowsWidth, int windowHeight, Orientation orientation, Approach approach) {
        this.curspeed += accelerate();
        if (this.curspeed > this.speed) {
            this.curspeed = this.speed;
        }
        for(int i = 0; i < (int)this.curspeed; i++) {
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

    }

    public void setSpeedToZero() {
        this.curspeed = 0;
    }


    // save the previous speed before stopping ( becuase stopping in main file mean that the speed is 0 so without saving previous speed we cant resume to previous speed)
    private double previousSpeed = this.speed;

    public void setPreviousSpeed(double speed) {
        this.previousSpeed = speed;
    }

    public double getPreviousSpeed() {
        return this.previousSpeed;
    }

}