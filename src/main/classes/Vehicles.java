import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;

enum TurnType {
    STRAIGHT,
    LEFT_TURN,
    RIGHT_TURN
}

enum MovementState {
    STRAIGHT,
    TURNING,
    AFTER_TURN
}

public class Vehicles {
    // private static int vehicleCount = 1;
    private int vehicleId;
    private Coordinate position;
    private int width;
    private int height;
    private double speed;
    private double curspeed;
    protected double previousSpeed;
    private Road road;
    private Orientation orientation;

    protected MovementState state;
    protected Approach entry;
    protected Approach exit;
    protected TurnType turnType;

    protected double turn_angle;
    protected double turnStart;
    protected double turn_end;
    protected int turnDir;
    protected boolean isTurning = false;
    protected Coordinate intersection;

    Vehicles (Orientation orientation, int x, int y, int width, int height, double speed,double curspeed, Road road, Approach exit) {
        this.orientation = orientation;
        this.position = new Coordinate(x, y);
        this.width = width;
        this.height = height;
        this.speed = validate_speed(speed);
        this.previousSpeed = this.speed;
        this.curspeed = curspeed;
        this.road = road;

        this.entry = road.approach;
        this.exit = exit;
        this.turnType = determineTurn(this.entry, this.exit);
        this.state = MovementState.STRAIGHT;
    }

    public void move(int windowsWidth, int windowHeight, Orientation orientation, Approach approach) {
        this.curspeed += accelerate();
        if (this.curspeed > this.speed) {
            this.curspeed = this.speed;
        }

        if(state == MovementState.STRAIGHT) {
            moveStraight(entry);
            if(shouldStartTurn()) {
                state = MovementState.TURNING;
                prepareTurn();
            }
        } else if (state == MovementState.TURNING) {
            updateTurn();
            if (!isTurning) {
                state = MovementState.AFTER_TURN;
                entry = exit;
            }
        } else if (state == MovementState.AFTER_TURN) {
            moveStraight(entry);
        }
    }

    double accelerate() {
        // calculate how much to increase speed
        return 0;
    }

    void breaking() {
        // breaking logic
    }

    public int getVehicleId() {
        return this.vehicleId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public double getCurspeed() {
        return curspeed;
    }

    public double getPreviousSpeed() {
        return this.previousSpeed;
    }

    private double validate_speed(double pspeed) {
        if (pspeed < 0) {
            pspeed = -pspeed;
        }
        // speed limit
        if (pspeed > 200) {
            pspeed = 199;
        }
        return pspeed;
    }

    public void setCurspeed(double curspeed) {
        if(curspeed > speed) {
            curspeed = speed;
        } else if (curspeed < 0) {
            curspeed = 0;
        }
        this.curspeed = curspeed;
    }

    public int getwidth() {
        return width;
    }

    public int getheight() {
        return height;
    }

    public Road getRoad() {
        return road;
    }

    public void stop() {
        this.curspeed = 0;
    }


    public double getSpeed() {
        return this.speed;
    }

    public double getX() {
        return this.position.getX();
    }

    public void setX(double x) {
        if (x < 0 && this.road.getApproach() == Approach.NORTH) {
            x = 1000;
        }
        if (x > 1000) {
            x = 0;
        }
        this.position.setX(x);
    }

    public double getY() {
        return this.position.getY();
    }

    public void setY(double y) {
        if (y < 1) {
            y = 800;
        } else if (y > 800) {
            y = 0;
        }
        this.position.setY(y);
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setSpeed(double pspeed) {
        if (pspeed < 0) {
            pspeed = -pspeed;
        }
        // speed limit
        if (pspeed > 200) {
            pspeed = 199;
        }
        this.speed = pspeed;
    }

    public TrafficLight obeyLight(List<TrafficLight> all_TrafficLight) {
        if (this.road == null)
            return null;
        for (TrafficLight light : all_TrafficLight) {
            if (light == null)
                continue;
            Road lroad = light.getRoad();
            if (lroad == null)
                continue;
            // match either by the same Road instance or by the same Approach
            if (this.road == lroad || this.road.getApproach() == lroad.getApproach()) {
                return light;
            }
        }
        return null;
    }

    public Stopline obeyLine(List<Stopline> all_line) {
        for (Stopline line : all_line) {
            if (line.getRoad().getApproach() == this.road.getApproach()) {
                return line;
            }
        }
        return null;
    }




    public static void drawVehicle(Graphics2D g2d, Vehicles v) {
        g2d.setColor(Color.RED);
        g2d.fillRect((int)v.getX(), (int)v.getY(), v.width, v.height);
        
        // Draw windows
        g2d.setColor(Color.CYAN);
        g2d.fillRect((int)(v.getX() + 5), (int)(v.getY() + 5), 8, 8);
        g2d.fillRect((int)(v.getX() + v.width - 13), (int)(v.getY() + 5), 8, 8);
        
        // Draw wheels
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(v.getX() + 5), (int)(v.getY() + v.height - 8), 6, 6);
        g2d.fillOval((int)(v.getX() + v.width - 11), (int)(v.getY() + v.height - 8), 6, 6);
    }


    public void prepareTurn() {
        Coordinate turnCenter = new Coordinate(0, 0);
        double startAngle = 0, endAngle = 0;

        if(turnType == TurnType.LEFT_TURN) {
            turnCenter = road.leftTurn;
            if(entry == Approach.NORTH) {
                startAngle = 0;
                endAngle = Math.PI / 2;
            } else if(entry == Approach.SOUTH) {
                startAngle = -Math.PI;
                endAngle = 3 * Math.PI / 2;
            } else if(entry == Approach.EAST) {
                startAngle = 3 * Math.PI / 2;
                endAngle = 2 * Math.PI;
            } else if(entry == Approach.WEST) {
                startAngle = Math.PI / 2;
                endAngle = Math.PI;
            }
        }else if (turnType == TurnType.RIGHT_TURN) {
            turnCenter = road.rightTurn;
            if(entry == Approach.NORTH) {
                startAngle = Math.PI;
                endAngle = Math.PI / 2;
            } else if(entry == Approach.SOUTH) {
                startAngle = 2 * Math.PI;
                endAngle = 3 * Math.PI / 2;
            } else if(entry == Approach.EAST) {
                startAngle = Math.PI / 2;
                endAngle = 0;
            } else if(entry == Approach.WEST) {
                startAngle = 3 * Math.PI / 2;
                endAngle = Math.PI;
            }
        }

        startTurn(startAngle, endAngle, turnCenter.getX(), turnCenter.getY());
    }


    public TurnType determineTurn(Approach entry, Approach exit) {
        if(entry == exit) {
            turnType = TurnType.STRAIGHT;
            return turnType;
        }

        if ((entry == Approach.NORTH && exit == Approach.WEST) ||
            (entry == Approach.WEST && exit == Approach.SOUTH) ||
            (entry == Approach.SOUTH && exit == Approach.EAST) ||
            (entry == Approach.EAST && exit == Approach.NORTH)) {
            turnType = TurnType.LEFT_TURN;
        } else if ((entry == Approach.NORTH && exit == Approach.EAST) ||
                   (entry == Approach.EAST && exit == Approach.SOUTH) ||
                   (entry == Approach.SOUTH && exit == Approach.WEST) ||
                   (entry == Approach.WEST && exit == Approach.NORTH)) {
            turnType = TurnType.RIGHT_TURN;
        } else {
            turnType = TurnType.STRAIGHT; // default to straight if no match
        }
        return turnType;
    }

    public void update(List<TrafficLight> lights, List<Stopline> stoplines, int windowWidth, int windowHeight) {
        boolean stop = false;
        int stopDistance = 100;

        for (TrafficLight light : lights) {
            if (shouldStopAtLight(light)) {
                Stopline slLine = obeyLine(stoplines);
                if (slLine != null) {
                    Coordinate sl = slLine.getPosition();
                    double dx = sl.getX() - getX();
                    double dy = sl.getY() - getY();
                    double distance = Math.hypot(dx, dy);

                    if (distance <= stopDistance) {
                        stop = true;
                        break;
                    }
                }
            }
        }

        if (stop) {
            setSpeed(0);
        } else {
            setSpeed(getPreviousSpeed());
            // If it was stopped, we might need a kickstart or just rely on move()
        }

        move(windowWidth, windowHeight, getOrientation(), this.exit);
    }


    protected boolean shouldStopAtLight(TrafficLight light) {
        return light.getState() == LightState.RED && obeyLight(java.util.Collections.singletonList(light)) == light;
    }

    void moveStraight(Approach approach) {
        if (approach == Approach.SOUTH) {
            position.setY(getY() + this.curspeed);            
        } else if (approach == Approach.NORTH) {
            position.setY(getY() - this.curspeed);
        } else if (approach == Approach.EAST) {
            position.setX(getX() + this.curspeed);
        } else if (approach == Approach.WEST) {
            position.setX(getX() - this.curspeed);
        }
    }

    boolean shouldStartTurn() {
        if (this.turnType == TurnType.STRAIGHT) {
            return false;
        }

        if(this.road.approach == Approach.EAST) {
            if(this.position.getX() >= road.turn.getX()) {
                return true;
            }
        }else if(this.road.approach == Approach.WEST) {
            if(this.position.getX() <= road.turn.getX()) {
                return true;
            }
        }else if(this.road.approach == Approach.NORTH) {
            if(this.position.getY() <= road.turn.getY()) {
                return true;
            }
        }else if(this.road.approach == Approach.SOUTH) {
            if(this.position.getY() >= road.turn.getY()) {
                return true;
            }
        }

        return false;
    }

    void startTurn(double startAngle, double endAngle, double cx, double cy) {
        this.isTurning = true;
        this.turn_angle = startAngle;
        this.turn_end = endAngle;
        this.intersection = new Coordinate(cx, cy);
        this.turn_angle = startAngle;

        if (endAngle > startAngle) {
            this.turnDir = 1; // counterclockwise
        } else {
            this.turnDir = -1; // clockwise
        }
    }


    void updateTurn() {
        if(!isTurning) return;

        double turnRadius = 0;
        if(turnType == TurnType.LEFT_TURN) {
            turnRadius = road.leftTurnRadius;
        } else if(turnType == TurnType.RIGHT_TURN) {
            turnRadius = road.rightTurnRadius;
        }

        turn_angle += turnDir * (getCurspeed() / turnRadius);
        
        if ((turnDir == 1 && turn_angle >= turn_end) || (turnDir == -1 && turn_angle <= turn_end)) {
            turn_angle = turn_end;
            isTurning = false;
        }
        
        double cx = intersection.getX();
        double cy = intersection.getY();


        position.setX(cx + turnRadius * Math.cos(turn_angle));
        position.setY(cy - turnRadius * Math.sin(turn_angle));
    }

}