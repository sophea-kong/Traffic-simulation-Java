// change some int to float
// create a class for object that interact with each other
// preset all vehicle type 
// seperate inanimated and animated object
// goal : have two or three object interact with each other
// goal : refactor the code
// ** explain the concept of OOP in this project more clearly **


enum LightState {
    RED,
    YELLOW,
    GREEN
}
public class TrafficLight {
    Coordinate position;
    LightState state;
    float greenMs, yellowMs, redMs;
    float elapsedMs = 0;

    TrafficLight(Coordinate position, LightState state, float greenMs, float yellowMs, float redMs) {
        this.position = position;
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
}
