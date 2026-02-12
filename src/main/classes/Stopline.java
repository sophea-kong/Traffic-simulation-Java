public class Stopline {
    private static int lineCount = 1;
    private int id;
    private Coordinate position;
    private Road road;

    public Stopline(Coordinate position, Road road) {
        setPosition(position);
        setRoad(road);
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
