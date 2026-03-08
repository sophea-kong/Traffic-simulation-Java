public class Stopline extends InanimatedObject {
    public static int count = 1;
    private Road road;

    public Stopline(Coordinate position, Road road) {
        super(position, 0, 0);
        this.road = road;
    }

    public Stopline(Coordinate position, int roadId) {
        super(position, 0, 0);
        this.road = null;
    }

    public Stopline(Road road) {
        super(null, 0, 0);
        this.position = (road.getId() == 1) ? new Coordinate(100, 400) :
                        (road.getId() == 2) ? new Coordinate(900, 800) :
                        (road.getId() == 3) ? new Coordinate(500, 750) :
                                        new Coordinate(500, 50);
        this.road = road;
    }  

    public Road getRoad(){
        return this.road;
    }
}
