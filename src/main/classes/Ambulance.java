import javax.imageio.ImageIO;
import java.io.IOException;

public class Ambulance extends Vehicle {

    public Ambulance(Orientation orientation, Approach approach, int x, int y, double speed, double curspeed, Road road) {
        super(orientation, approach, x, y, 60, 70, speed, curspeed, road); 
        loadSprite();
    }

    @Override
    public void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResource("/images/emergency/ambulance.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
