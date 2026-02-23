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
        //this.roadId = road.id;
    }
    Car(Orientation orientation, int x, int y, int roadId ) {
        super(orientation, x, y, 50, 30, 6.0, 0, null);
        //this.roadId = roadId;
        this.load = Car_load.ONE_PERSON;
    }
    Car(Road road) {
        super((road.getId() == 1 || road.getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL,
              (idToX(road.getId())),
              (road.getId() == 1) ? 450 : (road.getId() == 2) ? 350 : (road.getId() == 3) ? 750 : 50,
              50, 30, 6.0, 0, road);
        //this.roadId = road.id;
        
        this.load = Car_load.ONE_PERSON;
    }

    private static int idToX(int roadId){
        if(roadId == 1){
            return 500;
        } else if (roadId == 2){
            return 600;
        } else if (roadId == 3){
            return 450;
        } else {
            return 500;
        }
    }




    Car(int RoadId) {
        super((RoadId == 1 || RoadId == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL,
              (RoadId == 1) ? 500 : (RoadId == 2) ? 600 : 500,
              (RoadId == 1) ? 450 : (RoadId == 2) ? 400 : (RoadId == 3) ? 750 : 50,
              50, 30, 6.0, 0, null);
        //this.roadId = RoadId;
        this.load = Car_load.ONE_PERSON;
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
            setX(this.getPosition().getX() - this.getCurspeed());
            if (this.getPosition().getX() > windowsWidth) {
                setX(-this.getwidth()); // Wrap around to the left
            }
        } else if (approach == Approach.EAST){
            setY(this.getPosition().getY() + this.getCurspeed());
            if (this.getPosition().getY() > windowHeight) {
                setY(-this.getheight()); // Wrap around to the top
            }
        } else if (approach == Approach.WEST){
            setY(this.getPosition().getY() - this.getCurspeed());
            if (this.getPosition().getY() < 0) {
                setY(this.getheight()); // Wrap around to the bottom
            }
        } else {
            setX(this.getPosition().getX() + this.getCurspeed());
            if (this.getPosition().getX() < 0) {
                setX(this.getwidth()); // Wrap around to the right
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

    public static Car create_car(int roadId) {
        if (roadId == 1) {
            return new Car(Orientation.HORIZONTAL, 0, 450, 5.0, 5.0, null, Car_load.FOUR_PERSON);
        } else if (roadId == 2) {
            return new Car(Orientation.HORIZONTAL, 1000, 350, 5.0, 5.0, null, Car_load.ONE_PERSON);
        } else if (roadId == 3) {
            return new Car(Orientation.VERTICAL, 550, 800, 5.0, 5.0, null, Car_load.TWO_PERSON);
        } else {
            return new Car(Orientation.VERTICAL, 450, 0, 5.0, 5.0, null, Car_load.THREE_PERSON);
        }
    }

}