public class Stopline {
    Coordinate position;
    Orientation orientation;
    Road road;
    //int roadId;

    public Stopline(Coordinate position, Orientation orientation, Road road) {
        this.position = position;
        this.orientation = orientation;
        this.road = road;
        //this.roadId = road.id;
    }
    public Stopline(Coordinate position, int roadId) {
        this.position = position;
        this.orientation = (roadId == 1 || roadId == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.road = null;
        //this.roadId = roadId;
    }
    public Stopline(Road road) {
        this.position = (road.id == 1) ? new Coordinate(100, 400) :
                        (road.id == 2) ? new Coordinate(900, 800) :
                        (road.id == 3) ? new Coordinate(500, 750) :
                                        new Coordinate(500, 50);
        this.orientation = (road.id == 1 || road.id == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.road = road;
        //this.roadId = road.id;
    }  

    public Coordinate getPosition() {
        return this.position;
    }

    public Road getRoad(){
        return this.road;
    }
}
