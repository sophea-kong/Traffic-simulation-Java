public class Stopline {
    public static int count = 1;
    private int id;
    private Coordinate position;
    private Road road;
    // private Orientation orientation;
    //int roadId;

    public Stopline(Coordinate position, Road road) {
        setPosition(position);
        setRoad(road);
        this.id = Stopline.count++;
    }

    private void setPosition(Coordinate position) {
        if (position == null) {
            return;
        }
        this.position = position;
    }

    private void setRoad(Road road) {
        if (road == null) {
            return;
        }
        this.road = road;
        //this.roadId = road.id;
    }
    public Stopline(Coordinate position, int roadId) {
        this.position = position;
        this.road = null;
        //this.roadId = roadId;
    }
    public Stopline(Road road) {
        this.position = (road.getId() == 1) ? new Coordinate(100, 400) :
                        (road.getId() == 2) ? new Coordinate(900, 800) :
                        (road.getId() == 3) ? new Coordinate(500, 750) :
                                        new Coordinate(500, 50);
        this.road = road;
        //this.roadId = road.id;
    }  

    public Coordinate getPosition() {
        return this.position;
    }

    public Road getRoad(){
        return this.road;
    }
    public int getId() {
        return this.id;
    }
}
