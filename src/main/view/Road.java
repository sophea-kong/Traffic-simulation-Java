import java.awt.Color;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road {
    static int idCounter = 0;
    int id;
    int cx, cy;

    Orientation orientation;
    Approach approach;

    int lenght;
    int roadWidth;
    int laneCount;
    int stopLineOffset;

    // colors
    Color asphaltColor = new Color(60, 60, 60);
    Color laneMark = new Color(255, 255, 255);
    Color stopLineColor = new Color(255, 235, 235);
    Color crosColor = new Color(255, 235, 235);

    Road(int cx, int cy, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset) {
        this.cx = cx;
        this.cy = cy;
        this.orientation = orientation;
        this.approach = approach;
        this.lenght = lenght;
        this.roadWidth = roadWidth;
        this.laneCount = laneCount;
        this.stopLineOffset = stopLineOffset;
        this.id = idCounter++;
    }
    public Road(int cx, int cy, Orientation orientation, Approach approach, int stopLineOffset) {
        this(cx, cy, orientation, approach, 500, 200, 2, stopLineOffset);
    }   
    public int getId() { return id; }
    public Orientation getOrientation() { return orientation; }
}
