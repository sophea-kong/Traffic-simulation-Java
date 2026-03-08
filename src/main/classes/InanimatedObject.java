public class InanimatedObject extends Object {
    public InanimatedObject(Object object) {
        super(object.getPosition(), object.getHeight(), object.getWidth());
    }

    public InanimatedObject(Coordinate position, int width, int height) {
        super(position, width, height);
    }

}
