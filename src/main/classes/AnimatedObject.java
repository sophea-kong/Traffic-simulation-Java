public class AnimatedObject extends SimObject{
    public AnimatedObject(SimObject object) {
        super(object.getPosition(),object.getHeight(), object.getWidth());
    }

    public AnimatedObject(Coordinate position, int width, int height) {
        super(position, width, height);
    }
}
