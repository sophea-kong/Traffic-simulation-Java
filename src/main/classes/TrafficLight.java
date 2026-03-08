// import java.awt.Graphics2D;
// import java.awt.Color;

// enum LightState {
//     RED,
//     YELLOW,
//     GREEN
// }
// public class TrafficLight implements Renderable,Updatable {
//     private static int lightCount = 1;
//     private int id;
//     private Coordinate position;
//     private LightState state;
//     private Road road;
//     private float greenMs, yellowMs, redMs;
//     private float elapsedMs = 0;

//     TrafficLight(Coordinate position,Road road, LightState state, float greenMs, float yellowMs, float redMs) {
//         setPosition(position);
//         setRoad(road);
//         this.state = state;
//         this.greenMs = validateMs(greenMs);
//         this.yellowMs = validateMs(yellowMs);
//         this.redMs = validateMs(redMs);
//         this.id = lightCount++;
//     }

//     TrafficLight(Road road, LightState state, float greenMs, float yellowMs, float redMs) {
//         this.position = (road.getId() == 1) ? new Coordinate(500.0, 450.0) :
//                         (road.getId() == 2) ? new Coordinate(600.0, 400.0) :
//                         (road.getId() == 3) ? new Coordinate(500.0, 750.0) :
//                                         new Coordinate(500.0, 50.0);
//         setRoad(road);
//         this.state = state;
//         this.greenMs = validateMs(greenMs);
//         this.yellowMs = validateMs(yellowMs);
//         this.redMs = validateMs(redMs);
//         this.id = lightCount++;
//     }

//     TrafficLight(Road raod, LightState state) {
//         this.position = (raod.getId() == 1) ? new Coordinate(500.0, 450.0) :
//                         (raod.getId() == 2) ? new Coordinate(600.0, 400.0) :
//                         (raod.getId() == 3) ? new Coordinate(500.0, 750.0) :
//                                         new Coordinate(500.0, 50.0);
//         setRoad(raod);
//         this.state = state;
//         this.greenMs = 5000;
//         this.yellowMs = 2000;
//         this.redMs = 5000;
//         this.id = lightCount++;
//     }

//     TrafficLight(int RoadId){
//         this.position = (RoadId == 1) ? new Coordinate(500.0, 450.0) :
//                         (RoadId == 2) ? new Coordinate(600.0, 400.0) :
//                         (RoadId == 3) ? new Coordinate(500.0, 750.0) :
//                                         new Coordinate(500.0, 50.0);
//         this.road = null;
//         this.state = LightState.RED;
//         this.greenMs = 5000;
//         this.yellowMs = 2000;
//         this.redMs = 5000;
//         this.id = lightCount++;
//     }

//     private void setPosition(Coordinate position) {
//         if(position == null) {
//             return;
//         }
//         this.position = position;
//     }

//     private void setRoad(Road road) {
//         if (road == null) {
//             return;
//         }
//         this.road = road;
//     }

//     private float validateMs(float Ms){
//         if (Ms < 0) {
//             Ms = 0;
//         } else if (Ms > 10000) {
//             Ms = 10000;
//         }
//         return Ms;
//     }


//     public int getId() {
//         return this.id;
//     }


//    public void update(int deltaMs) {
//         elapsedMs += deltaMs;

//         switch (state) {
//             case GREEN:
//                 if (elapsedMs >= greenMs) {
//                     state = LightState.YELLOW;
//                     elapsedMs = 0;
//                 }
//                 break;
//             case YELLOW:
//                 if (elapsedMs >= yellowMs) {
//                     state = LightState.RED;
//                     elapsedMs = 0;
//                 }
//                 break;
//             case RED:
//                 if (elapsedMs >= redMs) {
//                     state = LightState.GREEN;
//                     elapsedMs = 0;
//                 }
//                 break;
//         }
//     }

//     public LightState getState() {
//         return this.state;
//     }


//     public Road getRoad(){
//         return this.road;
//     }

//     @Override
//     public void render(Graphics2D g2d, boolean vertical) {
//         TrafficLight light = this;
//         Color lightColor = switch (light.getState()) {
//             case RED -> Color.RED;
//             case YELLOW -> Color.YELLOW;
//             case GREEN -> Color.GREEN;
//         };
        
//         g2d.setColor(Color.BLACK);
//         g2d.fillRect((int)light.position.getX() - 15, (int)light.position.getY() - 45, 40, 100);
        
//         g2d.setColor(lightColor);
//         g2d.fillOval((int)light.position.getX() - 5, (int)light.position.getY() - 35 + (light.getState().ordinal() * 30), 20, 20);
//     }

// }





import java.awt.Graphics2D;
import java.awt.Color;

enum LightState {
    RED,
    YELLOW,
    GREEN
}

public class TrafficLight extends AnimatedObject implements Updatable, Renderable {
    private LightState state;
    private Road road;
    private float greenMs, yellowMs, redMs;
    private float elapsedMs = 0;

    TrafficLight(Coordinate position, Road road, LightState state, float greenMs, float yellowMs, float redMs) {
        super(position, 40, 100);
        setRoad(road);
        this.state = state;
        this.greenMs = validateMs(greenMs);
        this.yellowMs = validateMs(yellowMs);
        this.redMs = validateMs(redMs);
    }

    TrafficLight(Road road, LightState state, float greenMs, float yellowMs, float redMs) {
        super(null, 40, 100);
        this.position = (road.getId() == 1) ? new Coordinate(500.0, 450.0) :
                        (road.getId() == 2) ? new Coordinate(600.0, 400.0) :
                        (road.getId() == 3) ? new Coordinate(500.0, 750.0) :
                                        new Coordinate(500.0, 50.0);
        setRoad(road);
        this.state = state;
        this.greenMs = validateMs(greenMs);
        this.yellowMs = validateMs(yellowMs);
        this.redMs = validateMs(redMs);
    }

    TrafficLight(Road road, LightState state) {
        super(null, 40, 100);
        this.position = (road.getId() == 1) ? new Coordinate(500.0, 450.0) :
                        (road.getId() == 2) ? new Coordinate(600.0, 400.0) :
                        (road.getId() == 3) ? new Coordinate(500.0, 750.0) :
                                        new Coordinate(500.0, 50.0);
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


    public void render(Graphics2D g2d, boolean vertical) {
        Color lightColor = switch (this.getState()) {
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
        };
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)this.position.getX() - 15, (int)this.position.getY() - 45, 40, 100);
        
        g2d.setColor(lightColor);
        g2d.fillOval((int)this.position.getX() - 5, (int)this.position.getY() - 35 + (this.getState().ordinal() * 30), 20, 20);
    }
}
