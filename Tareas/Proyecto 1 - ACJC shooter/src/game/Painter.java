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
import javax.swing.SwingWorker;

import game.objects.Map;
import game.objects.Projectile;
import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Painter extends JPanel implements KeyListener {
	
	Dimension screenSize;
	public static int width;
    public static int height;
	private static final long serialVersionUID = 1L;
	private Set<Integer> pressed = new HashSet<>();
	private Set<Integer> pressed2 = new HashSet<>();
	private Player redPlayer;
	private Player bluePlayer;
	private PolygonObject map;
	
	public Painter() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width;
		height = screenSize.height;
        this.addKeyListener(this);
        redPlayer = new RedPlayer();
        bluePlayer = new BluePlayer();
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
        drawObject(g2d,redPlayer.po,Color.red);
        drawObject(g2d,bluePlayer.po,Color.blue);
        for(Projectile proyectile : redPlayer.projectiles) {
        	drawObject(g2d,proyectile.model,Color.red);
        }
        for(Projectile proyectile : bluePlayer.projectiles) {
        	drawObject(g2d,proyectile.model,Color.blue);
        }
        drawObject(g2d,map,Color.white);
        if(redPlayer.destroyed) drawObject(g2d,map,Color.blue);
        if(bluePlayer.destroyed) drawObject(g2d,map,Color.red);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	pressed.add(e.getKeyCode());
    }	
	
	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
    public void startAnimation() {
        SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                	pressed2 = pressed;
                	for(int tecla: pressed2) {
                		if(!redPlayer.destroyed) {
            	    		if(tecla == KeyEvent.VK_D) {
            	    			redPlayer.controller.rotateRight();
            	            }if (tecla == KeyEvent.VK_A) {
            	            	redPlayer.controller.rotateLeft();
            	            }if (tecla == KeyEvent.VK_W) {
            	            	redPlayer.controller.moveForward();
            	            }if (tecla == KeyEvent.VK_S) {
            	            	redPlayer.controller.moveBackward();
            	            }if (tecla == KeyEvent.VK_SPACE) {
            	            	redPlayer.fireProjectile();
            	            }
                		}
                        
                		if(!bluePlayer.destroyed) {
            	    		if(tecla == KeyEvent.VK_RIGHT) {
            	    			bluePlayer.controller.rotateRight();
            	            }if (tecla == KeyEvent.VK_LEFT) {
            	            	bluePlayer.controller.rotateLeft();
            	            }if (tecla == KeyEvent.VK_UP) {
            	            	bluePlayer.controller.moveForward();
            	            }if (tecla == KeyEvent.VK_DOWN) {
            	            	bluePlayer.controller.moveBackward();
            	            }if (tecla == KeyEvent.VK_ENTER) {
            	            	bluePlayer.fireProjectile();
            	            }
                		}
                        
                	}
                	for(Projectile projectile : redPlayer.projectiles) {
                    	projectile.controller.moveForward();
                    	if(!projectile.neutralized && bluePlayer.isHit(projectile.model)) {
                    		bluePlayer.takeDamage();
                    		System.out.println("Blue Player Health:" + bluePlayer.health);
                    		projectile.neutralize();
                    	}
                    }
                    for(Projectile projectile : bluePlayer.projectiles) {
                    	projectile.controller.moveForward();
                    	if(!projectile.neutralized && redPlayer.isHit(projectile.model)) {
                    		redPlayer.takeDamage();
                    		System.out.println("red Player Health:" + bluePlayer.health);
                    		projectile.neutralize();
                    	}
                    }
                    repaint();
                    Thread.sleep(50);
                }
            }
        };

        sw.execute();
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


