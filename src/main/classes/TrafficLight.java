import java.awt.Graphics2D;
import java.awt.Color;

enum LightState {
    RED,
    YELLOW,
    GREEN
}
public class TrafficLight {
    private Coordinate position;
    private LightState state;
    private Road road;
    private float greenMs, yellowMs, redMs;
    private float elapsedMs = 0;

    TrafficLight(Coordinate position,Road road, LightState state, float greenMs, float yellowMs, float redMs) {
        this.position = position;
        this.road = road;
        this.state = state;
        this.greenMs = greenMs;
        this.yellowMs = yellowMs;
        this.redMs = redMs;
    }

    public void update(int deltaMs) {
        elapsedMs += deltaMs;

        switch (state) {
            case GREEN:
                if (elapsedMs >= greenMs) {
                    state = LightState.YELLOW;
                    elapsedMs = 0;
                }
                break;
            case YELLOW:
                if (elapsedMs >= yellowMs) {
                    state = LightState.RED;
                    elapsedMs = 0;
                }
                break;
            case RED:
                if (elapsedMs >= redMs) {
                    state = LightState.GREEN;
                    elapsedMs = 0;
                }
                break;
        }
    }

    public LightState getState() {
        return this.state;
    }


    public Road getRoad(){
        return this.road;
    }


    public static void drawTrafficLight(Graphics2D g2d, TrafficLight light) {
        Color lightColor = switch (light.getState()) {
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
        };
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)light.position.getX() - 15, (int)light.position.getY() - 45, 40, 100);
        
        g2d.setColor(lightColor);
        g2d.fillOval((int)light.position.getX() - 5, (int)light.position.getY() - 35 + (light.getState().ordinal() * 30), 20, 20);
    }

}
