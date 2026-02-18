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
        if (x < 0) {
            x = 0;
        } else if (x > 1000) {
            x = 1000;
        }
        this.x = x;
    }

    public void setY(double y) {
        if(y < 0){
            y = 0;
        } else if ( y > 1000){
            y = 1000;
        }
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
