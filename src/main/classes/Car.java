import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicle {
    private Car_load load;
    private double previousSpeed = 6.0;

    Car(Orientation orientation, double speed, double curspeed, Road road, Car_load load) {
        super(orientation, 30, 50, speed, curspeed, road);
        this.load = load;
        this.previousSpeed = speed;
        loadSprite();
    }

    Car(Road road) {
        super(road);
        this.load = Car_load.ONE_PERSON;
        loadSprite();
    }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResource("/images/car/redCar.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setPreviousSpeed(double speed) { this.previousSpeed = speed; }
    public double getPreviousSpeed() { return this.previousSpeed; }
    public Car_load getLoad() { return load; }
    public void setLoad(Car_load load) { this.load = load; }

    public static Car create_car(Road road){
        return new Car(road);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return Double.compare(car.previousSpeed, previousSpeed) == 0 && load == car.load;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", load=" + load +
                ", previousSpeed=" + previousSpeed +
                ", orientation=" + orientation +
                ", approach=" + approach +
                '}';
    }
}
