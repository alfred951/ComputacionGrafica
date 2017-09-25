package game.objects;

import game.ProjectileController;
import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Projectile {

	public PolygonObject model;
	public ProjectileController controller;
	public boolean neutralized;
	public double direction;
	
	public Projectile(double movx, double movy, double direction) {
		
		this.direction = direction;
		this.controller = new ProjectileController(this);
		this.neutralized = false;
		
		Point[] points = {
			new Point(0,-5),
			new Point(3,-3),
			new Point(5,0),
			new Point(3,3),
			new Point(0,5),
			new Point(-3,3),
			new Point(-5,0),
			new Point(-3,-3)
		};
		
		for(Point p: points){
			p.x = p.x + movx;
			p.y = p.y + movy;
		};
		
		Edge[] edges = {
			new Edge(points[0],points[1]),
			new Edge(points[1],points[2]),
			new Edge(points[2],points[3]),
			new Edge(points[3],points[4]),
			new Edge(points[4],points[5]),
			new Edge(points[5],points[6]),
			new Edge(points[6],points[7]),
			new Edge(points[7],points[0])
		};
		
		model = new PolygonObject();
		for(Edge e: edges) {
			model.addEdge(e);
		}	
	}
	
	public void neutralize() {
		this.neutralized = true;
		this.model =  new PolygonObject();
	}
}
