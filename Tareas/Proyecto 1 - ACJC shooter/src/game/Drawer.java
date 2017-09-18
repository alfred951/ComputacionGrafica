package game;

import java.awt.Color;
import java.awt.Graphics2D;

import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Drawer {
	
	public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    
    int minX;
    int minY;
    int maxX;
    int maxY;
    
    public PolygonObject po;

	public void drawAxis(Graphics2D g2d) {
        g2d.setColor(Color.red);
        drawEdge(g2d,new Point(0, -100), new Point(0, 100));
        g2d.setColor(Color.green);
        drawEdge(g2d,new Point(-100, 0), new Point(100, 0));
    }
    
    public void drawClippingArea(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        Point p0 = new Point(minX, minY);
        Point p1 = new Point(maxX, minY);
        Point p2 = new Point(maxX, maxY);
        Point p3 = new Point(minX, maxY);
        drawEdge(g2d, p0, p1);
        drawEdge(g2d, p1, p2);
        drawEdge(g2d, p2, p3);
        drawEdge(g2d, p3, p0);
    }
    
    public void drawObject(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        for(Edge e: po.edges) {
            Point p0 = e.p1;
            Point p1 = e.p2;
            drawEdge(g2d, p0, p1);
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
