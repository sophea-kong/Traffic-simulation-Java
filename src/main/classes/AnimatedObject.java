public class AnimatedObject extends Object{
    public AnimatedObject(Object object) {
        super(object.getPosition(),object.getHeight(), object.getWidth());
    }

    public AnimatedObject(Coordinate position, int width, int height) {
        super(position, width, height);
    }
}
