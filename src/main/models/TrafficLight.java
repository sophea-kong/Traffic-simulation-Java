
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
}
