package beziercurves;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.time.Clock;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Project 1
 * Students have to replace method drawBresenhamLine with an implementation
 * of the Bresenham algorithm.
 * @author htrefftz
 */
public class BezierCurves extends JPanel {

    public static final int STEP = 5;
    public static final int R = 200;
    public static int width;
    public static int height;

    /**
     * Draw the lines. This is called by Java whenever it is necessary to draw
     * (or redraw) the panel
     * @param g Graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        width = w;
        height = h;

        //Dibujar plano cartesiano
        MyPoint startPoint = new MyPoint(0,0);
        MyPoint endPoint = new MyPoint(0,0);
        
        //Eje Y
        
        startPoint.x = 0;
        startPoint.y = h/2;
        endPoint.x = 0;
        endPoint.y = -h/2;
        
        drawLine(startPoint, endPoint, g2d);
        
        //Eje X
        
        startPoint.x = w/2;
        startPoint.y = 0;
        endPoint.x = -w/2;
        endPoint.y = 0;

        drawLine(startPoint, endPoint, g2d);
        
        g2d.setColor(Color.red);
        
        //Llamar bezier
        
        MyPoint p0 = new MyPoint(-100,0);
        MyPoint p1 = new MyPoint(-30,200);
        MyPoint p2 = new MyPoint(30,-200);
        MyPoint p3 = new MyPoint(100,0);
        MyPoint[] points = {p0,p1,p2,p3};
        
        int n = 3;
        
        double valuex = 0d;
        double valuey = 0d;
        
        double oldvaluex = calcularBezier(0,n,0,points[0].x);
        double oldvaluey = calcularBezier(0,n,0,points[0].y);
        
        for(double u=0; u<=1; u+=0.01){
            for(int k=0; k <= n; k++){
                valuex += calcularBezier(k,n,u,points[k].x);
                valuey += calcularBezier(k,n,u,points[k].y);
            }
            
            startPoint.x = (int) oldvaluex;
            startPoint.y = (int) oldvaluey;
            endPoint.x = (int) valuex;
            endPoint.y = (int) valuey;
            
            drawLine(startPoint,endPoint,g2d);
            
            oldvaluex = valuex;
            oldvaluey = valuey;
            
            valuex = 0;
            valuey = 0;
            
        }
    }
    
    public int evaluarFuncion(int x) {
        return 0;
    }
    
    public double calcularBezier(int k, int n, double u, double p){
        double BEZ = cFunction(k,n);
        BEZ = BEZ * Math.pow(u,k);
        BEZ = BEZ * Math.pow(1-u,n-k);
        BEZ = BEZ * p;
        return BEZ;
    }
    
    public int cFunction(int k, int n){
        int nk = n-k;
        int divisor = factorial(k) * factorial(nk);
        return factorial(n)/divisor;
    }
    
    public int factorial(int x){
        if(x == 0) return 1;
        else return x*factorial(x-1);
    }

    /**
     * Transforms a point and then draws it on the panel
     * @param p point to be drawn
     * @param g2d graphics context
     */
    public void drawPoint(MyPoint p, Graphics2D g2d) {
        viewportTransf(p);
        g2d.drawLine(p.x, p.y, p.x, p.y);
    }
    
    public void drawLine(MyPoint p1, MyPoint p2, Graphics2D g2d) {
        viewportTransf(p1);
        viewportTransf(p2);
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * Transform a point to java coordinates: X grows from left to right and
     * Y grows from top to bottom
     * @param p Point to be transformed
     */
    public void viewportTransf(MyPoint p) {
        p.x += width / 2;
        p.y = height / 2 - p.y;
    }

    /**
     * Main program
     * @param args Not used in this case
     */
    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Bezier Curves");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new BezierCurves());
        // Asignarle tamaño
        frame.setSize(500, 500);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

}