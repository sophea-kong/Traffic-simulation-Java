import java.awt.Color;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road {
    Coordinate position;   
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

    Road(int x, int y, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset) {
        this.position = new Coordinate(x, y);
        this.orientation = orientation;
        this.approach = approach;
        this.lenght = lenght;
        this.roadWidth = roadWidth;
        this.laneCount = laneCount;
        this.stopLineOffset = stopLineOffset;
    }
}
