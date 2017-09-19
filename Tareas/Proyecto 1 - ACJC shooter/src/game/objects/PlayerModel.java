package game.objects;

import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public final class PlayerModel {

	public final PolygonObject model;
	public static final Point[] points = {
			new Point(100,300),
			new Point(100,200),
			new Point(200,350),
			new Point(200,150),
			new Point(300,200),
			new Point(300,300)
	};
	
	public static final Edge[] edges = {
			new Edge(points[0],points[3]),
			new Edge(points[0],points[5]),
			new Edge(points[1],points[2]),
			new Edge(points[1],points[4]),
			new Edge(points[2],points[4]),
			new Edge(points[3],points[5])
	};
	
	public PlayerModel() {
		model = new PolygonObject();
		for(Edge e: edges) {
			model.addEdge(e);
		}
	}
	
}
