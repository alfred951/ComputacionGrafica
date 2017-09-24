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
import math.Matrix3x3;

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
        RedPlayer redPlayer = new RedPlayer();
        rp = redPlayer.po;
        BluePlayer bluePlayer = new BluePlayer();
        bp = bluePlayer.po;
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
    public void keyPressed(KeyEvent e) {
    	pressed.add(e.getKeyCode());
    	double[][] m3 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    	Matrix3x3 redPlayerMove = new Matrix3x3(m3);
    	Matrix3x3 bluePlayerMove = new Matrix3x3(m3);
    	for(int tecla: pressed) {
    		
    		if(tecla == KeyEvent.VK_D) {
    			redPlayerMove = Controller.moveRight(redPlayerMove);
            }if (tecla == KeyEvent.VK_A) {
            	redPlayerMove = Controller.moveLeft(redPlayerMove);
            }if (tecla == KeyEvent.VK_W) {
            	redPlayerMove = Controller.moveUp(redPlayerMove);
            }if (tecla == KeyEvent.VK_S) {
            	redPlayerMove = Controller.moveDown(redPlayerMove);
            }if (tecla == KeyEvent.VK_O) {
            	redPlayerMove = Controller.rotateLeft(redPlayerMove, rp);
            }if (tecla == KeyEvent.VK_L) {
            	redPlayerMove = Controller.rotateRight(redPlayerMove, rp);
            } Controller.applyProjection(redPlayerMove, rp);
        	
    		if(tecla == KeyEvent.VK_RIGHT) {
    			bluePlayerMove = Controller.moveRight(bluePlayerMove);
            }if (tecla == KeyEvent.VK_LEFT) {
            	bluePlayerMove = Controller.moveLeft(bluePlayerMove);
            }if (tecla == KeyEvent.VK_UP) {
            	bluePlayerMove = Controller.moveUp(bluePlayerMove);
            }if (tecla == KeyEvent.VK_DOWN) {
            	bluePlayerMove = Controller.moveDown(bluePlayerMove);
            }if (tecla == KeyEvent.VK_1) {
            	bluePlayerMove = Controller.rotateLeft(bluePlayerMove, bp);
            }if (tecla == KeyEvent.VK_2) {
            	bluePlayerMove = Controller.rotateRight(bluePlayerMove, bp);
            } Controller.applyProjection(bluePlayerMove, bp);
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


