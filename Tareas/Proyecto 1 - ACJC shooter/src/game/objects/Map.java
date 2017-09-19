package game.objects;

import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Map{

	public PolygonObject po;
	public final Point[] points = {
			new Point(-100,-100),
			new Point(-100,100),
			new Point(100,100),
			new Point(100,-100)
	};
	
	public final Edge[] edges = {
			new Edge(points[0],points[1]),
			new Edge(points[1],points[2]),
			new Edge(points[2],points[3]),
			new Edge(points[3],points[0]),
	};
	
	public Map() {
		po = new PolygonObject();
		for(Edge e: edges) {
			po.addEdge(e);
		}
	}
}
