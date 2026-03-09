public class InanimatedObject extends SimObject {
    public InanimatedObject(SimObject object) {
        super(object.getPosition(), object.getHeight(), object.getWidth());
    }

    public InanimatedObject(Coordinate position, int width, int height) {
        super(position, width, height);
    }

}
