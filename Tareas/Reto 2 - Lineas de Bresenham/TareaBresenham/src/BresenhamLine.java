/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class BresenhamLine extends JPanel {

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
        
        drawBresenhamLine3(g2d,startPoint,endPoint);
        
        //Eje X
        
        startPoint.x = w/2;
        startPoint.y = 0;
        endPoint.x = -w/2;
        endPoint.y = 0;
        
        drawBresenhamLine3(g2d,startPoint,endPoint);
        
        //Pintar la funcion uno a uno
        
        for(int i=-300;i<=300;i++){
            startPoint.x = i;
            startPoint.y = evaluarFuncion(i);
            endPoint.x = i+1;
            endPoint.y = evaluarFuncion(i+1);
            drawBresenhamLine3(g2d,startPoint,endPoint);
        }

        //Pintar en rojo la funcion con saltos de 5 en 5
        //Las lineas se hacen cada 200 puntos
        
        g2d.setColor(Color.red);
        
        for(int i=-300;i<=300;i=i+5){
            startPoint.x = i;
            startPoint.y = evaluarFuncion(i);
            endPoint.x = i+200;
            endPoint.y = evaluarFuncion(i+200);
            drawBresenhamLine3(g2d,startPoint,endPoint);
        }
        
    }
    
    public int evaluarFuncion(int x) {
        
        //TODO: Revisar funcion matematica f(x) = (x^2)/50
        //Nueva funcion f(x) = ||x||
        
        //double result = Math.pow(x, 2)/50;
        int result = Math.abs(x);
        return (int) result;

    }

    /** 
     * This has to be changed to an implementation of the Bresenham line
     * @param g2d graphics context
     * @param p1 beginning point of the line
     * @param p2 end point of the line
     */
    public void drawBresenhamLine3(Graphics2D g2d, MyPoint p1, MyPoint p2) {
        
        //Adaptado de https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#Java
        
        int d = 0;
 
        int dy = Math.abs(p2.y - p1.y);
        int dx = Math.abs(p2.x - p1.x);
        
        int ix;
        
        if(p1.x < p2.x){
            ix = 1;
        } else { 
            ix = - 1;
        }
        
        int iy;
        
        if(p1.y < p2.y){
            iy = 1;
        } else { 
            iy = - 1;
        } 
        
        MyPoint point = new MyPoint(0,0);
        
        if (dy <= dx) {
            for (;;) {
                point.x = p1.x;
                point.y = p1.y;
                drawPoint(point,g2d);
                if (p1.x == p2.x)
                    break;
                p1.x += ix;
                d += dy;
                if (d > dx) {
                    p1.y += iy;
                    d -= dx;
                }
            }
        } else {
            for (;;) {
                point.x = p1.x;
                point.y = p1.y;
                drawPoint(point,g2d);
                if (p1.y == p2.y)
                    break;
                p1.y += iy;
                d += dx;
                if (d > dy) {
                    p1.x += ix;
                    d -= dy;
                }
            }
        }
        
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
        JFrame frame = new JFrame("Bresenham");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new BresenhamLine());
        // Asignarle tamaño
        frame.setSize(500, 500);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

}
