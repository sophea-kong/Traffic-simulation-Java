import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel {
    private List<Road> roads = new ArrayList<>();
    private List<TrafficLight> trafficLights = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Stopline> stoplines = new ArrayList<>();

    public SimulationPanel(int windowWidth, int windowHeight) {
        setBackground(new Color(150, 150, 150)); 
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        Timer timer = new Timer(30, e -> {
            updateSimulation();
            repaint();
        });
        timer.start();
    }

    private void updateSimulation() {
        for (TrafficLight light : trafficLights) updateTrafficLight(light, 30);
        int stopDistance = 50; 

        for (Vehicle v : vehicles) {
            // Acceleration logic (moved from Vehicle.accelerate)
            double speedIncrease = 0.2;
            if (v.getCurspeed() >= v.getSpeed()) speedIncrease = 0.0;
            v.setCurspeed(v.getCurspeed() + speedIncrease);

            boolean stop = false;
            TrafficLight matchedLight = obeyLight(v, trafficLights);
            Stopline matchedLine = obeyLine(v, stoplines);

            // Adhere to traffic lights only if not already in the middle of a turn
            if (!v.hasTurned() && matchedLight != null && matchedLine != null && matchedLight.getState() == LightState.RED) {
                double distance = distanceToPoint(v, matchedLine.getPosition().getX(), matchedLine.getPosition().getY());
                if (distance <= stopDistance) stop = true;
            }

            if (!stop) {
                for (Vehicle other : vehicles) {
                    if (v == other) continue;
                    // Proximity check on same road
                    if (v.getRoad().getApproach() == other.getRoad().getApproach() && v.getPosition()!=null && other.getPosition()!=null) {
                        if (isBehind(v, other) && distanceToVehicles(v, other) < 80) {
                            stop = true;
                            break;
                        }
                    }
                }
            }

            if (stop) v.setCurspeed(0);
            else {
                // We already applied acceleration above, but if it was stopped it might need to recover previousSpeed?
                // Actually the old logic was: if (stop) v.setCurspeed(0); else v.setCurspeed(v.getPreviousSpeed());
                // Let's stick closer to the old logic for behavior consistency but with the "dumb" object structure.
                if (v.getCurspeed() == 0) v.setCurspeed(0.2); // Start moving again
            }

            // Turning Trigger: Check if we are inside the intersection box
            if (!v.hasTurned() && v.getTurnDirection() != TurnDirection.STRAIGHT) {
                // Intersection area is roughly 400-600 in X and 300-500 in Y
                if (v.getX() > 420 && v.getX() < 580 && v.getY() > 320 && v.getY() < 480) {
                    performTurn(v);
                }
            }

            moveVehicle(v, 1000, 800);
        }
    }

    private void updateTrafficLight(TrafficLight light, int deltaMs) {
        float elapsedMs = light.getElapsedMs() + deltaMs;
        light.setElapsedMs(elapsedMs);

        switch (light.getState()) {
            case GREEN:
                if (elapsedMs >= light.getGreenMs()) {
                    light.setState(LightState.YELLOW);
                    light.setElapsedMs(0);
                }
                break;
            case YELLOW:
                if (elapsedMs >= light.getYellowMs()) {
                    light.setState(LightState.RED);
                    light.setElapsedMs(0);
                }
                break;
            case RED:
                if (elapsedMs >= light.getRedMs()) {
                    light.setState(LightState.GREEN);
                    light.setElapsedMs(0);
                }
                break;
        }
    }

    private void moveVehicle(Vehicle v, int windowsWidth, int windowHeight) {
        Approach approach = v.getRoad().getApproach();
        double curspeed = v.getCurspeed();

        // Standard forward movement
        if (approach == Approach.SOUTH) v.setX(v.getX() + curspeed);
        else if (approach == Approach.NORTH) v.setX(v.getX() - curspeed);
        else if (approach == Approach.EAST) v.setY(v.getY() + curspeed);
        else if (approach == Approach.WEST) v.setY(v.getY() - curspeed);

        // Smooth coordinate shift during turning
        if (v.isTurning()) {
            double shiftSpeed = 3.0;
            double turnTargetCoord = v.getTurnTargetCoord();
            if (v.getOrientation() == Orientation.VERTICAL) {
                if (Math.abs(v.getX() - turnTargetCoord) < shiftSpeed) {
                    v.setX(turnTargetCoord);
                    v.setTurning(false);
                } else {
                    v.setX(v.getX() + (v.getX() < turnTargetCoord ? shiftSpeed : -shiftSpeed));
                }
            } else {
                if (Math.abs(v.getY() - turnTargetCoord) < shiftSpeed) {
                    v.setY(turnTargetCoord);
                    v.setTurning(false);
                } else {
                    v.setY(v.getY() + (v.getY() < turnTargetCoord ? shiftSpeed : -shiftSpeed));
                }
            }
        }

        // Wrap around / Reset
        if (v.getX() > windowsWidth + 100 || v.getX() < -200 || 
            v.getY() > windowHeight + 100 || v.getY() < -200) {
            resetVehicle(v);
        }
    }

    private void resetVehicle(Vehicle v) {
        v.setX(v.getSpawnPosition().getX());
        v.setY(v.getSpawnPosition().getY());
        v.setRoad(v.getOriginalRoad());
        v.setOrientation((v.getRoad().getId() == 1 || v.getRoad().getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
        v.setHasTurned(false);
        v.setTurning(false);
        v.setTurnDirection(TurnDirection.values()[new java.util.Random().nextInt(3)]);
    }

    private double distanceToPoint(Vehicle v, double x, double y) {
        return Math.hypot(x - v.getX(), y - v.getY());
    }

    private double distanceToVehicles(Vehicle v1, Vehicle v2) {
        return Math.hypot(v2.getX() - v1.getX(), v2.getY() - v1.getY());
    }

    private boolean isBehind(Vehicle v, Vehicle other) {
        if (v.getRoad() == null || other.getRoad() == null) return false;
        Approach approach = v.getRoad().getApproach();
        if (approach == Approach.SOUTH) return other.getX() > v.getX();
        else if (approach == Approach.NORTH) return other.getX() < v.getX();
        else if (approach == Approach.EAST) return other.getY() > v.getY();
        else if (approach == Approach.WEST) return other.getY() < v.getY();
        return false;
    }

    private TrafficLight obeyLight(Vehicle v, List<TrafficLight> all_TrafficLight) {
        if (v.getRoad() == null) return null;
        for (TrafficLight light : all_TrafficLight) {
            if (light.getRoad().getApproach() == v.getRoad().getApproach()) return light;
        }
        return null;
    }

    private Stopline obeyLine(Vehicle v, List<Stopline> all_line) {
        if (v.getRoad() == null) return null;
        for (Stopline line : all_line) {
            if (line.getRoad().getApproach() == v.getRoad().getApproach()) return line;
        }
        return null;
    }

    private void performTurn(Vehicle v) {
        // need refactor to be more elegant but it works for now
        Approach currentApp = v.getRoad().getApproach();
        TurnDirection dir = v.getTurnDirection();
        
        Road targetRoad = null;
        double targetCoord = 0;

        if (currentApp == Approach.SOUTH) {
            if (dir == TurnDirection.LEFT) { targetRoad = findRoadByApproach(Approach.WEST); targetCoord = 550; }
            else { targetRoad = findRoadByApproach(Approach.EAST); targetCoord = 450; }
        } else if (currentApp == Approach.NORTH) {
            if (dir == TurnDirection.LEFT) { targetRoad = findRoadByApproach(Approach.EAST); targetCoord = 450; }
            else { targetRoad = findRoadByApproach(Approach.WEST); targetCoord = 550; }
        } else if (currentApp == Approach.WEST) {
            if (dir == TurnDirection.LEFT) { targetRoad = findRoadByApproach(Approach.NORTH); targetCoord = 350; }
            else { targetRoad = findRoadByApproach(Approach.SOUTH); targetCoord = 450; }
        } else if (currentApp == Approach.EAST) {
            if (dir == TurnDirection.LEFT) { targetRoad = findRoadByApproach(Approach.SOUTH); targetCoord = 450; }
            else { targetRoad = findRoadByApproach(Approach.NORTH); targetCoord = 350; }
        }

        if (targetRoad != null) {
            v.setRoad(targetRoad);
            v.setOrientation((targetRoad.getApproach() == Approach.NORTH || targetRoad.getApproach() == Approach.SOUTH) 
                               ? Orientation.HORIZONTAL : Orientation.VERTICAL);
            v.setTurnTargetCoord(targetCoord);
            v.setTurning(true);
            v.setHasTurned(true);
        }
    }

    private Road findRoadByApproach(Approach app) {
        for (Road r : roads) {
            if (r.getApproach() == app) return r;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Road road : roads) road.render(g2d);
        for (TrafficLight light : trafficLights) light.render(g2d);
        for (Vehicle v : vehicles) v.render(g2d);
    }
    void addRoad(Road road) { 
        roads.add(road); 
    }
    void addTrafficLight(TrafficLight light) { trafficLights.add(light); }
    void addVehicle(Vehicle vehicle) { vehicles.add(vehicle); }
    void addstopline(Stopline line) { stoplines.add(line); }
}