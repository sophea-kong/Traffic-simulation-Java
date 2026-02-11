import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class DrawRoad {
    public static void drawRoad(Graphics2D g2d, Road road) {
        // Draw road background
        g2d.setColor(road.asphaltColor);
        
        if (road.orientation == Orientation.HORIZONTAL) {
            g2d.fillRect(road.cx - road.lenght / 2, road.cy - road.roadWidth / 2, road.lenght, road.roadWidth);
            
            // Draw lane markings
            g2d.setColor(road.laneMark);
            for (int i = road.roadWidth / road.laneCount; i < road.roadWidth; i += road.roadWidth / road.laneCount) {
                g2d.drawLine(road.cx - road.lenght / 2, road.cy - road.roadWidth / 2 + i, 
                            road.cx + road.lenght / 2, road.cy - road.roadWidth / 2 + i);
            }
        } else {
            g2d.fillRect(road.cx - road.roadWidth / 2, road.cy - road.lenght / 2, road.roadWidth, road.lenght);
            
            // Draw lane markings
            g2d.setColor(road.laneMark);
            for (int i = road.roadWidth / road.laneCount; i < road.roadWidth; i += road.roadWidth / road.laneCount) {
                g2d.drawLine(road.cx - road.roadWidth / 2 + i, road.cy - road.lenght / 2,
                            road.cx - road.roadWidth / 2 + i, road.cy + road.lenght / 2);
            }
        }

        // Draw stop line
        g2d.setColor(road.stopLineColor);
        if (road.orientation == Orientation.HORIZONTAL) {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(road.cx + road.stopLineOffset, road.cy - road.roadWidth / 2,
                        road.cx + road.stopLineOffset, road.cy + road.roadWidth / 2);
        } else {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(road.cx - road.roadWidth / 2, road.cy + road.stopLineOffset,
                        road.cx + road.roadWidth / 2, road.cy + road.stopLineOffset);
        }
    }
}