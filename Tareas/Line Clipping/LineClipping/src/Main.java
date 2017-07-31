
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author htrefftz
 */
public class Main extends JPanel {

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    
    PolygonObject po;

    /**
     * Draw the axis and the object
     * @par am g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawAxis(g2d);
        drawObject(g2d);
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
            
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            
            Point LR = new Point(x2, y1);
            Point LL = new Point(x1, y1);
            Point UR = new Point(x2, y2);
            Point UL = new Point(x1, y2);
            
            Edge edge = new Edge(LR, LL);
            po.add(edge);
            
            edge = new Edge(LR, UR);
            po.add(edge);
            
            edge = new Edge(UL, UR);
            po.add(edge);
            
            edge = new Edge(UL, LL);
            po.add(edge);
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

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
