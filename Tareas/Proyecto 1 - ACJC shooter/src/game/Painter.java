package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;

import math.Matrix3x3;
import math.Vector3;
import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Painter extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	Drawer drawer;
	
	public Painter() {
		this.drawer = new Drawer();
	}
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addKeyListener(this);

        Graphics2D g2d = (Graphics2D) g;
        drawer.drawObject(g2d);
    }
    
    public void readObjectDescription(String fileName) {
        Scanner in;
        drawer.po = new PolygonObject();
        try {
            in = new Scanner(new File(fileName));
            int numVertices = in.nextInt();
            Point[] vertexArray = new Point[numVertices];           
            for (int i = 0; i < numVertices; i++) {                
                int x = in.nextInt();
                int y = in.nextInt();
                vertexArray[i] = new Point(x, y);
            }
            int numEdges = in.nextInt();
            for (int i = 0; i < numEdges; i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                Edge edge = new Edge(vertexArray[start], vertexArray[end]);
                drawer.po.addEdge(edge);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	int tecla = e.getKeyCode();
    	double[][] m3 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    	Matrix3x3 matrixObject = new Matrix3x3(m3); 
        if(tecla == KeyEvent.VK_D) {
            matrixObject.matrix[0][2] = 1;
            matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_A) {
        	matrixObject.matrix[0][2] = -1;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_W) {
        	matrixObject.matrix[1][2] = 1;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_S) {
        	matrixObject.matrix[1][2] = -1;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_Z) {
        	matrixObject.matrix[0][0] = 1.001;
        	matrixObject.matrix[1][1] = 1.001;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_X) {
        	matrixObject.matrix[0][0] = 0.999;
        	matrixObject.matrix[1][1] = 0.999;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_O) {
        	matrixObject.matrix[0][0] = Math.cos(Math.toRadians(0.1));
        	matrixObject.matrix[0][1] = -Math.sin(Math.toRadians(0.1));
        	matrixObject.matrix[1][0] = Math.sin(Math.toRadians(0.1));
        	matrixObject.matrix[1][1] = Math.cos(Math.toRadians(0.1));
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_L) {
        	matrixObject.matrix[0][0] = Math.cos(Math.toRadians(-0.1));
        	matrixObject.matrix[0][1] = -Math.sin(Math.toRadians(-0.1));
        	matrixObject.matrix[1][0] = Math.sin(Math.toRadians(-0.1));
        	matrixObject.matrix[1][1] = Math.cos(Math.toRadians(-0.1));	
        }
        
        for(Edge edge: drawer.po.edges) {
        	Vector3 v1 = edge.p1.pointToVector();
        	Vector3 v2 = edge.p2.pointToVector();
        	v1 = matrixObject.times(v1);
        	v2 = matrixObject.times(v2);
        	edge.p1 = v1.point;
        	edge.p2 = v2.point;
        }
        
        repaint();
    }	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
