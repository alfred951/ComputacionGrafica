
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
    PolygonObject tempo;
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
        g2d.setColor(Color.green);
        for(Edge e: po.edges) {
            Point p0 = e.p1;
            Point p1 = e.p2;
            drawEdge(g2d, p0, p1);
        }
        g2d.setColor(Color.red);
        for(Edge e: tempo.edges) {
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
        tempo = new PolygonObject();
        float x1 = box.p1.x;
        float y1 = box.p1.y;
        float x2 = box.p2.x;
        float y2 = box.p2.y;
//        float xmin = box.p1.x;
//        float ymin = box.p1.y;
//        float xmax = box.p2.x;
//        float ymax = box.p2.y;
        for(Edge e: po.edges) {
            float xmin = e.p1.x;
            float ymin = e.p1.y;
            float xmax = e.p2.x;
            float ymax = e.p2.y;
            
//            float x1 = e.p1.x;
//            float y1 = e.p1.y;
//            float x2 = e.p2.x;
//            float y2 = e.p2.y;
            
            float p1 = -(x2-x1);
            float p2 = -p1;
            float p3 = -(y2 - y1);
            float p4 = -p3;
            
            float q1 = x1 - xmin;
            float q2 = xmax - x1;
            float q3 = y1 - ymin;
            float q4 = ymax - y1;

            float[] posarr = new float[5];
            float[] negarr = new float[5];
            int posind = 1, negind = 1;
            posarr[0] = 1;
            negarr[0] = 0;
            
            //po.edges.remove(e);
            
            if ((p1 == 0 && q1 < 0) || (p3 == 0 && q3 < 0)) {
                break;
            }
            if (p1 != 0) {
              float r1 = q1 / p1;
              float r2 = q2 / p2;
              if (p1 < 0) {
                negarr[negind++] = r1; // for negative p1, add it to negative array
                posarr[posind++] = r2; // and add p2 to positive array
              } else {
                negarr[negind++] = r2;
                posarr[posind++] = r1;
              }
            }
            if (p3 != 0) {
              float r3 = q3 / p3;
              float r4 = q4 / p4;
              if (p3 < 0) {
                negarr[negind++] = r3;
                posarr[posind++] = r4;
              } else {
                negarr[negind++] = r4;
                posarr[posind++] = r3;
              }
            }

            int xn1, yn1, xn2, yn2;
            float rn1, rn2;
            rn1 = maxi(negarr, negind); // maximum of negative array
            rn2 = mini(posarr, posind); // minimum of positive array

            xn1 = (int) (x1 + p2 * rn1);
            yn1 = (int) (y1 + p4 * rn1); // computing new points
            
            xn2 = (int) (x1 + p2 * rn2);
            yn2 = (int) (y1 + p4 * rn2);
            
            Point iniClipLine = new Point(xn1, yn1);
            Point endClipLine = new Point(xn2, yn2);
            
            Edge edge = new Edge(iniClipLine, iniClipLine);
            clpo.add(edge);
            
            Point iniLine = new Point((int)x1, (int)y1);
            Point endLine = new Point(xn1, yn1);
            
            edge = new Edge(iniLine,endLine);
            tempo.add(edge);
            
            iniLine = new Point((int)x2, (int)y2);
            endLine = new Point(xn2, yn2);
            
            edge = new Edge(iniLine,endLine);
            tempo.add(edge);
            
        }
    }
    
    // this function gives the maximum
    public float maxi(float arr[],int n) {
        float m = 0;
        for (int i = 0; i < n; ++i)
            if (m < arr[i])
                m = arr[i];
        return m;
    }

    // this function gives the minimum
    public float mini(float arr[], int n) {
        float m = 1;
        for (int i = 0; i < n; ++i)
            if (m > arr[i])
                m = arr[i];
        return m;
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
