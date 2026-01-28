public class Coordinate {
    public float x;
    public float y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // move the vehicle by dx and dy to avoid changing position of (x,y) directly
    public void translate(float dx, float dy) {
        this.x += dx; // changes in x
        this.y += dy; // changes in y
    }

    // check if two coordinates are the same
    public boolean equals(Coordinate other) {
        return this.x == other.x && this.y == other.y;
    }

// -- stuffs interactivity methods --

    // cal the distance between two points
    public float distanceTo(Coordinate other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy); // pythagorean theorem to find distance
    }

    // check if close enough (for stopping or switching lights)
    public boolean isNear(Coordinate other, float threshold) {
        return distanceTo(other) <= threshold;
    }
}
