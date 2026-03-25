package children;

import parents.InanimatedObject;
import interfaces.Renderable;
import utils.*;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;



public class Road extends InanimatedObject implements Renderable {
    public static int idCounter = 1;
    private Orientation orientation;
    Approach approach;

    private int length;
    private int roadWidth;
    private int laneCount;
    private int stopLineOffset;

    private int innerLaneVehicleCount = 0;
    private int outerLaneVehicleCount = 0;

    private Color asphaltColor = new Color(60, 60, 60);
    private Color laneMark = new Color(255, 255, 255);
    private Color stopLineColor = new Color(255, 235, 235);

    public Road(Orientation orientation, Approach approach, int length, int roadWidth,
            int laneCount, int stopLineOffset) {
        super((orientation == Orientation.HORIZONTAL) ? roadWidth : length,
              (orientation == Orientation.HORIZONTAL) ? length : roadWidth, true, true);
        this.orientation = orientation;
        this.approach = approach;
        setLength(length);
        setRoadWidth(roadWidth);
        setLaneCount(laneCount);
        setStopLineOffset(stopLineOffset);
    }
    
    public void incrementVehicleCount(LaneType laneType) {
        if (laneType == LaneType.INNER) innerLaneVehicleCount++;
        else outerLaneVehicleCount++;
    }

    public void decrementVehicleCount(LaneType laneType) {
        if (laneType == LaneType.INNER) innerLaneVehicleCount--;
        else outerLaneVehicleCount--;
    }

    public int getInnerLaneVehicleCount() { return innerLaneVehicleCount; }
    public int getOuterLaneVehicleCount() { return outerLaneVehicleCount; }
    public int getTotalVehicleCount() { return innerLaneVehicleCount + outerLaneVehicleCount; }
    

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
            g2d.fillRect((int) pos.getX() - this.length / 2, (int) pos.getY() - this.roadWidth / 2,
                    this.length, this.roadWidth);

            // Draw lane markings
            g2d.setColor(this.laneMark);
            for (int i = this.roadWidth / this.laneCount; i < this.roadWidth; i += this.roadWidth / this.laneCount) {
                g2d.drawLine((int) pos.getX() - this.length / 2,
                        (int) (pos.getY() - this.roadWidth / 2 + i),
                        (int) pos.getX() + this.length / 2,
                        (int) (pos.getY() - this.roadWidth / 2 + i));
            }
        } else {
            g2d.fillRect((int) pos.getX() - this.roadWidth / 2, (int) pos.getY() - this.length / 2,
                    this.roadWidth, this.length);

            // Draw lane markings
            g2d.setColor(this.laneMark);
            for (int i = this.roadWidth / this.laneCount; i < this.roadWidth; i += this.roadWidth / this.laneCount) {
                g2d.drawLine((int) (pos.getX() - this.roadWidth / 2 + i),
                        (int) pos.getY() - this.length / 2,
                        (int) (pos.getX() - this.roadWidth / 2 + i),
                        (int) pos.getY() + this.length / 2);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Road road = (Road) o;
        return length == road.length && roadWidth == road.roadWidth && laneCount == road.laneCount && stopLineOffset == road.stopLineOffset && orientation == road.orientation && approach == road.approach;
    }

    @Override
    public String toString() {
        return "Road{" +
                "id=" + id +
                ", orientation=" + orientation +
                ", approach=" + approach +
                ", length=" + length +
                ", roadWidth=" + roadWidth +
                '}';
    }
}


