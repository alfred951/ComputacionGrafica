package game;

import java.awt.Color;
import java.awt.Graphics2D;

import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Drawer {
	
	public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    
    public void drawObject(Graphics2D g2d, PolygonObject po) {
        g2d.setColor(Color.blue);
        for(Edge e: po.edges) {
            Point p1 = e.p1;
            Point p2 = e.p2;
            drawEdge(g2d, p1, p2);
        }
    }
    
    public void drawEdge(Graphics2D g2d,Point p0, Point p1) {
        int x0 = (int) (p0.x + FRAME_WIDTH/2);
        int y0 = (int) (FRAME_HEIGHT/2 - p0.y) ;
        int x1 = (int) (p1.x + FRAME_WIDTH/2);
        int y1 = (int) (FRAME_HEIGHT/2 - p1.y);
        g2d.drawLine(x0, y0, x1, y1);
    }
}
