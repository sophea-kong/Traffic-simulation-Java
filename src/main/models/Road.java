import java.awt.Color;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road {
    final int cx, cy;

    final Orientation orientation;
    final Approach approach;

    final int lenght;
    final int roadWidth;
    final int laneCount;
    final int intersectionSize;
    final int stopLineOffset;
    final boolean hasStopLine = true;


    final Color asphaltColor = new Color(60, 60, 60);
    final Color laneMark = new Color(255, 255, 255);
    final Color stopLineColor = new Color(255, 235, 235);
    final Color crosColor = new Color(255, 235, 235);

    public Road(int cx, int cy, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int intersectionSize, int stopLineOffset) {
        this.cx = cx;
        this.cy = cy;
        this.orientation = orientation;
        this.approach = approach;
        this.lenght = lenght;
        this.roadWidth = roadWidth;
        this.laneCount = laneCount;
        this.intersectionSize = intersectionSize;
        this.stopLineOffset = stopLineOffset;
    }
}
