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

import Math.Escalation;
import Math.Matrix4x4;
import Math.Vector4;
import Math.Projection;
import Math.Translation;
import Math.Rotation;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This example reads the description of an object (a polygon) from a file
 * and draws it on a jPanel
 * 
 * @author htrefftz
 */
public class DibujarCasita3D extends JPanel implements KeyListener {

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
        
    }
    
    /**
     * Create and apply the projection matrix
     * First: create the projection matrix.
     * Then 
     * The parameter is the negative value of the distance from the
     * origin to the projection plane (see constant above)
     */
    private void applyProjection() {
        // Create the projection matrix
        // The parameter is the negative value of the
        // distance from the origin to the projection plane
        // Apply the projection using method transformObject in PolygonObject
        // The input object is transformedObject
        // The output object is also transformedObject
        // The transformation to be applied is the projection matrix
        // just created
    	//Projection proj = new Projection(-500);
    	//currentTransformation = Matrix4x4.times(currentTransformation, proj);
    	//transformedObject = PolygonObject.transformObject(po, currentTransformation);
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
            }
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
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

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
      } else if(ke.getKeyCode() == KeyEvent.VK_I) { // Rotation x
    	  Rotation rotation = new Rotation('x', Math.toRadians(10));
          currentTransformation = Matrix4x4.times(currentTransformation, rotation);
      } else if(ke.getKeyCode() == KeyEvent.VK_O) { // Rotation -x
    	  Rotation rotation = new Rotation('x', Math.toRadians(-10));
          currentTransformation = Matrix4x4.times(currentTransformation, rotation);
      } else if(ke.getKeyCode() == KeyEvent.VK_J) { // Rotation y
    	  Rotation rotation = new Rotation('y', Math.toRadians(10));
          currentTransformation = Matrix4x4.times(currentTransformation, rotation);
      } else if(ke.getKeyCode() == KeyEvent.VK_K) { // Rotation -y
    	  Rotation rotation = new Rotation('y', Math.toRadians(-10));
          currentTransformation = Matrix4x4.times(currentTransformation, rotation);
      } else if(ke.getKeyCode() == KeyEvent.VK_N) { // Rotation z
    	  Rotation rotation = new Rotation('z', Math.toRadians(10));
          currentTransformation = Matrix4x4.times(currentTransformation, rotation);
      } else if(ke.getKeyCode() == KeyEvent.VK_M) { // Rotation -z
    	  Rotation rotation = new Rotation('z', Math.toRadians(10));
          currentTransformation = Matrix4x4.times(currentTransformation, rotation);
      } else if(ke.getKeyCode() == KeyEvent.VK_Z) { // Escalation Up
    	  Escalation escalation = new Escalation(2, 2, 2);
          currentTransformation = Matrix4x4.times(currentTransformation, escalation);
      } else if(ke.getKeyCode() == KeyEvent.VK_X) { // Escalation Down
    	  Escalation escalation = new Escalation(0.5, 0.5, 0.5);
          currentTransformation = Matrix4x4.times(currentTransformation, escalation);
      } else if(ke.getKeyCode() == KeyEvent.VK_R) { // Reset
        currentTransformation = new Matrix4x4();
      }
      
      repaint();
  } 
  
    @Override
  public void keyTyped(KeyEvent ke) {
      System.out.println("Key Typed");
  }
  
    
    
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
