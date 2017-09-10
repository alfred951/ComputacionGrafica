package Geometry;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import Math.Matrix4x4;
import Math.Vector4;
import Math.Projection;
import Math.Translation;
import Math.Uvn;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This example reads the description of an object (a polygon) from a file
 * and draws it on a jPanel.
 * 
 * 
 * @author htrefftz
 */
public class DibujarCasita3D extends JPanel implements KeyListener {
    
    public static final boolean DEBUG = true;

    /**
     * Original (untransformed) PolygonObject
     */
    PolygonObject po;
    /**
     * Transformed object to be drawn
     */
    PolygonObject transformedObject;
    
    /**
     * Current transformations.
     * This is the accumulation of transformations done to the object
     */
    Matrix4x4 currentTransformation = new Matrix4x4();

    public static int FRAME_WIDTH = 600;
    public static int FRAME_HEIGHT = 400;
    
    public static int AXIS_SIZE = 20;

    Dimension size;
    Graphics2D g2d;
    /**
     * Distance to the projection plane.
     */
    int proyectionPlaneDistance;
    
    /**
     * Center of the object
     */
    double maxX;
    double minX;
    double maxY;
    double minY;
    double maxZ;
    double minZ;
    double centerX;
    double centerY;
    double centerZ;
    
    /**
     * Position of the camera in spherical coordinates
     */
    double theta = 0;
    double phi = 0;
    double radius = 500;
    
    /**
     * Increments
     */
    public static final double THETA_INCREMENT = Math.PI / 18d;
    public static final double PHI_INCREMENT = Math.PI / 18d;
    
    /**
     * This method draws the object.
     * The graphics context is received in variable Graphics.
     * It is necessary to cast the graphics context into Graphics 2D in order
     * to use Java2D.
     * @param g Graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        // Size of the window.
        size = getSize();
        
        // Draw the X axis
        g2d.setColor(Color.RED);
        drawOneLine(-DibujarCasita3D.AXIS_SIZE, 0, DibujarCasita3D.AXIS_SIZE, 0);

        // Draw the Y axis
        g2d.setColor(Color.GREEN);
        drawOneLine(0, -DibujarCasita3D.AXIS_SIZE, 0, DibujarCasita3D.AXIS_SIZE);

        // Draw the polygon object
        g2d.setColor(Color.BLUE);
        //po.drawObject(this);
        
        // Transform the object
        transformObject();
        
        // Apply UVN matrix
        applyUVN();
        
        // Apply projection
        applyProjection();

        // Draw the object
        transformedObject.drawObject(this);
        
    }
    
    /**
     * Apply the current transformation to the original object.
     * currentTransformation is the accumulation of the transforms that
     * the user has entered.
     */
    private void transformObject() {
        transformedObject = PolygonObject.transformObject(po, currentTransformation);
    }
    
    /**
     * Based on the position and orientation of the camera, create and apply
     * the UVN matrix.
     */
    private void applyUVN() {
        double yCamera = radius * Math.sin(phi) + centerY;
        double projectedR = radius * Math.cos(phi);
        double xCamera = projectedR * Math.sin(theta) + centerX;
        double zCamera = projectedR * Math.cos(theta) + centerZ;
        
        
        Vector4 cameraPos = new Vector4(xCamera, yCamera, zCamera);
        Vector4 objectCenter = new Vector4(centerX, centerY, centerZ);
        Vector4 V = new Vector4(0, 1, 0);

        Uvn uvnMat = new Uvn(cameraPos, objectCenter, V);
        
        transformedObject = PolygonObject.transformObject(transformedObject, uvnMat);
    }
    
    /**
     * Create and apply the projection matrix
     */
    private void applyProjection() {
        Projection proj = new Projection(- proyectionPlaneDistance);
        transformedObject = PolygonObject.transformObject(transformedObject, proj);
    }

    /**
     * This function draws one line on this JPanel.
     * A mapping is done in order to:
     * - Have the Y coordinate grow upwards
     * - Have the origin of the coordinate system in the middle of the panel
     *
     * @param x1 Starting x coordinate of the line to be drawn
     * @param y1 Starting y coordinate of the line to be drawn
     * @param x2 Ending x coordinate of the line to be drawn
     * @param y2 Ending x coordinate of the line to be drawn
     */
    public void drawOneLine(int x1, int y1, int x2, int y2) {

        x1 = x1 + size.width / 2;
        x2 = x2 + size.width / 2;

        y1 = size.height / 2 - y1;
        y2 = size.height / 2 - y2;

        g2d.drawLine(x1, y1, x2, y2);
    }

    /**
     * Read the description of the object from the given file
     * @param fileName Name of the file with the object description
     */
    public void readObjectDescription(String fileName) {
        Scanner in;
        po = new PolygonObject();
        try {
            in = new Scanner(new File(fileName));
            // Read the number of vertices
            int numVertices = in.nextInt();
            Vector4[] vertexArray = new Vector4[numVertices];
            // Read the vertices
            for (int i = 0; i < numVertices; i++) {
                // Read a vertex
                int x = in.nextInt();
                int y = in.nextInt();
                int z = in.nextInt();
                vertexArray[i] = new Vector4(x, y, z);
                if(i == 0) {
                    initializeMaxMin(vertexArray[i]);
                } else {
                    updateMaxMin(vertexArray[i]);
                }
            }
            // Compute the center of the object
            computeCenter();
            // Read the number of edges
            int numEdges = in.nextInt();
            // Read the edges
            for (int i = 0; i < numEdges; i++) {
                // Read an edge
                int start = in.nextInt();
                int end = in.nextInt();
                Edge edge = new Edge(vertexArray[start], vertexArray[end]);
                po.addEdge(edge);
            }
            // Read the Project Plane Distance to the virtual camera
            proyectionPlaneDistance = in.nextInt();
            radius = proyectionPlaneDistance;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }

    /**
     * Prepare to find the minimum and maximum corners of the object
     * @param v 
     */
    private void initializeMaxMin(Vector4 v) {
        minX = v.getX();
        maxX = v.getX();
        minY = v.getY();
        maxY = v.getY();
        minZ = v.getZ();
        maxZ = v.getZ();
    }
    
    /**
     * Update the minimum and maximum corners of the object
     * @param v 
     */
    private void updateMaxMin(Vector4 v) {
        if(v.getX() > maxX) maxX = v.getX();
        if(v.getY() > maxY) maxY = v.getY();
        if(v.getZ() > maxZ) maxZ = v.getZ();
        if(v.getX() < minX) minX = v.getX();
        if(v.getY() < minY) minY = v.getY();
        if(v.getZ() < minZ) minZ = v.getZ();
    }
    
    /**
     * Compute the middle of the object
     */
    private void computeCenter() {
        centerX = (minX + maxX) / 2;
        centerY = (minY + maxY) / 2;
        centerZ = (minZ + maxZ) / 2;
    }
    
    @Override
  public void keyReleased(KeyEvent ke) {
      System.out.println("Key Released");      
      repaint();
  }
  
    @Override
  public void keyPressed(KeyEvent ke) {
      System.out.println("Key Pressed");
      if(ke.getKeyCode() == KeyEvent.VK_A) {        // Left
        Translation trans = new Translation(-10, 0, 0);
        currentTransformation = Matrix4x4.times(currentTransformation, trans);
      } else if(ke.getKeyCode() == KeyEvent.VK_D) { // Right
        Translation trans = new Translation(10, 0, 0);
        currentTransformation = Matrix4x4.times(currentTransformation, trans);
      } else if(ke.getKeyCode() == KeyEvent.VK_W) { // Up
        Translation trans = new Translation(0, 10, 0);
        currentTransformation = Matrix4x4.times(currentTransformation, trans);
      } else if(ke.getKeyCode() == KeyEvent.VK_S) { // Down
        Translation trans = new Translation(0, -10, 0);
        currentTransformation = Matrix4x4.times(currentTransformation, trans);
      } else if(ke.getKeyCode() == KeyEvent.VK_R) { // Reset
        currentTransformation = new Matrix4x4();
      } else if(ke.getKeyCode() == KeyEvent.VK_J) { // change longitude
        theta -= THETA_INCREMENT;
        if(theta <= - Math.PI) theta = - Math.PI;
      } else if(ke.getKeyCode() == KeyEvent.VK_L) { // change longitude
        theta += THETA_INCREMENT;
        if(theta >= Math.PI) theta = Math.PI;
      } else if(ke.getKeyCode() == KeyEvent.VK_I) { // change latitude
        phi += PHI_INCREMENT;
        if(phi >= Math.PI / 2) phi =  Math.PI / 2 - PHI_INCREMENT;
      } else if(ke.getKeyCode() == KeyEvent.VK_K) { // change latitude
        phi -= PHI_INCREMENT;
        if(phi <= - Math.PI / 2) phi = - Math.PI / 2 + PHI_INCREMENT;
      }
  } 
  
    @Override
  public void keyTyped(KeyEvent ke) {
      System.out.println("Key Typed");
  }
  
    
    /**
     * Create the frame, create the panel, add the panel to the frame,
     * make everything vissible.
     * @param args 
     */
    public static void main(String[] args) {
        DibujarCasita3D dc = new DibujarCasita3D();

        // Read the file with the object description
        dc.readObjectDescription("objeto3D.txt");

        // Create a new Frame
        JFrame frame = new JFrame("Wire Frame Object");
        // Upon closing the frame, the application ends
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add a panel called DibujarCasita3D
        frame.add(dc);
        // DibujarCasita will respond to the key events
        frame.addKeyListener(dc);
        
        // Asignarle tamaño
        frame.setSize(DibujarCasita3D.FRAME_WIDTH, DibujarCasita3D.FRAME_HEIGHT);
        // Put the frame in the middle of the window
        frame.setLocationRelativeTo(null);
        // Show the frame
        frame.setVisible(true);
    }
}
