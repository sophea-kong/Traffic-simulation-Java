package parents;
public abstract class InanimatedObject extends SimObject {
    protected boolean isInteractable;
    protected boolean isCollidable;

    public InanimatedObject(SimObject object, boolean isInteractable, boolean isCollidable) {
        super(object.getHeight(), object.getWidth());
        this.isInteractable = isInteractable;
        this.isCollidable = isCollidable;
    }

    // Standardized to (height, width) to match SimObject
    public InanimatedObject(int height, int width, boolean isInteractable, boolean isCollidable) {
        super(height, width);
        this.isInteractable = isInteractable;
        this.isCollidable = isCollidable;
    }


    public boolean isInteractable() { return isInteractable; }
    public void setInteractable(boolean interactable) { isInteractable = interactable; }
    public boolean isCollidable() { return isCollidable; }
    public void setCollidable(boolean collidable) { isCollidable = collidable; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InanimatedObject that = (InanimatedObject) o;
        return isInteractable == that.isInteractable && isCollidable == that.isCollidable;
    }

    @Override
    public String toString() {
        return "InanimatedObject{" +
                "id=" + id +
                ", height=" + height +
                ", width=" + width +
                ", isInteractable=" + isInteractable +
                ", isCollidable=" + isCollidable +
                '}';
    }
}
