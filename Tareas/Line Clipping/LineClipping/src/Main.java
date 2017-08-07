
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
    PolygonObject pobox;
    PolygonObject clpo;
    BoundaryBox box;

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
        g2d.setColor(Color.blue);
        drawEdge(g2d,new Point(0, -1000), new Point(0, 1000));
        g2d.setColor(Color.blue);
        drawEdge(g2d,new Point(-1000, 0), new Point(1000, 0));
    }
    
    /**
     * Draw the wire-frame object
     * @param g2d Graphics2D context
     */
    private void drawObject(Graphics2D g2d) {
        g2d.setColor(Color.red);
        for(Edge e: po.edges) {
            Point p0 = e.p1;
            Point p1 = e.p2;
            drawEdge(g2d, p0, p1);
        }
        g2d.setColor(Color.green);
        for(Edge e: clpo.edges) {
            Point p0 = e.p1;
            Point p1 = e.p2;
            drawEdge(g2d, p0, p1);
        }
        g2d.setColor(Color.black);
        for(Edge e: pobox.edges) {
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
        pobox = new PolygonObject();
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
            
            box = new BoundaryBox(LL,UR);
            
            Edge edge = new Edge(LR, LL);
            pobox.add(edge);
            
            edge = new Edge(LR, UR);
            pobox.add(edge);
            
            edge = new Edge(UL, UR);
            pobox.add(edge);
            
            edge = new Edge(UL, LL);
            pobox.add(edge);
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }

    private void clipLines() {
        clpo = new PolygonObject();
        
        int xMin = box.p1.x;
        int yMin = box.p1.y;
        int xMax = box.p2.x;
        int yMax = box.p2.y;
        
        for(Edge e: po.edges) {
            
            int x1 = e.p1.x;
            int y1 = e.p1.y;
            int x2 = e.p2.x;
            int y2 = e.p2.y;
            
            int dx = (x2 - x1);
            int dy = (y2 - y1);
            
            int p1 = -dx;
            int p2 = -p1;
            int p3 = -dy;
            int p4 = -p3;
            
            int p[] = {p1,p2,p3,p4};
            
            int q1 = x1 - xMin;
            int q2 = xMax - x1;
            int q3 = y1 - yMin;
            int q4 = yMax - y1;
            
            int q[] = {q1,q2,q3,q4};

            double u1 = 0;
            double u2 = 1;
            
            boolean out = false;
            
            for (int i = 0; i < 4; i++) {
                if (p[i] == 0) {
                    if (q[i] < 0) {
                        out = true;
                        break;
                    }
                } else {
                    double u = (double) q[i] / p[i];
                    if (p[i] < 0) {
                        u1 = Math.max(u, u1);
                    } else {
                        u2 = Math.min(u, u2);
                    }
                }
            }
            
            if (u1 > u2) {
                out = true;
            }
            
            if(!out){
                int nx1, ny1, nx2, ny2;
                nx1 = (int) (x1 + u1 * dx);
                ny1 = (int) (y1 + u1 * dy);
                nx2 = (int) (x1 + u2 * dx);
                ny2 = (int) (y1 + u2 * dy);

                Point ini = new Point(nx1, ny1);
                Point fin = new Point(nx2, ny2);

                Edge edge = new Edge(ini, fin);
                clpo.add(edge);
            }
        }
    }   
    
    /**
     * Main program
    */
    public static void main(String[] args) {
        Main m = new Main();

        // Read the file with the object description
        m.readObjectDescription("objeto.txt");
        //Aply Line Clipping Algorithm to divide edges
        m.clipLines();
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
