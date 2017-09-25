package game;

import game.objects.Projectile;
import math.Matrix3x3;
import math.Translation;
import math.Vector3;
import utils.Edge;

public class ProjectileController {
	
	public Projectile projectile;
	public double speed;
	
	public ProjectileController(Projectile projectile) {
		this.projectile = projectile;
		this.speed = 1.5d;
	}
	
	public void moveForward() {
		Matrix3x3 transformation = new Matrix3x3();
		double directionX = Math.cos(Math.toRadians(projectile.direction));
		double directionY = Math.sin(Math.toRadians(projectile.direction));
		Translation trans = new Translation(directionX*speed, directionY*speed);
        transformation.matrix = trans.matrix;
        applyProjection(transformation); 
	}
	
	public void applyProjection(Matrix3x3 transformation) {	
        for(Edge edge: projectile.model.edges) {
        	Vector3 v1 = edge.p1.pointToVector();
        	Vector3 v2 = edge.p2.pointToVector();
        	v1 = transformation.times(v1);
        	v2 = transformation.times(v2);
        	edge.p1 = v1.point;
        	edge.p2 = v2.point;
        }
	}
}
