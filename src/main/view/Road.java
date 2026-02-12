import java.awt.Color;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road {

    int cx, cy;

    Orientation orientation;
    Approach approach;

    int lenght;
    int roadWidth;
    int laneCount;
    int stopLineOffset;
    int idcounter = 1;
    int id;

    Color asphaltColor = new Color(60, 60, 60);
    Color laneMark = new Color(255, 255, 255);
    Color stopLineColor = new Color(255, 235, 235);
    Color crosColor = new Color(255, 235, 235);

    Road(int cx, int cy, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset, int id) {
        this.cx = cx;
        this.cy = cy;
        this.orientation = orientation;
        this.approach = approach;
        this.lenght = lenght;
        this.roadWidth = roadWidth;
        this.laneCount = laneCount;
        this.stopLineOffset = stopLineOffset;
        this.id = idcounter++;
    }
    Road (int x, int y, Orientation orientation, Approach approach, int stopLineOffset, int id) {
        this.cx = x;
        this.cy = y;
        this.orientation = orientation;
        this.approach = approach;
        this.lenght = 600;
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = stopLineOffset;
        this.id = idcounter++;
    }
   Road (int x, int y, Approach approach, int id) {  
        this.cx = x;
        this.cy = y;
        this.orientation = (approach == Approach.NORTH || approach == Approach.SOUTH) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        this.approach = approach;
        this.lenght = 600;  
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = (approach == Approach.NORTH || approach == Approach.EAST) ? 200 : -200;
        this.id = idcounter++; 
   } 
   Road (int x, int y, int id) {
        this.cx = x;
        this.cy = y;
        this.orientation = (id == 1 || id == 2) ? Orientation.HORIZONTAL : Orientation.VERTICAL; 
        this.approach = (id == 1) ? Approach.SOUTH : (id == 2) ? Approach.NORTH : (id == 3) ? Approach.EAST : Approach.WEST;
        this.lenght = 600;  
        this.roadWidth = 200;
        this.laneCount = 2;
        this.stopLineOffset = (this.approach == Approach.SOUTH || this.approach == Approach.WEST) ? 200 : -200;
        this.id = idcounter++; 
   }    
}

