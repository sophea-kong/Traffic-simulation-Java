public class Stopline {
    private Coordinate position;
    private Road road;

    public Stopline(Coordinate position, Road road) {
        this.position = position;
        this.road = road;
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public Road getRoad(){
        return this.road;
    }
}
