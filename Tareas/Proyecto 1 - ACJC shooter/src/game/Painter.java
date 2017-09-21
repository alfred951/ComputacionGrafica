package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import game.objects.Map;
import math.Escalation;
import math.Matrix3x3;
import math.Rotation;
import math.Translation;
import math.Vector3;
import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Painter extends JPanel implements KeyListener {
	
	Dimension screenSize;
	public static int width;
    public static int height;
	private static final long serialVersionUID = 1L;
	private final Set<Integer> pressed = new HashSet<>();
	private PolygonObject rp;
	private PolygonObject bp;
	private PolygonObject map;
	
	public Painter() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width;
		height = screenSize.height;
        this.addKeyListener(this);
        rp = new RedPlayer().po;
        bp = new BluePlayer().po;
        map = new Map(height/2,width/2).po;
	}
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setBackground(Color.black);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(10));
        drawObject(g2d,rp,Color.red);
        drawObject(g2d,bp,Color.blue);
        drawObject(g2d,map,Color.white);
    }
    
    @Override
    public synchronized void keyPressed(KeyEvent e) {
    	pressed.add(e.getKeyCode());
    	double[][] m3 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    	Matrix3x3 matrixObject = new Matrix3x3(m3); 
    	for(int tecla: pressed) {
    		if(tecla == KeyEvent.VK_D) {
    			Translation trans = new Translation(5, 0);
                matrixObject.matrix = trans.matrix;
            }if (tecla == KeyEvent.VK_A) {
            	Translation trans = new Translation(-5, 0);
            	matrixObject.matrix = trans.matrix;            	
            }if (tecla == KeyEvent.VK_W) {
            	Translation trans = new Translation(0, 5);
            	matrixObject.matrix = trans.matrix;
            }if (tecla == KeyEvent.VK_S) {
            	Translation trans = new Translation(0, -5);
            	matrixObject.matrix = trans.matrix;
            }if (tecla == KeyEvent.VK_Z) {
            	Escalation escal = new Escalation(1.01, 1.01);
            	matrixObject.matrix = escal.matrix;
            }if (tecla == KeyEvent.VK_X) {
            	Escalation escal = new Escalation(0.99, 0.99);
            	matrixObject.matrix = escal.matrix;
            }if (tecla == KeyEvent.VK_O) {
            	double tempX = rp.getXCenter();
            	double tempY = rp.getYCenter();
            	matrixObject.matrix[0][2] = -tempX;         
            	matrixObject.matrix[1][2] = -tempY;
            	applyProjection(matrixObject);
            	matrixObject.setMatrix(); 
            	Rotation rot = new Rotation(5);
            	matrixObject.matrix  = rot.matrix;
            	applyProjection(matrixObject);            
            	matrixObject.setMatrix(); 
            	matrixObject.matrix[0][2] = tempX;         
            	matrixObject.matrix[1][2] = tempY;
            }if (tecla == KeyEvent.VK_L) {
            	double tempX = rp.getXCenter();
            	double tempY = rp.getYCenter();
            	matrixObject.matrix[0][2] = -tempX;         
            	matrixObject.matrix[1][2] = -tempY;
            	applyProjection(matrixObject);
            	matrixObject.setMatrix(); 
            	Rotation rot = new Rotation(-5);
            	matrixObject.matrix  = rot.matrix;
            	applyProjection(matrixObject);            
            	matrixObject.setMatrix(); 
            	matrixObject.matrix[0][2] = tempX;         
            	matrixObject.matrix[1][2] = tempY;	
            }
        	applyProjection(matrixObject);
    	}
        
        repaint();
    }	
	
	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public void applyProjection(Matrix3x3 matrixObject) {
        for(Edge edge: rp.edges) {
        	Vector3 v1 = edge.p1.pointToVector();
        	Vector3 v2 = edge.p2.pointToVector();
        	v1 = matrixObject.times(v1);
        	v2 = matrixObject.times(v2);
        	edge.p1 = v1.point;
        	edge.p2 = v2.point;
        	System.out.println(edge.p1.toString());
        }
	}
	
    public void drawObject(Graphics2D g2d, PolygonObject po, Color color) {
        g2d.setColor(color);
        for(Edge e: po.edges) {
            Point p1 = e.p1;
            Point p2 = e.p2;
            drawEdge(g2d, p1, p2);
        }
    }
    
    public void drawEdge(Graphics2D g2d,Point p0, Point p1) {
        int x0 = (int) (p0.x + width/2);
        int y0 = (int) (height/2 - p0.y) ;
        int x1 = (int) (p1.x + width/2);
        int y1 = (int) (height/2 - p1.y);
        g2d.drawLine(x0, y0, x1, y1);
    }
}


