package view;

import parents.*;
import children.*;
import exceptions.TickSpeedException;
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
    private Road s; // temporary variable to store the road for new vehicle
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
                
                //Lambda Expression
                int carCount = countFilteredVehicles(v ->( v instanceof Car) && vehicles.get(v).getX() <= 1000
                                && vehicles.get(v).getY() > 0 && vehicles.get(v).getY() <= 900);
                
                System.out.println("Stats [Frame " + frameCounter + "]: Emergency: " + emergencyCount + ", Cars: " + carCount);
            }
        });

        JButton button =  new JButton("Pause");
        
        //lambda expression on button
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
            try{
                speedValue = Double.parseDouble(speed.getText());
                if (speedValue < 0) {
                    throw new IllegalArgumentException("Speed value cannot be negative.");
                }
            }catch(NumberFormatException Ne) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for speed.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }catch(IllegalArgumentException Ie) {
                JOptionPane.showMessageDialog(this, Ie.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(speed);

        JTextField laneEntry = new JTextField(10);
        laneEntry.setText("set lane (1-4)");
        laneEntry.addActionListener(e -> {
            try {
                laneEntryValue = Integer.parseInt(laneEntry.getText());
                if(laneEntryValue < 1 || laneEntryValue > 4) {
                    throw new IllegalArgumentException("Lane entry value must be between 1 and 4.");
                }

                // Horizontal for lane 1 and 2, vertical for lane 3 and 4
                if(laneEntryValue == 1 || laneEntryValue == 2) {
                    laneEntryOrientation = Orientation.HORIZONTAL;
                } else {
                    laneEntryOrientation = Orientation.VERTICAL;
                }

                // road
                if(laneEntryValue == 1) {
                    s = findRoadByApproach(Approach.SOUTH);
                    spawnPos = new Coordinate(-50, 450);
                } else if (laneEntryValue == 2) {
                    s = findRoadByApproach(Approach.NORTH);
                    spawnPos = new Coordinate(1550, 350);
                } else if (laneEntryValue == 3) {
                    s = findRoadByApproach(Approach.WEST);
                    spawnPos = new Coordinate(550, 1350);
                } else if (laneEntryValue == 4) {
                    s = findRoadByApproach(Approach.EAST);
                    spawnPos = new Coordinate(450, -550);
                }

            } catch(NumberFormatException Ne) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for lane.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }catch(IllegalArgumentException Ie) {
                JOptionPane.showMessageDialog(this, Ie.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(laneEntry);
        
        // Drop down menu for select add vehicle type
        String options[] = {"Car", "Moto", "Ambulance"};
        String loadOptions[] = {"One Person", "Two People", "Three People", "Four People"};
        final JComboBox<String> comboBox = new JComboBox<>(options);
        final JComboBox<String> loadComboBox = new JComboBox<>(loadOptions);
        comboBox.addActionListener(e -> {
            if(comboBox.getSelectedItem().equals("Car")) {
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

            selectedVehicle  = (String) comboBox.getSelectedItem();
            System.out.println("Selected vehicle type: " + selectedVehicle);
        });
        add(comboBox);

        // button to add vehicle
        JButton addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.addActionListener(e -> {

            if(button.getText().equals("Pause")) {
                JOptionPane.showMessageDialog(this, "Please pause the simulation before adding a vehicle.", "Simulation Paused", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(speedValue <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid speed value before adding a vehicle.", "Invalid Speed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(laneEntryValue < 1 || laneEntryValue > 4) {
                JOptionPane.showMessageDialog(this, "Please enter a valid lane entry value (1-4).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(s == null || spawnPos == null) {
                JOptionPane.showMessageDialog(this, "Please set a valid lane before adding a vehicle.", "Invalid Lane", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vehicle newVehicle = null;
            
            switch (selectedVehicle) {
                case "Car":
                    newVehicle = new Car(laneEntryOrientation, speedValue, 0, s, selectedCarLoad);
                    break;
                case "Moto":
                    newVehicle = new Motorcycle(laneEntryOrientation, s.getApproach(), (int) spawnPos.getX(), (int) spawnPos.getY(), speedValue, 0, s);
                    break;
                case "Ambulance":
                    newVehicle = new Ambulance(laneEntryOrientation, s.getApproach(), (int) spawnPos.getX(), (int) spawnPos.getY(), speedValue, 0, s);
                    break;
                default: 
                    return;
            }
            if (newVehicle != null) {
                addVehicle(newVehicle, spawnPos);
                System.out.println("Added vehicle");
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
                if(newDelay > 200) {throw new TickSpeedException();}
                timer.setDelay(newDelay);
            } catch (NumberFormatException Ne) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer for tick speed.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (TickSpeedException Te) {
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
        for (TrafficLight light : trafficLights.keySet()) updateTrafficLight(light, 30);
        int stopDistance = 50; 

        for (Vehicle v : vehicles.keySet()) {
            Coordinate pos = vehicles.get(v);
            
            double speedIncrease = v.getAccelerationRate();
            if (v.getCurspeed() >= v.getSpeed()) speedIncrease = 0.0;
            v.setCurspeed(v.getCurspeed() + speedIncrease);

            boolean stop = false;
            TrafficLight matchedLight = getTrafficlightToObey(v);
            Stopline matchedLine = getStoplineToObey(v);

            if (!v.isEmergency() && !v.hasTurned() && matchedLight != null && matchedLine != null && matchedLight.getState() == LightState.RED) {
                Coordinate linePos = stoplines.get(matchedLine);
                double distance = Math.hypot(linePos.getX() - pos.getX(), linePos.getY() - pos.getY());
                if (distance <= stopDistance) stop = true;
            }

            if (!stop) {
                for (Vehicle other : vehicles.keySet()) {
                    if (v == other) continue;
                    Coordinate otherPos = vehicles.get(other);
                    if (v.getRoad().getApproach() == other.getRoad().getApproach()) {
                        if (isBehind(v, pos, other, otherPos) && Math.hypot(otherPos.getX() - pos.getX(), otherPos.getY() - pos.getY()) < 80) {
                            if(v.isEmergency()) {
                                if((v.getRoad().getApproach() == Approach.SOUTH || v.getRoad().getApproach() == Approach.NORTH) && !v.isOnPriorityRoad()) {
                                    pos.setY(pos.getY() - 40);
                                    v.setOnPriorityRoad(true);
                                    System.out.println("Emergency vehicle on " + v.getRoad().getApproach() + " is moving to priority road." + v.isOnPriorityRoad());
                                } else if ((v.getRoad().getApproach() == Approach.EAST || v.getRoad().getApproach() == Approach.WEST) && !v.isOnPriorityRoad()){
                                    pos.setX(pos.getX() - 40);
                                    v.setOnPriorityRoad(true);
                                }
                            } else {
                                stop = true;
                            }
                            break;
                        }
                    }
                }
            }

            if (stop) v.setCurspeed(0);
            else if (v.getCurspeed() == 0) v.setCurspeed(0.2);

            if (!v.hasTurned() && v.getTurnDirection() != TurnDirection.STRAIGHT) {
                if (pos.getX() > 420 && pos.getX() < 580 && pos.getY() > 320 && pos.getY() < 480) {
                    performTurn(v);
                }
            }

            moveVehicle(v, pos, 1000, 800);
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


    private void moveVehicle(Vehicle v, Coordinate pos, int windowsWidth, int windowHeight) {
        Approach approach = v.getRoad().getApproach();
        double curspeed = v.getCurspeed();

        if (approach == Approach.SOUTH) pos.setX(pos.getX() + curspeed);
        else if (approach == Approach.NORTH) pos.setX(pos.getX() - curspeed);
        else if (approach == Approach.EAST) pos.setY(pos.getY() + curspeed);
        else if (approach == Approach.WEST) pos.setY(pos.getY() - curspeed);

        if (v.isTurning()) {
            double shiftSpeed = 3.0;
            double turnTargetCoord = v.getTurnTargetCoord();
            if (v.getOrientation() == Orientation.VERTICAL) {
                if (Math.abs(pos.getX() - turnTargetCoord) < shiftSpeed) {
                    pos.setX(turnTargetCoord);
                    v.setTurning(false);
                } else {
                    pos.setX(pos.getX() + (pos.getX() < turnTargetCoord ? shiftSpeed : -shiftSpeed));
                }
            } else {
                if (Math.abs(pos.getY() - turnTargetCoord) < shiftSpeed) {
                    pos.setY(turnTargetCoord);
                    v.setTurning(false);
                } else {
                    pos.setY(pos.getY() + (pos.getY() < turnTargetCoord ? shiftSpeed : -shiftSpeed));
                }
            }
        }

        if (pos.getX() > windowsWidth + 1000 || pos.getX() < -1000 || 
            pos.getY() > windowHeight + 1000 || pos.getY() < -1000) {
            resetVehicle(v, pos);
        }
    }

    private void resetVehicle(Vehicle v, Coordinate pos) {
        Coordinate spawn = vehicleSpawns.get(v);
        pos.setX(spawn.getX());
        pos.setY(spawn.getY());
        v.setRoad(v.getOriginalRoad());
        v.setOrientation((v.getRoad().getId() == 1 || v.getRoad().getId() == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
        v.setHasTurned(false);
        v.setTurning(false);
        v.setOnPriorityRoad(false);
        v.setTurnDirection(TurnDirection.values()[new java.util.Random().nextInt(3)]);
    }

    private boolean isBehind(Vehicle v, Coordinate vPos, Vehicle other, Coordinate otherPos) {
        Approach approach = v.getRoad().getApproach();
        if (approach == Approach.SOUTH) return otherPos.getX() > vPos.getX();
        else if (approach == Approach.NORTH) return otherPos.getX() < vPos.getX();
        else if (approach == Approach.EAST) return otherPos.getY() > vPos.getY();
        else if (approach == Approach.WEST) return otherPos.getY() < vPos.getY();
        return false;
    }

    private TrafficLight getTrafficlightToObey(Vehicle v) {
        for (TrafficLight light : trafficLights.keySet()) {
            if (light.getRoad().getApproach() == v.getRoad().getApproach()) return light;
        }
        return null;
    }

    private Stopline getStoplineToObey(Vehicle v) {
        for (Stopline line : stoplines.keySet()) {
            if (line.getRoad().getApproach() == v.getRoad().getApproach() && line.isCollidable()) return line;
        }
        return null;
    }

    private void performTurn(Vehicle v) {
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
        for (Road r : roads.keySet()) {
            if (r.getApproach() == app) return r;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // loop through roads, traffic lights, and vehicles to render them (polymorphism becuase we call render on the abstract class type but it uses the overridden method in the actual class)
        for (Map.Entry<Road, Coordinate> entry : roads.entrySet()) {
            entry.getKey().render(g2d, true, entry.getValue());
        };

        for (Map.Entry<TrafficLight, Coordinate> entry : trafficLights.entrySet()) {
            entry.getKey().render(g2d, true, entry.getValue());
        };

        for (Map.Entry<Vehicle, Coordinate> entry : vehicles.entrySet()) {
            entry.getKey().render(g2d, entry.getKey().getOrientation() == Orientation.VERTICAL, entry.getValue());
        };    ;
    }

    void countVehicles() {
        int count = 0;
        for (Vehicle v : vehicles.keySet()) {
            count++;
        }
        System.out.println("Total vehicles: " + count);
    }

    private int countFilteredVehicles(VehicleFilter filter) {
        int count = 0;
        for (Vehicle v : vehicles.keySet()) {
            if (filter.filter(v)) {
                count++;
            }
        }
        return count;
    }

    void addRoad(Road road, Coordinate pos) { roads.put(road, pos); }
    void addTrafficLight(TrafficLight light, Coordinate pos) { trafficLights.put(light, pos); }
    void addVehicle(Vehicle v, Coordinate pos) { 
        vehicles.put(v, new Coordinate(pos.getX(), pos.getY())); 
        vehicleSpawns.put(v, new Coordinate(pos.getX(), pos.getY()));
    }
    void addstopline(Stopline line, Coordinate pos) { stoplines.put(line, pos); }
}