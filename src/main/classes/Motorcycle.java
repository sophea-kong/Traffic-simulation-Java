import javax.imageio.ImageIO;
import java.io.IOException;

public class Motorcycle extends Vehicle {

    public Motorcycle(Orientation orientation, Approach approach, int x, int y, double speed, double curspeed, Road road) {
        super(orientation, approach, x, y, 20, 40, speed, curspeed, road);
        loadSprite();
    }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResource("/images/motorcycle/redMotorcycle.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}