public class Stopline {
    Coordinate position;
    Orientation orientation;
    Road road;

    public Stopline(Coordinate position, Orientation orientation, Road road) {
        this.position = position;
        this.orientation = orientation;
        this.road = road;
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public Road getRoad(){
        return this.road;
    }
}
