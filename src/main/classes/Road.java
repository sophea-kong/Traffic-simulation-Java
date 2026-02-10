import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

enum Orientation {
    HORIZONTAL, VERTICAL
}

enum Approach {
    NORTH, SOUTH, EAST, WEST
}

public class Road {
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
    private Color crosColor = new Color(255, 235, 235);

    Road(int cx, int cy, Orientation orientation, Approach approach, int lenght, int roadWidth,
            int laneCount, int stopLineOffset) {
        this.position = new Coordinate(cx, cy);
        this.orientation = orientation;
        this.approach = approach;
        this.lenght = lenght;
        this.roadWidth = roadWidth;
        this.laneCount = laneCount;
        this.stopLineOffset = stopLineOffset;
    }

    public Approach getApproach() {
        return this.approach;
    }


    public static void drawRoad(Graphics2D g2d, Road road) {
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
