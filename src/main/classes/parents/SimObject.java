package parents;



public abstract class SimObject {
    protected int id;
    protected int height;
    protected int width;
    static int count = 1;

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
        this.height = 10;
        this.width = 10;
        this.id = SimObject.count++;
    }

    public SimObject(int height, int width) {
        setHeight(height);
        setWidth(width);
        this.id = SimObject.count++;
    }

    public int getId() {
        return this.id;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimObject simObject = (SimObject) o;
        return id == simObject.id && height == simObject.height && width == simObject.width;
    }

    @Override
    public String toString() {
        return "SimObject{" +
                "id=" + id +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
