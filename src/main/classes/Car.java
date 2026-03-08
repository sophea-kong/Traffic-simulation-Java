enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicles {
    private Car_load load;
    private double previousSpeed = 6.0;

    Car(Orientation orientation, int x, int y, double speed, double curspeed, Road road, Car_load load) {
        super(orientation, x, y, 30, 50, speed, curspeed, road);
        this.load = load;
        this.previousSpeed = speed;
    }

    Car(Road road) {
        super(road);
        this.load = Car_load.ONE_PERSON;
    }

    public void setPreviousSpeed(double speed) { this.previousSpeed = speed; }
    public double getPreviousSpeed() { return this.previousSpeed; }

    public static Car create_car(Road road){
        return new Car(road);
    }
}
