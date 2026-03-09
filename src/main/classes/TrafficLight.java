import java.awt.Graphics2D;
import java.awt.Color;

enum LightState {
    RED,
    YELLOW,
    GREEN
}

public class TrafficLight extends InanimatedObject implements Updatable, Renderable {
    private LightState state;
    private Road road;
    private float greenMs, yellowMs, redMs;
    private float elapsedMs = 0;

    TrafficLight(Road road, LightState state, float greenMs, float yellowMs, float redMs) {
        super(40, 100, true, false);
        setRoad(road);
        this.state = state;
        this.greenMs = validateMs(greenMs);
        this.yellowMs = validateMs(yellowMs);
        this.redMs = validateMs(redMs);
    }

    TrafficLight(Road road, LightState state) {
        super(40, 100, true, false);
        setRoad(road);
        this.state = state;
        this.greenMs = 5000;
        this.yellowMs = 2000;
        this.redMs = 5000;
    }

    private void setRoad(Road road) {
        if (road == null) {
            return;
        }
        this.road = road;
    }

    private float validateMs(float Ms){
        if (Ms < 0) {
            Ms = 0;
        } else if (Ms > 10000) {
            Ms = 10000;
        }
        return Ms;
    }

    public void setState(LightState state) { this.state = state; }
    public LightState getState() { return this.state; }
    public Road getRoad(){ return this.road; }
    public float getGreenMs() { return greenMs; }
    public float getYellowMs() { return yellowMs; }
    public float getRedMs() { return redMs; }
    public float getElapsedMs() { return elapsedMs; }
    public void setElapsedMs(float elapsedMs) { this.elapsedMs = elapsedMs; }

    public void update(int deltaMs) {
        
    }


    @Override
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        Color lightColor = switch (this.getState()) {
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
        };
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)pos.getX() - 15, (int)pos.getY() - 45, 40, 100);
        
        g2d.setColor(lightColor);
        g2d.fillOval((int)pos.getX() - 5, (int)pos.getY() - 35 + (this.getState().ordinal() * 30), 20, 20);
    }
}
