package view;

import parents.*;
import children.*;
import exceptions.InvalidInputException;
import utils.*;
import interfaces.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulationPanel extends JPanel {
    private Map<Road, Coordinate> roads = new LinkedHashMap<>();
    private Map<TrafficLight, Coordinate> trafficLights = new LinkedHashMap<>();
    private Map<Vehicle, Coordinate> vehicles = new LinkedHashMap<>();
    private Map<Vehicle, Coordinate> vehicleSpawns = new LinkedHashMap<>();
    private Map<Stopline, Coordinate> stoplines = new LinkedHashMap<>();

    private int frameCounter = 0;
    private String selectedVehicle = "Car";
    private Car_load selectedCarLoad = Car_load.ONE_PERSON;
    private double speedValue = 1.0;
    private int laneEntryValue = 1;
    private Orientation laneEntryOrientation = Orientation.HORIZONTAL;
    private Road roadSelected; // temporary variable to store the road for new vehicle
    private Coordinate spawnPos = new Coordinate(0, 0);

    public SimulationPanel(int windowWidth, int windowHeight) {
        setBackground(new Color(150, 150, 150));
        setPreferredSize(new Dimension(windowWidth, windowHeight));

        // lamda expression on timer
        Timer timer = new Timer(30, e -> {
            updateSimulation();
            repaint();

            frameCounter++;
            if (frameCounter % 100 == 0) {
                // anonymous Inner Class
                VehicleFilter emergencyFilter = new VehicleFilter() {
                    @Override
                    public boolean filter(Vehicle v) {
                        // return if the vehicle is emergency and is in the range of panel
                        return v.isEmergency() && vehicles.get(v).getX() <= 1000
                                && vehicles.get(v).getY() > 0 && vehicles.get(v).getY() <= 900;
                    }
                };
                int emergencyCount = countFilteredVehicles(emergencyFilter);

                // Lambda Expression
                int carCount = countFilteredVehicles(v -> (v instanceof Car) && vehicles.get(v).getX() <= 1000
                        && vehicles.get(v).getY() > 0 && vehicles.get(v).getY() <= 900);

                System.out.println(
                        "Stats [Frame " + frameCounter + "]: Emergency: " + emergencyCount + ", Cars: " + carCount);
                // print every vehicle position and type and spped

                for (Road r : roads.keySet()) {
                    System.out.println("Road " + r.getApproach() + " stats: Inner: " + r.getInnerLaneVehicleCount()
                            + ", Outer: " + r.getOuterLaneVehicleCount());
                }
            }
        });

        JButton button = new JButton("Pause");

        // lambda expression on button
        button.addActionListener(e -> {
            if (timer.isRunning()) {
                timer.stop();
                button.setText("Resume");
            } else {
                timer.start();
                button.setText("Pause");
            }
        });
        add(button);

        // speed lane entry and exit
        JTextField speed = new JTextField(10);
        speed.setText("set speed");
        speed.addActionListener(e -> {
            try {
                speedValue = Double.parseDouble(speed.getText());
                if (speedValue < 0) {
                    throw new IllegalArgumentException("Speed value cannot be negative.");
                }
            } catch (NumberFormatException Ne) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for speed.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException Ie) {
                JOptionPane.showMessageDialog(this, Ie.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(speed);

        JTextField laneEntry = new JTextField(10);
        laneEntry.setText("set lane (1-4)");
        laneEntry.addActionListener(e -> {
            try {
                laneEntryValue = Integer.parseInt(laneEntry.getText());
                if (laneEntryValue < 1 || laneEntryValue > 4) {
                    throw new IllegalArgumentException("Lane entry value must be between 1 and 4.");
                }

                // Horizontal for lane 1 and 2, vertical for lane 3 and 4
                if (laneEntryValue == 1 || laneEntryValue == 2) {
                    laneEntryOrientation = Orientation.HORIZONTAL;
                } else {
                    laneEntryOrientation = Orientation.VERTICAL;
                }

                // road
                if (laneEntryValue == 1) {
                    roadSelected = findRoadByApproach(Approach.SOUTH);
                    spawnPos = new Coordinate(-50, 450);
                } else if (laneEntryValue == 2) {
                    roadSelected = findRoadByApproach(Approach.NORTH);
                    spawnPos = new Coordinate(1550, 320);
                } else if (laneEntryValue == 3) {
                    roadSelected = findRoadByApproach(Approach.WEST);
                    spawnPos = new Coordinate(550, 1350);
                } else if (laneEntryValue == 4) {
                    roadSelected = findRoadByApproach(Approach.EAST);
                    spawnPos = new Coordinate(450, -550);
                }

            } catch (NumberFormatException Ne) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for lane.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException Ie) {
                JOptionPane.showMessageDialog(this, Ie.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(laneEntry);

        // Drop down menu for select add vehicle type
        String options[] = { "Car", "Moto", "Ambulance" };
        String loadOptions[] = { "One Person", "Two People", "Three People", "Four People" };
        final JComboBox<String> comboBox = new JComboBox<>(options);
        final JComboBox<String> loadComboBox = new JComboBox<>(loadOptions);
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedItem().equals("Car")) {
                // if they select car, show another drop down menu for car load
                loadComboBox.addActionListener(le -> {
                    String selectedLoad = (String) loadComboBox.getSelectedItem();
                    switch (selectedLoad) {
                        case "One Person":
                            selectedCarLoad = Car_load.ONE_PERSON;
                            break;
                        case "Two People":
                            selectedCarLoad = Car_load.TWO_PERSON;
                            break;
                        case "Three People":
                            selectedCarLoad = Car_load.THREE_PERSON;
                            break;
                        case "Four People":
                            selectedCarLoad = Car_load.FOUR_PERSON;
                            break;
                    }
                    System.out.println("Selected car load: " + selectedCarLoad);
                });
                add(loadComboBox);
                loadComboBox.setVisible(true);
            } else {
                loadComboBox.setVisible(false);
            }

            selectedVehicle = (String) comboBox.getSelectedItem();
            System.out.println("Selected vehicle type: " + selectedVehicle);
        });
        add(comboBox);

        // button to add vehicle
        JButton addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.addActionListener(e -> {

            if (button.getText().equals("Pause")) {
                JOptionPane.showMessageDialog(this, "Please pause the simulation before adding a vehicle.",
                        "Simulation Paused", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (speedValue <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid speed value before adding a vehicle.",
                        "Invalid Speed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (laneEntryValue < 1 || laneEntryValue > 4) {
                JOptionPane.showMessageDialog(this, "Please enter a valid lane entry value (1-4).", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (roadSelected == null || spawnPos == null) {
                JOptionPane.showMessageDialog(this, "Please set a valid lane before adding a vehicle.", "Invalid Lane",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vehicle newVehicle = null;

            switch (selectedVehicle) {
                case "Car":
                    newVehicle = new Car(laneEntryOrientation, speedValue, 0, roadSelected, selectedCarLoad);
                    break;
                case "Moto":
                    newVehicle = new Motorcycle(laneEntryOrientation, roadSelected.getApproach(), speedValue, 0, roadSelected);
                    break;
                case "Ambulance":
                    newVehicle = new Ambulance(laneEntryOrientation, roadSelected.getApproach(), speedValue, 0, roadSelected);
                    break;
                default:
                    return;
            }
            if (newVehicle != null) {
                addVehicle(newVehicle, spawnPos);
                System.out.println("Added vehicle");
                System.out.println(
                        "Vehicle type: " + selectedVehicle + ", Speed: " + speedValue + ", Lane: " + laneEntryValue);
            }
        });

        add(addVehicleButton);

        // creat a input for tickspeed
        JTextField tickInput = new JTextField("30", 5);
        tickInput.setLocation(10, 10);
        tickInput.setVisible(true);
        tickInput.addActionListener(e -> {
            try {
                int newDelay = Integer.parseInt(tickInput.getText());
                if (newDelay > 200) {
                    throw new InvalidInputException("Tick speed must be between 0 and 200 ms.");
                }
                timer.setDelay(newDelay);
            } catch (NumberFormatException Ne) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer for tick speed.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (InvalidInputException Te) {
                JOptionPane.showMessageDialog(this, Te.getMessage(), "Invalid Tick Speed", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException Ie) {
                JOptionPane.showMessageDialog(this, Ie.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        JLabel tickspeedtext = new JLabel("Tick Speed (ms):");
        tickspeedtext.setBackground(Color.WHITE);
        add(tickspeedtext);
        add(tickInput);

        // timer for simulation loop
        timer.start();
    }

    private void updateSimulation() {
        for (TrafficLight light : trafficLights.keySet()) {
            // update light in parttern WNSE: 1 green, 3 red, then switch

            updateTrafficLight(light, 30);
        }
        ;
        int stopDistance = 50;

        for (Vehicle v : vehicles.keySet()) {
            Coordinate pos = vehicles.get(v);

            double speedIncrease = v.getAccelerationRate();
            // System.out.println("Vehicle " + v.getClass().getSimpleName() + " at (" +
            // pos.getX() + ", " + pos.getY() + ") with speed " + v.getCurspeed() + "speed"
            // + v.getSpeed() + "acceleration" + speedIncrease);
            if (v.getCurspeed() >= v.getSpeed())
                speedIncrease = 0.0;
            v.setCurspeed(v.getCurspeed() + speedIncrease);

            boolean stop = false;
            TrafficLight matchedLight = getTrafficlightToObey(v);
            Stopline matchedLine = getStoplineToObey(v);

            // Emergency vehicles only bypass stopline (ignore matchedLine check if
            // emergency)
            boolean shouldObeyStopline = !v.isEmergency();
            boolean stopcondition = shouldObeyStopline && !v.hasTurned() && matchedLight != null && matchedLine != null
                    && matchedLight.getState() == LightState.RED;
            if (stopcondition) {
                Coordinate linePos = stoplines.get(matchedLine);
                double distance = Math.hypot(linePos.getX() - pos.getX(), linePos.getY() - pos.getY());
                if (distance <= stopDistance)
                    stop = true;
            }

            if (!stop) {
                for (Vehicle other : vehicles.keySet()) {
                    if (v == other)
                        continue;
                    Coordinate otherPos = vehicles.get(other);
                    double dist = Math.hypot(otherPos.getX() - pos.getX(), otherPos.getY() - pos.getY());

                    if (dist < 100 && isBehind(v, pos, other, otherPos)) {
                        // Same lane check for overtaking
                        if (v.getRoad().getApproach() == other.getRoad().getApproach()
                                && v.getLaneType() == other.getLaneType()) {
                            if (v.getLaneType() == LaneType.OUTER && isLaneSwitchSafe(v, LaneType.INNER)) {
                                switchLane(v, pos, LaneType.INNER);
                                break;
                            }
                            stop = true;
                            break;
                        } else {
                            // Global collision: slow down if any vehicle is in front and close
                            if (dist < 50 && !v.isTurning()) {
                                stop = true;
                                break;
                            } else if (dist < 50 && v.isTurning() && other.isTurning()) {
                                v.setCurspeed(Math.min(v.getCurspeed(), other.getCurspeed()));
                            }
                        }
                    }
                }
            }

            if (stop)
                v.setCurspeed(0);
            else if (v.getCurspeed() == 0)
                v.setCurspeed(0);

            if (!v.hasTurned() && v.getTurnDirection() != TurnDirection.STRAIGHT && !v.isTurning()) {
                // Trigger turn when entering intersection box [400, 600] x [300, 500]
                if (pos.getX() >= 400 && pos.getX() <= 600 && pos.getY() >= 300 && pos.getY() <= 500) {
                    performTurn(v);
                }
            }

            moveVehicle(v, pos, 1000, 800);
        }
    }

    private boolean isLaneSwitchSafe(Vehicle v, LaneType targetLane) {
        Road road = v.getRoad();

        if (targetLane == LaneType.INNER) {
            if (road.getInnerLaneVehicleCount() >= (road.getTotalVehicleCount() + 1) / 2) {
                if (!v.isEmergency())
                    return false;
            }
        } else {
            if (road.getOuterLaneVehicleCount() >= (road.getTotalVehicleCount() + 1) / 2) {
                if (!v.isEmergency())
                    return false;
            }
        }

        Coordinate myPos = vehicles.get(v);
        double myNewX = myPos.getX();
        double myNewY = myPos.getY();
        double offset = 40;
        Approach app = road.getApproach();

        if (app == Approach.SOUTH)
            myNewY += (targetLane == LaneType.INNER ? -offset : offset);
        else if (app == Approach.NORTH)
            myNewY += (targetLane == LaneType.INNER ? offset : -offset);
        else if (app == Approach.WEST)
            myNewX += (targetLane == LaneType.INNER ? -offset : offset);
        else if (app == Approach.EAST)
            myNewX += (targetLane == LaneType.INNER ? offset : -offset);

        for (Vehicle other : vehicles.keySet()) {
            if (v == other)
                continue;
            if (other.getRoad().getApproach() == road.getApproach() && other.getLaneType() == targetLane) {
                Coordinate otherPos = vehicles.get(other);
                if (Math.hypot(otherPos.getX() - myNewX, otherPos.getY() - myNewY) < 110) {
                    return false;
                }
            }
        }

        return true;
    }

    private void switchLane(Vehicle v, Coordinate pos, LaneType targetLane) {
        if (v.getLaneType() == targetLane)
            return;

        v.getRoad().decrementVehicleCount(v.getLaneType());
        v.setLaneType(targetLane);
        v.getRoad().incrementVehicleCount(targetLane);

        Approach approach = v.getRoad().getApproach();
        double offset = 40;

        if (approach == Approach.SOUTH)
            pos.setY(pos.getY() + (targetLane == LaneType.INNER ? -offset : offset));
        else if (approach == Approach.NORTH)
            pos.setY(pos.getY() + (targetLane == LaneType.INNER ? offset : -offset));
        else if (approach == Approach.WEST)
            pos.setX(pos.getX() + (targetLane == LaneType.INNER ? -offset : offset));
        else if (approach == Approach.EAST)
            pos.setX(pos.getX() + (targetLane == LaneType.INNER ? offset : -offset));
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

    private void moveVehicle(Vehicle v, Coordinate pos, int windowsWidth, int windowHeight) {
        if (v.isTurning()) {
            double curspeed = v.getCurspeed();
            double radius = v.getTurnRadius();
            double dTheta = (curspeed / radius);

            double currentTheta = v.getCurrentTurnAngle();
            double targetTheta = v.getTargetTurnAngle();
            int dirFactor = v.getTurnDirectionFactor();

            double nextTheta = currentTheta + dirFactor * dTheta;

            // Check if turn is complete
            boolean turnComplete = false;
            if (dirFactor > 0) {
                if (nextTheta >= targetTheta)
                    turnComplete = true;
            } else {
                if (nextTheta <= targetTheta)
                    turnComplete = true;
            }

            if (turnComplete) {
                nextTheta = targetTheta;
                v.setTurning(false);
                v.setHasTurned(true);
            }

            v.setCurrentTurnAngle(nextTheta);
            Coordinate center = v.getTurnCenter();

            // Update position using circular arc formula from summary
            // X = cx + R * cos(theta), Y = cy - R * sin(theta)
            pos.setX(center.getX() + radius * Math.cos(nextTheta));
            pos.setY(center.getY() - radius * Math.sin(nextTheta));

        } else {
            Approach approach = v.getRoad().getApproach();
            double curspeed = v.getCurspeed();

            if (approach == Approach.SOUTH)
                pos.setX(pos.getX() + curspeed);
            else if (approach == Approach.NORTH)
                pos.setX(pos.getX() - curspeed);
            else if (approach == Approach.EAST)
                pos.setY(pos.getY() + curspeed);
            else if (approach == Approach.WEST)
                pos.setY(pos.getY() - curspeed);
        }

        if (pos.getX() > windowsWidth + 1000 || pos.getX() < -1000 ||
                pos.getY() > windowHeight + 1000 || pos.getY() < -1000) {
            resetVehicle(v, pos);
        }
    }

    private void resetVehicle(Vehicle v, Coordinate pos) {
        Coordinate spawn = vehicleSpawns.get(v);
        if (v.getRoad() != null) {
            v.getRoad().decrementVehicleCount(v.getLaneType());
        }

        pos.setX(spawn.getX());
        pos.setY(spawn.getY());
        v.setRoad(v.getOriginalRoad());
        v.setLaneType(LaneType.OUTER);

        if (v.getRoad() != null) {
            v.getRoad().incrementVehicleCount(v.getLaneType());
        }

        v.setOrientation(
                (v.getRoad().getId() == 1 || v.getRoad().getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
        v.setHasTurned(false);
        v.setTurning(false);
        v.setTurnDirection(TurnDirection.values()[new java.util.Random().nextInt(3)]);
    }

    private boolean isBehind(Vehicle v, Coordinate vPos, Vehicle other, Coordinate otherPos) {
        Approach approach = v.getRoad().getApproach();
        if (approach == Approach.SOUTH)
            return otherPos.getX() > vPos.getX();
        else if (approach == Approach.NORTH)
            return otherPos.getX() < vPos.getX();
        else if (approach == Approach.EAST)
            return otherPos.getY() > vPos.getY();
        else if (approach == Approach.WEST)
            return otherPos.getY() - vPos.getY() < 0; // Wait, West moves North (-Y)

        // Correcting WEST logic: if moving North (-Y), behind means other has smaller Y
        if (approach == Approach.WEST)
            return otherPos.getY() < vPos.getY();
        return false;
    }

    private TrafficLight getTrafficlightToObey(Vehicle v) {
        for (TrafficLight light : trafficLights.keySet()) {
            if (light.getRoad().getApproach() == v.getRoad().getApproach())
                return light;
        }
        return null;
    }

    private Stopline getStoplineToObey(Vehicle v) {
        for (Stopline line : stoplines.keySet()) {
            if (line.getRoad().getApproach() == v.getRoad().getApproach() && line.isCollidable())
                return line;
        }
        return null;
    }

    private void performTurn(Vehicle v) {
        Approach currentApp = v.getRoad().getApproach();
        TurnDirection dir = v.getTurnDirection();
        if (dir == TurnDirection.STRAIGHT)
            return;

        Road targetRoad = null;
        double icx = 500, icy = 400; // Intersection Center
        Coordinate center = null;
        double startAngle = 0, targetAngle = 0;
        int dirFactor = 0;
        double radius = 0;

        // Lane positions relative to midline (400 for HORIZ, 500 for VERT)
        // South/North roads (HORIZ): Outer = 450/350, Inner = 410/390. Midline 400.
        // West/East roads (VERT): Outer = 550/450, Inner = 510/490. Midline 500.

        if (currentApp == Approach.SOUTH) { // Moving East (+X)
            if (dir == TurnDirection.LEFT) { // to WEST (North, -Y)
                targetRoad = findRoadByApproach(Approach.WEST);
                center = new Coordinate(400, 300);
                radius = Math.abs(vehicles.get(v).getY() - 300);
                startAngle = 1.5 * Math.PI;
                targetAngle = 2.0 * Math.PI;
                dirFactor = 1;
            } else { // to EAST (South, +Y)
                targetRoad = findRoadByApproach(Approach.EAST);
                center = new Coordinate(400, 500);
                radius = Math.abs(500 - vehicles.get(v).getY());
                startAngle = 0.5 * Math.PI;
                targetAngle = 0.0;
                dirFactor = -1;
            }
        } else if (currentApp == Approach.NORTH) { // Moving West (-X)
            if (dir == TurnDirection.LEFT) { // to EAST (South, +Y)
                targetRoad = findRoadByApproach(Approach.EAST);
                center = new Coordinate(600, 500);
                radius = Math.abs(500 - vehicles.get(v).getY());
                startAngle = 0.5 * Math.PI;
                targetAngle = 1.0 * Math.PI;
                dirFactor = 1;
            } else { // to WEST (North, -Y)
                targetRoad = findRoadByApproach(Approach.WEST);
                center = new Coordinate(600, 300);
                radius = Math.abs(vehicles.get(v).getY() - 300);
                startAngle = 1.5 * Math.PI;
                targetAngle = 1.0 * Math.PI;
                dirFactor = -1;
            }
        } else if (currentApp == Approach.WEST) { // Moving North (-Y)
            if (dir == TurnDirection.LEFT) { // to NORTH (West, -X)
                targetRoad = findRoadByApproach(Approach.NORTH);
                center = new Coordinate(600, 500); // Pivot around Bottom-Right
                radius = Math.abs(600 - vehicles.get(v).getX());
                startAngle = Math.PI;
                targetAngle = 0.5 * Math.PI;
                dirFactor = -1;
            } else { // to SOUTH (East, +X)
                targetRoad = findRoadByApproach(Approach.SOUTH);
                center = new Coordinate(400, 500); // Pivot around Bottom-Left
                radius = Math.abs(vehicles.get(v).getX() - 400);
                startAngle = 0.0;
                targetAngle = 0.5 * Math.PI;
                dirFactor = 1;
            }
        } else if (currentApp == Approach.EAST) { // Moving South (+Y)
            if (dir == TurnDirection.LEFT) { // to SOUTH (East, +X)
                targetRoad = findRoadByApproach(Approach.SOUTH);
                center = new Coordinate(400, 300); // Pivot around Top-Left
                radius = Math.abs(vehicles.get(v).getX() - 400);
                startAngle = Math.PI;
                targetAngle = 1.5 * Math.PI;
                dirFactor = 1;
            } else { // to NORTH (West, -X)
                targetRoad = findRoadByApproach(Approach.NORTH);
                center = new Coordinate(600, 300); // Pivot around Top-Right
                radius = Math.abs(600 - vehicles.get(v).getX());
                startAngle = 0.0;
                targetAngle = -0.5 * Math.PI;
                dirFactor = -1;
            }
        }

        if (targetRoad != null) {
            v.getRoad().decrementVehicleCount(v.getLaneType());
            v.setRoad(targetRoad);
            v.getRoad().incrementVehicleCount(v.getLaneType());

            v.setTurning(true);
            v.setTurnCenter(center);
            v.setTurnRadius(radius);
            v.setCurrentTurnAngle(startAngle);
            v.setTargetTurnAngle(targetAngle);
            v.setTurnDirectionFactor(dirFactor);
            v.setOrientation((targetRoad.getApproach() == Approach.NORTH || targetRoad.getApproach() == Approach.SOUTH)
                    ? Orientation.HORIZONTAL
                    : Orientation.VERTICAL);
        }
    }

    private Road findRoadByApproach(Approach app) {
        for (Road r : roads.keySet()) {
            if (r.getApproach() == app)
                return r;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Map.Entry<Road, Coordinate> entry : roads.entrySet()) {
            entry.getKey().render(g2d, true, entry.getValue());
        }
        ;
        for (Map.Entry<TrafficLight, Coordinate> entry : trafficLights.entrySet()) {
            entry.getKey().render(g2d, true, entry.getValue());
        }
        ;
        for (Map.Entry<Vehicle, Coordinate> entry : vehicles.entrySet()) {
            entry.getKey().render(g2d, entry.getKey().getOrientation() == Orientation.VERTICAL, entry.getValue());
        }
        ;
    }

    void countVehicles() {
        int count = 0;
        for (Vehicle v : vehicles.keySet())
            count++;
        System.out.println("Total vehicles: " + count);
    }

    private int countFilteredVehicles(VehicleFilter filter) {
        int count = 0;
        for (Vehicle v : vehicles.keySet()) {
            if (filter.filter(v))
                count++;
        }
        return count;
    }

    void addRoad(Road road, Coordinate pos) {
        roads.put(road, pos);
    }

    void addTrafficLight(TrafficLight light, Coordinate pos) {
        trafficLights.put(light, pos);
    }

    void addVehicle(Vehicle v, Coordinate pos) {
        vehicles.put(v, new Coordinate(pos.getX(), pos.getY()));
        vehicleSpawns.put(v, new Coordinate(pos.getX(), pos.getY()));
        if (v.getRoad() != null) {
            v.getRoad().incrementVehicleCount(v.getLaneType());
        }
    }

    void addstopline(Stopline line, Coordinate pos) {
        stoplines.put(line, pos);
    }
}