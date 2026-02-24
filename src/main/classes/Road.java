import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road implements Renderable{
    public static int idCounter = 1;
    private int id;
    private Coordinate position;

    private Orientation orientation;
    private Approach approach;

    private int lenght;
    private int roadWidth;
    private int laneCount;
    private int stopLineOffset;

    private Color asphaltColor = new Color(60, 60, 60);
    private Color laneMark = new Color(255, 255, 255);
    private Color stopLineColor = new Color(255, 235, 235);
    // private Color crosColor = new Color(255, 235, 235);

    Road(int cx, int cy, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset) {
        this.id = Road.idCounter++;
        setPosition(new Coordinate(cx, cy));
        this.orientation = orientation;
        this.approach = approach;
        setLenght(lenght);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
    }


    Road(int cx, int cy, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset, int id) {
        setPosition(new Coordinate(cx, cy));
        this.orientation = orientation;
        this.approach = approach;
        setLenght(lenght);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
        this.id = Road.idCounter++;
    }
    Road (int x, int y, Orientation orientation, Approach approach, int stopLineOffset, int id) {
        setPosition(new Coordinate(x, y));
        this.orientation = orientation;
        this.approach = approach;
        setLenght(600);
        setRoadWidth(200);
        setLaneCount(2);
        setStopLineOffset(stopLineOffset);
        this.id = Road.idCounter++;
    }
   Road (int x, int y, Approach approach) {  
        setPosition(new Coordinate(x, y));
        this.orientation = (approach == Approach.NORTH || approach == Approach.SOUTH) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.approach = approach;
        setLenght(600);
        setRoadWidth(200);
        setLaneCount(2);
        this.stopLineOffset = (approach == Approach.NORTH || approach == Approach.EAST) ? 200 : -200;
        this.id = Road.idCounter++; 
   } 
   Road (int x, int y, int id) {
        setPosition(new Coordinate(x, y));
        this.orientation = (id == 1 || id == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL; 
        this.approach = (id == 1) ? Approach.SOUTH : (id == 2) ? Approach.NORTH : (id == 3) ? Approach.EAST : Approach.WEST;
        setLenght(600);
        setRoadWidth(200);
        setLaneCount(2);
        setStopLineOffset((this.approach == Approach.SOUTH || this.approach == Approach.WEST) ? 200 : -200);
        this.id = Road.idCounter++; 
   }


    private void setStopLineOffset(int stopLineOffset) {
        if (stopLineOffset < 0) {
            stopLineOffset = 0;
        } else if (stopLineOffset > this.lenght / 2) {
            stopLineOffset = this.lenght / 2;
        }
        this.stopLineOffset = stopLineOffset;
    }
    private void setLaneCount(int laneCount) {
        if (laneCount < 1) {
            laneCount = 1;
        } else if (laneCount > 5) {
            laneCount = 5;
        }
        this.laneCount = laneCount;
    }

    private void setRoadWidth(int roadWidth) {
        if (roadWidth < 0) {
            roadWidth = 0;
        } else if (roadWidth > 1000) {
            roadWidth = 1000;
        }
        this.roadWidth = roadWidth;

    }



    private void setPosition(Coordinate position) {
        if (position == null) {
            return;
        }
        this.position = position;
    }
    
    private void setLenght(int lenght) {
        if (lenght < 0) {
            lenght = 0;
        } else if (lenght > 2000) {
            lenght = 2000;
        }
        this.lenght = lenght;
    }

    public Approach getApproach() {
        return this.approach;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public int getId() {
        return this.id;
    }
    public Coordinate getPosition() {
        return this.position;
    }
    public void render(Graphics2D g2d, boolean vertical) {
        Road road = this;
        // Draw road background
        g2d.setColor(road.asphaltColor);

        if (road.orientation == Orientation.HORIZONTAL) {
            g2d.fillRect((int) road.position.getX() - road.lenght / 2, (int) road.position.getY() - road.roadWidth / 2,
                    road.lenght, road.roadWidth);

            // Draw lane markings
            g2d.setColor(road.laneMark);
            for (int i = road.roadWidth / road.laneCount; i < road.roadWidth; i += road.roadWidth / road.laneCount) {
                g2d.drawLine((int) road.position.getX() - road.lenght / 2,
                        (int) (road.position.getY() - road.roadWidth / 2 + i),
                        (int) road.position.getX() + road.lenght / 2,
                        (int) (road.position.getY() - road.roadWidth / 2 + i));
            }
        } else {
            g2d.fillRect((int) road.position.getX() - road.roadWidth / 2, (int) road.position.getY() - road.lenght / 2,
                    road.roadWidth, road.lenght);

            // Draw lane markings
            g2d.setColor(road.laneMark);
            for (int i = road.roadWidth / road.laneCount; i < road.roadWidth; i += road.roadWidth / road.laneCount) {
                g2d.drawLine((int) (road.position.getX() - road.roadWidth / 2 + i),
                        (int) road.position.getY() - road.lenght / 2,
                        (int) (road.position.getX() - road.roadWidth / 2 + i),
                        (int) road.position.getY() + road.lenght / 2);
            }
        }

        // Draw stop line
        g2d.setColor(road.stopLineColor);
        if (road.orientation == Orientation.HORIZONTAL) {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine((int) road.position.getX() + road.stopLineOffset,
                    (int) road.position.getY() - road.roadWidth / 2,
                    (int) road.position.getX() + road.stopLineOffset, (int) road.position.getY() + road.roadWidth / 2);
        } else {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine((int) road.position.getX() - road.roadWidth / 2,
                    (int) road.position.getY() + road.stopLineOffset,
                    (int) road.position.getX() + road.roadWidth / 2, (int) road.position.getY() + road.stopLineOffset);
        }
    }
}
