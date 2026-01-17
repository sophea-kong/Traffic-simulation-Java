
enum LightState {
    RED,
    YELLOW,
    GREEN
}
public class TrafficLight {
    int x,y;
    final Approach approach;
    LightState state;
    final int greenMs, yellowMs, redMs;
    int elapsedMs = 0;
    
    public TrafficLight(int x, int y, Approach approach, LightState initialState, int greenMs, int yellowMs, int redMs) {
        this.x = x;
        this.y = y;
        this.approach = approach;
        this.state = initialState;
        this.greenMs = greenMs;
        this.yellowMs = yellowMs;
        this.redMs = redMs;
    }
}
