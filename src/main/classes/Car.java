enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicles {
    private Car_load load;
    private double previousSpeed = 6.0;


    public boolean isLawEnforced(Action action) { return true; }
    
    Car(Orientation orientation, double speed, double curspeed, Road road, Car_load load) {
        super(orientation, 30, 50, speed, curspeed, road);
        this.load = load;
        this.previousSpeed = speed;
    }

    Car(Road road) {
        super(road);
        this.load = Car_load.ONE_PERSON;
    }

    public void setPreviousSpeed(double speed) { this.previousSpeed = speed; }
    public double getPreviousSpeed() { return this.previousSpeed; }
    public Car_load getLoad() { return load; }
    public void setLoad(Car_load load) { this.load = load; }

    public static Car create_car(Road road){
        return new Car(road);
    }
}
