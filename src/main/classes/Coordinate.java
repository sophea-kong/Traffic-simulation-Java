public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return this.x;
    }

    public  double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    int compareTo(Coordinate other) {
        // comparison logic
        if (this.x != other.x) {
            return Double.compare(this.x, other.x);
        }
        return Double.compare(this.y, other.y);
    }
}
