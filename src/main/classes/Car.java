import java.awt.*;
import java.awt.geom.AffineTransform;



enum Car_load {
    ONE_PERSON,
    TWO_PERSON,
    THREE_PERSON,
    FOUR_PERSON
}

public class Car extends Vehicles {
    private Car_load load;
    private Color color;


    
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
            return 525;
        } else {
            return 425;
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


    public static Car create_car(Road road){
        if (road.getApproach() == Approach.NORTH) {
            return new Car(Orientation.VERTICAL, 1000, 350, 5.0, 5.0, road, Car_load.ONE_PERSON);
        } else if (road.getApproach() == Approach.SOUTH) {
            return new Car(Orientation.HORIZONTAL, 0, 450, 5.0, 5.0, road, Car_load.FOUR_PERSON);
        } else if (road.getApproach() == Approach.EAST) {
            return new Car(Orientation.VERTICAL, 450, 800, 5.0, 5.0, road, Car_load.TWO_PERSON);
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
            return new Car(Orientation.VERTICAL, 400, 800, 5.0, 5.0, null, Car_load.TWO_PERSON);
        } else {
            return new Car(Orientation.VERTICAL, 450, 0, 5.0, 5.0, null, Car_load.THREE_PERSON);
        }
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Graphics2D g2d, boolean vertical) {
        // Save original transform
        AffineTransform oldTx = g2d.getTransform();

        // Compute center of the car to rotate around its center
        double cx = this.getX() + this.getwidth() / 2.0;
        double cy = this.getY() + this.getheight() / 2.0;

        // Rotate 90 degrees (π/2) when vertical; 0 when horizontal
        double angle = vertical ? Math.PI / 2.0 : 0.0;
        g2d.rotate(angle, cx, cy);

        // Draw body
        g2d.setColor(this.color != null ? this.color : Color.RED);
        g2d.fillRect((int) this.getX(), (int) this.getY(), this.getwidth(), this.getheight());

        // Draw windows
        g2d.setColor(Color.CYAN);
        g2d.fillRect((int) (this.getX() + 5), (int) (this.getY() + 5), 8, 8);
        g2d.fillRect((int) (this.getX() + this.getwidth() - 13), (int) (this.getY() + 5), 8, 8);

        // Draw wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int) (this.getX() + 5), (int) (this.getY() + this.getheight() - 8), 6, 6);
        g2d.fillOval((int) (this.getX() + this.getwidth() - 11), (int) (this.getY() + this.getheight() - 8), 6, 6);

        // Restore original transform
        g2d.setTransform(oldTx);
    }

}