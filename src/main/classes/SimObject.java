public class SimObject {
    protected int id;
    protected Coordinate position;
    protected int height;
    protected int width;
    static int count = 1;

    public void setPosition(Coordinate position) {
        if (position == null) {
            return;
        }
        this.position = position;
    }

    public void setX(double x) {
        this.position.setX(x);
    }

    public void setY(double y) {
        this.position.setY(y);
    }

    public void setHeight(int height) {
        if (height < 10) {
            height = 10;
        } else if (height > 200) {
            height = 200;
        }
        this.height = height;
    }

    public void setWidth(int width) {
        if (width < 10) {
            width = 10;
        } else if (width > 200) {
            width = 200;
        }
        this.width = width;
    } 

    public SimObject() {
        this.position = new Coordinate(0, 0);
        this.height = 10;
        this.width = 10;
        this.id = SimObject.count++;
    }

    public SimObject(int height, int width) {
        setHeight(height);
        setWidth(width);
        this.position = new Coordinate(0, 0);
        this.id = SimObject.count++;
    }

    public SimObject(Coordinate position, int height, int width) {
        setPosition(position);
        setHeight(height);
        setWidth(width);
        this.id = SimObject.count++;
    }

    public int getId() {
        return this.id;
    }

    public Coordinate getPosition() {
        return this.position;
    }
    
    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

}
