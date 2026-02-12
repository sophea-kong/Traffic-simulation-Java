public class Coordinate {
    double x;
    double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return this.x;
    }

    double getY() {
        return this.y;
    }

    int compareTo(Coordinate other) {
        // comparison logic
        if (this.x != other.x) {
            return Double.compare(this.x, other.x);
        }
        return Double.compare(this.y, other.y);
    }
}