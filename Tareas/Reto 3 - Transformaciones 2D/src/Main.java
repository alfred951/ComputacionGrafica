
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author htrefftz
 */
public class Main extends JPanel implements KeyListener {

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    
    PolygonObject po;
    
    int minX;
    int minY;
    int maxX;
    int maxY;
    
    boolean DEBUG = true;

    /**
     * Draw the axis and the object
     * @param g 
     */
        @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // El panel, por defecto no es "focusable". 
        // Hay que incluir estas líneas para que el panel pueda
        // agregarse como KeyListsener.
        this.setFocusable(true);
        this.requestFocusInWindow();


        this.addKeyListener(this);

        Graphics2D g2d = (Graphics2D) g;
        drawAxis(g2d);
        drawObject(g2d);
        drawClippingArea(g2d);
    }

    /**
     * Draw the X and Y axis
     * @param g2d Graphics2D context
     */
    private void drawAxis(Graphics2D g2d) {
        g2d.setColor(Color.red);
        drawEdge(g2d,new Point(0, -100), new Point(0, 100));
        g2d.setColor(Color.green);
        drawEdge(g2d,new Point(-100, 0), new Point(100, 0));
    }
    
    private void drawClippingArea(Graphics2D g2d) {
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
    
    /**
     * Draw the wire-frame object
     * @param g2d Graphics2D context
     */
    private void drawObject(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        for(Edge e: po.edges) {
            Point p0 = e.p1;
            Point p1 = e.p2;
            drawEdge(g2d, p0, p1);
        }
    }

    /**
     * Draw an edge from p0 to p1
     * p0 and p1 are in world coordinates, need to be tranformed
     * to the viewpoint.
     * @param g2d Graphics2D context
     * @param p0 first point
     * @param p1 second point
     */
    private void drawEdge(Graphics2D g2d,Point p0, Point p1) {
        int x0 = p0.x + FRAME_WIDTH/2;
        int y0 = FRAME_HEIGHT/2 - p0.y ;
        int x1 = p1.x + FRAME_WIDTH/2;
        int y1 = FRAME_HEIGHT/2 - p1.y;
        g2d.drawLine(x0, y0, x1, y1);
    }

    /**
     * Read the object description:
     * n (number of vertices)
     * n vertices
     * m (number of edges)
     * m edges (index of first and second point to be linked)
     * @param fileName Name of the file to read the object description from
     */
    public void readObjectDescription(String fileName) {
        Scanner in;
        po = new PolygonObject();
        try {
            in = new Scanner(new File(fileName));
            // Read the number of vertices
            int numVertices = in.nextInt();
            Point[] vertexArray = new Point[numVertices];
            // Read the vertices
            for (int i = 0; i < numVertices; i++) {
                // Read a vertex
                int x = in.nextInt();
                int y = in.nextInt();
                vertexArray[i] = new Point(x, y);
            }
            // Read the number of edges
            int numEdges = in.nextInt();
            // Read the edges
            for (int i = 0; i < numEdges; i++) {
                // Read an edge
                int start = in.nextInt();
                int end = in.nextInt();
                Edge edge = new Edge(vertexArray[start], vertexArray[end]);
                po.add(edge);
            }
            // Read clipping area
            minX = in.nextInt();
            minY = in.nextInt();
            maxX = in.nextInt();
            maxY = in.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
        	matrixObject.matrix[0][0] = 1.01;
        	matrixObject.matrix[1][1] = 1.01;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_X) {
        	matrixObject.matrix[0][0] = 0.99;
        	matrixObject.matrix[1][1] = 0.99;
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_O) {
        	matrixObject.matrix[0][0] = Math.cos(Math.toRadians(1));
        	matrixObject.matrix[0][1] = -Math.sin(Math.toRadians(1));
        	matrixObject.matrix[1][0] = Math.sin(Math.toRadians(1));
        	matrixObject.matrix[1][1] = Math.cos(Math.toRadians(1));
        	matrixObject.printMatrix();
        } else if (tecla == KeyEvent.VK_L) {
        	matrixObject.matrix[0][0] = Math.cos(Math.toRadians(-1));
        	matrixObject.matrix[0][1] = -Math.sin(Math.toRadians(-1));
        	matrixObject.matrix[1][0] = Math.sin(Math.toRadians(-1));
        	matrixObject.matrix[1][1] = Math.cos(Math.toRadians(-1));	
        }
        
        for(Edge edge: po.edges) {
        	Vector3 v1 = edge.p1.pointToVector();
        	Vector3 v2 = edge.p2.pointToVector();
        	v1 = matrixObject.times(v1);
        	v2 = matrixObject.times(v2);
        	edge.p1 = v1.point.pointWToPoint();
        	edge.p2 = v2.point.pointWToPoint();
        }
        
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    
    
    
    /**
     * Main program
    */
    public static void main(String[] args) {
        Main m = new Main();

        // Read the file with the object description
        m.readObjectDescription("objeto.txt");

        // Create a new Frame
        JFrame frame = new JFrame("Wire Frame Object");
        // Upon closing the frame, the application ends
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add a panel called DibujarCasita3D
        frame.add(m);

        // Asignarle tamaño
        frame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        // Put the frame in the middle of the window
        frame.setLocationRelativeTo(null);
        // Show the frame
        frame.setVisible(true);
    }

}
