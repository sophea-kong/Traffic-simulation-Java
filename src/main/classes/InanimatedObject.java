public class InanimatedObject extends Object {
    protected boolean isInteractable;
    protected boolean isCollidable;

    public InanimatedObject(Object object, boolean isInteractable, boolean isCollidable) {
        super(object.getHeight(), object.getWidth());
        this.isInteractable = isInteractable;
        this.isCollidable = isCollidable;
    }

    public InanimatedObject(int width, int height, boolean isInteractable, boolean isCollidable) {
        super(width, height);
        this.isInteractable = isInteractable;
        this.isCollidable = isCollidable;
    }


    public boolean isInteractable() { return isInteractable; }
    public void setInteractable(boolean interactable) { isInteractable = interactable; }
    public boolean isCollidable() { return isCollidable; }
    public void setCollidable(boolean collidable) { isCollidable = collidable; }
}
