import javax.imageio.ImageIO;
import java.io.IOException;

enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicle {
    private Car_load load;
    private double previousSpeed = 6.0;

    Car(Orientation orientation, Approach approach, int x, int y, double speed, double curspeed, Road road, Car_load load) {
        super(orientation, approach, x, y, 30, 50, speed, curspeed, road);
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

    public static Car create_car(Road road){
        return new Car(road);
    }
}
