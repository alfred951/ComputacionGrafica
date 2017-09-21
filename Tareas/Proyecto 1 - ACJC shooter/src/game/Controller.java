package game;

import math.Matrix3x3;
import math.Rotation;
import math.Translation;
import math.Vector3;
import utils.Edge;
import utils.PolygonObject;

public class Controller {
	
	public static Matrix3x3 moveRight(Matrix3x3 matrixObject) {
		Translation trans = new Translation(5, 0);
        matrixObject.matrix = trans.matrix;
        return matrixObject;
	}
	
	public static Matrix3x3 moveLeft(Matrix3x3 matrixObject) {
		Translation trans = new Translation(-5, 0);
    	matrixObject.matrix = trans.matrix;
    	return matrixObject;
	}
	
	public static Matrix3x3 moveUp(Matrix3x3 matrixObject) {
		Translation trans = new Translation(0, 5);
    	matrixObject.matrix = trans.matrix;
    	return matrixObject;
	}
	
	public static Matrix3x3 moveDown(Matrix3x3 matrixObject) {
		Translation trans = new Translation(0, -5);
    	matrixObject.matrix = trans.matrix;
    	return matrixObject;
	}
	
	public static Matrix3x3 rotateLeft(Matrix3x3 matrixObject, PolygonObject po) {
		double tempX = po.getXCenter();
    	double tempY = po.getYCenter();
    	matrixObject.matrix[0][2] = -tempX;         
    	matrixObject.matrix[1][2] = -tempY;
    	applyProjection(matrixObject, po);
    	matrixObject.setMatrix(); 
    	Rotation rot = new Rotation(5);
    	matrixObject.matrix  = rot.matrix;
    	applyProjection(matrixObject, po);            
    	matrixObject.setMatrix(); 
    	matrixObject.matrix[0][2] = tempX;         
    	matrixObject.matrix[1][2] = tempY;
    	return matrixObject;
	}
	
	public static Matrix3x3 rotateRight(Matrix3x3 matrixObject, PolygonObject po) {
		double tempX = po.getXCenter();
    	double tempY = po.getYCenter();
    	matrixObject.matrix[0][2] = -tempX;         
    	matrixObject.matrix[1][2] = -tempY;
    	applyProjection(matrixObject, po);
    	matrixObject.setMatrix(); 
    	Rotation rot = new Rotation(-5);
    	matrixObject.matrix  = rot.matrix;
    	applyProjection(matrixObject, po);            
    	matrixObject.setMatrix(); 
    	matrixObject.matrix[0][2] = tempX;         
    	matrixObject.matrix[1][2] = tempY;
    	return matrixObject;
	}
	
	public static void applyProjection(Matrix3x3 matrixObject, PolygonObject po) {	
        for(Edge edge: po.edges) {
        	Vector3 v1 = edge.p1.pointToVector();
        	Vector3 v2 = edge.p2.pointToVector();
        	v1 = matrixObject.times(v1);
        	v2 = matrixObject.times(v2);
        	edge.p1 = v1.point;
        	edge.p2 = v2.point;
        }
	}
}
