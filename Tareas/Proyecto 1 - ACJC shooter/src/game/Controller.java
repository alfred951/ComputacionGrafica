package game;

import math.Matrix3x3;
import math.Rotation;
import math.Translation;
import math.Vector3;
import utils.Edge;

public class Controller {
	
	public Player player;
	public double speed;
	
	public Controller(Player assingedPlayer) {
		this.player = assingedPlayer;
		speed = 4d;
	}
	
	public void moveForward() {
		Matrix3x3 transformation = new Matrix3x3();
		double directionX = Math.cos(Math.toRadians(player.direction))*speed;
		double directionY = Math.sin(Math.toRadians(player.direction))*speed;
		Translation trans = new Translation(directionX, directionY);
        transformation.matrix = trans.matrix;
        applyProjection(transformation); 
	}
	
	public void moveBackward() {
		Matrix3x3 transformation = new Matrix3x3();
		double directionX = -Math.cos(Math.toRadians(player.direction))*speed;
		double directionY = -Math.sin(Math.toRadians(player.direction))*speed;
		Translation trans = new Translation(directionX, directionY);
        transformation.matrix = trans.matrix;
        applyProjection(transformation); 
	}
	
	public void moveRight() {
		Matrix3x3 transformation = new Matrix3x3();
		Translation trans = new Translation(5, 0);
        transformation.matrix = trans.matrix;
        applyProjection(transformation); 
	}
	
	public void moveRight(int distance) {
		Matrix3x3 transformation = new Matrix3x3();
		Translation trans = new Translation(distance, 0);
        transformation.matrix = trans.matrix;
        applyProjection(transformation); 
	}
	
	public void moveLeft() {
		Matrix3x3 transformation = new Matrix3x3();
		Translation trans = new Translation(-5, 0);
    	transformation.matrix = trans.matrix;
    	applyProjection(transformation); 
	}
	
	public void moveLeft(int distance) {
		Matrix3x3 transformation = new Matrix3x3();
		Translation trans = new Translation(-distance, 0);
    	transformation.matrix = trans.matrix;
    	applyProjection(transformation); 
	}
	
	public void moveUp() {
		Matrix3x3 transformation = new Matrix3x3();
		Translation trans = new Translation(0, 5);
    	transformation.matrix = trans.matrix;
    	applyProjection(transformation); 
	}
	
	public void moveDown() {
		Matrix3x3 transformation = new Matrix3x3();
		Translation trans = new Translation(0, -5);
    	transformation.matrix = trans.matrix;
    	applyProjection(transformation); 
	}
	
	public void rotateLeft() {
		Matrix3x3 transformation = new Matrix3x3();
		double tempX = player.po.getXCenter();
    	double tempY = player.po.getYCenter();
    	transformation.matrix[0][2] = -tempX;         
    	transformation.matrix[1][2] = -tempY;
    	player.invisible = true;
    	applyProjection(transformation);
    	transformation.setMatrix(); 
    	Rotation rot = new Rotation(5);
    	player.direction += 5;
    	transformation.matrix  = rot.matrix;
    	applyProjection(transformation);            
    	transformation.setMatrix(); 
    	transformation.matrix[0][2] = tempX;         
    	transformation.matrix[1][2] = tempY;
    	applyProjection(transformation); 
    	player.invisible = false;
	}
	
	public void rotateLeft(int degree) {
		Matrix3x3 transformation = new Matrix3x3();
		double tempX = player.po.getXCenter();
    	double tempY = player.po.getYCenter();
    	transformation.matrix[0][2] = -tempX;         
    	transformation.matrix[1][2] = -tempY;
    	player.invisible = true;
    	applyProjection(transformation);
    	transformation.setMatrix(); 
    	Rotation rot = new Rotation(degree);
    	player.direction += degree;
    	transformation.matrix  = rot.matrix;
    	applyProjection(transformation);            
    	transformation.setMatrix(); 
    	transformation.matrix[0][2] = tempX;         
    	transformation.matrix[1][2] = tempY;
    	applyProjection(transformation); 
    	player.invisible = false;
	}
	
	public void rotateRight() {
		Matrix3x3 transformation = new Matrix3x3();
		double tempX = player.po.getXCenter();
    	double tempY = player.po.getYCenter();
    	transformation.matrix[0][2] = -tempX;         
    	transformation.matrix[1][2] = -tempY;
    	player.invisible = true;
    	applyProjection(transformation);
    	transformation.setMatrix(); 
    	Rotation rot = new Rotation(-5);
    	player.direction -= 5;
    	transformation.matrix  = rot.matrix;
    	applyProjection(transformation);            
    	transformation.setMatrix(); 
    	transformation.matrix[0][2] = tempX;         
    	transformation.matrix[1][2] = tempY;
    	applyProjection(transformation); 
    	player.invisible = false;
	}
	
	public void rotateRight(int degree) {
		Matrix3x3 transformation = new Matrix3x3();
		double tempX = player.po.getXCenter();
    	double tempY = player.po.getYCenter();
    	transformation.matrix[0][2] = -tempX;         
    	transformation.matrix[1][2] = -tempY;
    	player.invisible = true;
    	applyProjection(transformation);
    	transformation.setMatrix(); 
    	Rotation rot = new Rotation(-degree);
    	player.direction -= degree;
    	transformation.matrix  = rot.matrix;
    	applyProjection(transformation);    
    	transformation.setMatrix(); 
    	transformation.matrix[0][2] = tempX;         
    	transformation.matrix[1][2] = tempY;
    	applyProjection(transformation); 
    	player.invisible = false;
	}
	
	public void applyProjection(Matrix3x3 transformation) {	
        for(Edge edge: player.po.edges) {
        	Vector3 v1 = edge.p1.pointToVector();
        	Vector3 v2 = edge.p2.pointToVector();
        	v1 = transformation.times(v1);
        	v2 = transformation.times(v2);
        	edge.p1 = v1.point;
        	edge.p2 = v2.point;
        	updateBoundaries();
        }
        Vector3 v1 = player.cannon.pointToVector();
        v1 = transformation.times(v1);
        player.cannon = v1.point;
	}
	
	public void updateBoundaries() {
		player.max.x = -9999;
		player.min.x = 9999;
		player.max.y = -9999;
		player.min.y = 9999;
		for(Edge edge: player.po.edges) {
        	if(edge.p1.x > player.max.x) player.max.x = edge.p1.x;
        	if(edge.p2.x > player.max.x) player.max.x = edge.p2.x;
        	if(edge.p1.x < player.min.x) player.min.x = edge.p1.x;
        	if(edge.p2.x < player.min.x) player.min.x = edge.p2.x;
        	if(edge.p1.y > player.max.y) player.max.y = edge.p1.y;
        	if(edge.p2.y > player.max.y) player.max.y = edge.p2.y;
        	if(edge.p1.y < player.min.y) player.min.y = edge.p1.y;
        	if(edge.p2.y < player.min.y) player.min.y = edge.p2.y;
        }
	}
}
