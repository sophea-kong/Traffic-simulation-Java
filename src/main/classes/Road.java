import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road extends InanimatedObject implements Renderable {
    public static int idCounter = 1;
    private Orientation orientation;
    private Approach approach;

    private int length;
    private int roadWidth;
    private int laneCount;
    private int stopLineOffset;

    private Color asphaltColor = new Color(60, 60, 60);
    private Color laneMark = new Color(255, 255, 255);
    private Color stopLineColor = new Color(255, 235, 235);

    Road(int cx, int cy, Orientation orientation, Approach approach, int length, int roadWidth,
            int laneCount, int stopLineOffset) {
        super(new Coordinate(cx, cy), 
              (orientation == Orientation.HORIZONTAL) ? length : roadWidth,
              (orientation == Orientation.HORIZONTAL) ? roadWidth : length);
        this.orientation = orientation;
        this.approach = approach;
        setLength(length);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
    }


    Road(int cx, int cy, Orientation orientation, Approach approach, int length, int roadWidth,
            int laneCount, int stopLineOffset, int id) {
        super(new Coordinate(cx, cy), 
              (orientation == Orientation.HORIZONTAL) ? length : roadWidth,
              (orientation == Orientation.HORIZONTAL) ? roadWidth : length);
        this.orientation = orientation;
        this.approach = approach;
        setLength(length);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
    }
    Road (int x, int y, Orientation orientation, Approach approach, int stopLineOffset, int id) {
        super(new Coordinate(x, y), 
              (orientation == Orientation.HORIZONTAL) ? 600 : 200,
              (orientation == Orientation.HORIZONTAL) ? 200 : 600);
        this.orientation = orientation;
        this.approach = approach;
        this.length = 600;
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = stopLineOffset;
    }
   Road (int x, int y, Approach approach, int id) {  
        super(new Coordinate(x, y), 600, 200);
        this.orientation = (approach == Approach.NORTH || approach == Approach.SOUTH) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.width = (orientation == Orientation.HORIZONTAL) ? 600 : 200;
        this.height = (orientation == Orientation.HORIZONTAL) ? 200 : 600;
        this.approach = approach;
        this.length = 600;  
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = (approach == Approach.NORTH || approach == Approach.EAST) ? 200 : -200;
   } 
   Road (int x, int y, int id) {
        super(new Coordinate(x, y), 600, 200);
        this.orientation = (id == 1 || id == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL; 
        this.width = (orientation == Orientation.HORIZONTAL) ? 600 : 200;
        this.height = (orientation == Orientation.HORIZONTAL) ? 200 : 600;
        this.approach = (id == 1) ? Approach.SOUTH : (id == 2) ? Approach.NORTH : (id == 3) ? Approach.EAST : Approach.WEST;
        this.length = 600;  
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = (this.approach == Approach.SOUTH || this.approach == Approach.WEST) ? 200 : -200;
   }


    private void setStopLineOffset(int stopLineOffset) {
        if (stopLineOffset < -this.length / 2) {
            stopLineOffset = -this.length / 2;
        } else if (stopLineOffset > this.length / 2) {
            stopLineOffset = this.length / 2;
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



    private void setLength(int length) {
        if (length < 0) {
            length = 0;
        } else if (length > 2000) {
            length = 2000;
        }
        this.length = length;
    }

    public Approach getApproach() {
        return this.approach;
    }

    @Override
    public void render(Graphics2D g2d) {
        // Draw road background
        g2d.setColor(this.asphaltColor);

        if (this.orientation == Orientation.HORIZONTAL) {
            g2d.fillRect((int) this.position.getX() - this.length / 2, (int) this.position.getY() - this.roadWidth / 2,
                    this.length, this.roadWidth);

            // Draw lane markings
            g2d.setColor(this.laneMark);
            for (int i = this.roadWidth / this.laneCount; i < this.roadWidth; i += this.roadWidth / this.laneCount) {
                g2d.drawLine((int) this.position.getX() - this.length / 2,
                        (int) (this.position.getY() - this.roadWidth / 2 + i),
                        (int) this.position.getX() + this.length / 2,
                        (int) (this.position.getY() - this.roadWidth / 2 + i));
            }
        } else {
            g2d.fillRect((int) this.position.getX() - this.roadWidth / 2, (int) this.position.getY() - this.length / 2,
                    this.roadWidth, this.length);

            // Draw lane markings
            g2d.setColor(this.laneMark);
            for (int i = this.roadWidth / this.laneCount; i < this.roadWidth; i += this.roadWidth / this.laneCount) {
                g2d.drawLine((int) (this.position.getX() - this.roadWidth / 2 + i),
                        (int) this.position.getY() - this.length / 2,
                        (int) (this.position.getX() - this.roadWidth / 2 + i),
                        (int) this.position.getY() + this.length / 2);
            }
        }

        // Draw stop line
        g2d.setColor(this.stopLineColor);
        if (this.orientation == Orientation.HORIZONTAL) {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine((int) this.position.getX() + this.stopLineOffset,
                    (int) this.position.getY() - this.roadWidth / 2,
                    (int) this.position.getX() + this.stopLineOffset, (int) this.position.getY() + this.roadWidth / 2);
        } else {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine((int) this.position.getX() - this.roadWidth / 2,
                    (int) this.position.getY() + this.stopLineOffset,
                    (int) this.position.getX() + this.roadWidth / 2, (int) this.position.getY() + this.stopLineOffset);
        }
    }
}


