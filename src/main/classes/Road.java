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

    Road(Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset) {
        super((orientation == Orientation.HORIZONTAL) ? lenght : roadWidth,
              (orientation == Orientation.HORIZONTAL) ? roadWidth : lenght,true, true);
        this.orientation = orientation;
        this.approach = approach;
        setLength(length);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
    }


    Road(Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset, int id) {
        super((orientation == Orientation.HORIZONTAL) ? lenght : roadWidth,
              (orientation == Orientation.HORIZONTAL) ? roadWidth : lenght,true, true);
        this.orientation = orientation;
        this.approach = approach;
        setLength(length);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
    }
    Road (Orientation orientation, Approach approach, int stopLineOffset, int id) {
        super((orientation == Orientation.HORIZONTAL) ? 600 : 200,
              (orientation == Orientation.HORIZONTAL) ? 200 : 600,true, true    );
        this.orientation = orientation;
        this.approach = approach;
        this.length = 600;
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = stopLineOffset;
    }
   Road (Approach approach, int id) {  
        super((approach == Approach.NORTH || approach == Approach.SOUTH) ? 600 : 200,
              (approach == Approach.NORTH || approach == Approach.SOUTH) ? 200 : 600,true, true);
        this.orientation = (approach == Approach.NORTH || approach == Approach.SOUTH) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.approach = approach;
        this.length = 600;  
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = (approach == Approach.NORTH || approach == Approach.EAST) ? 200 : -200;
   } 
   Road (int id) {
        super((id == 1 || id == 2) ? 600 : 200,
              (id == 1 || id == 2) ? 200 : 600,true, true);
        this.orientation = (id == 1 || id == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL; 
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
    public void render(Graphics2D g2d, boolean vertical, Coordinate pos) {
        // Draw road background
        g2d.setColor(this.asphaltColor);

        if (this.orientation == Orientation.HORIZONTAL) {
            g2d.fillRect((int) pos.getX() - this.lenght / 2, (int) pos.getY() - this.roadWidth / 2,
                    this.lenght, this.roadWidth);

            // Draw lane markings
            g2d.setColor(this.laneMark);
            for (int i = this.roadWidth / this.laneCount; i < this.roadWidth; i += this.roadWidth / this.laneCount) {
                g2d.drawLine((int) pos.getX() - this.lenght / 2,
                        (int) (pos.getY() - this.roadWidth / 2 + i),
                        (int) pos.getX() + this.lenght / 2,
                        (int) (pos.getY() - this.roadWidth / 2 + i));
            }
        } else {
            g2d.fillRect((int) pos.getX() - this.roadWidth / 2, (int) pos.getY() - this.lenght / 2,
                    this.roadWidth, this.lenght);

            // Draw lane markings
            g2d.setColor(this.laneMark);
            for (int i = this.roadWidth / this.laneCount; i < this.roadWidth; i += this.roadWidth / this.laneCount) {
                g2d.drawLine((int) (pos.getX() - this.roadWidth / 2 + i),
                        (int) pos.getY() - this.lenght / 2,
                        (int) (pos.getX() - this.roadWidth / 2 + i),
                        (int) pos.getY() + this.lenght / 2);
            }
        }

        // Draw stop line
        g2d.setColor(this.stopLineColor);
        if (this.orientation == Orientation.HORIZONTAL) {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine((int) pos.getX() + this.stopLineOffset,
                    (int) pos.getY() - this.roadWidth / 2,
                    (int) pos.getX() + this.stopLineOffset, (int) pos.getY() + this.roadWidth / 2);
        } else {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine((int) pos.getX() - this.roadWidth / 2,
                    (int) pos.getY() + this.stopLineOffset,
                    (int) pos.getX() + this.roadWidth / 2, (int) pos.getY() + this.stopLineOffset);
        }
    }
}


