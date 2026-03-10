import java.awt.Graphics2D;

public interface Renderable {
    // instance render method to be implemented by drawable objects
    public void render(Graphics2D g, boolean vertical, Coordinate pos);
}
