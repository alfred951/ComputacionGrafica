package game.objects;

import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public final class PlayerModel {

	public final PolygonObject model;
	public static final Point[] points = {
			new Point(-50,-50),
			new Point(-50,50),
			new Point(50,50),
			new Point(50,-50),
			new Point(-25,50),
			new Point(-25,100),
			new Point(25,100),
			new Point(25,50)
	};
	
	public final Edge[] edges = {
			new Edge(points[0],points[1]),
			new Edge(points[1],points[2]),
			new Edge(points[2],points[3]),
			new Edge(points[3],points[0]),
			new Edge(points[0],points[2]),
			new Edge(points[1],points[3]),
			new Edge(points[4],points[5]),
			new Edge(points[5],points[6]),
			new Edge(points[6],points[7]),
			new Edge(points[7],points[4])
	};
	
	public PlayerModel() {
		model = new PolygonObject();
		for(Edge e: edges) {
			model.addEdge(e);
		}
	}
	
}
