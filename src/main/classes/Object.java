public class Object {
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

    public Object() {
        this.height = 10;
        this.width = 10;
        this.id = Object.count++;
    }

    public Object(int height, int width) {
        setHeight(height);
        setWidth(width);
        this.id = Object.count++;
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
}
