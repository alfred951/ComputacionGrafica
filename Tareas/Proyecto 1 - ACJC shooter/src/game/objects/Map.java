package game.objects;

import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Map{

	public PolygonObject po;
	
	
	public Map(int w, int h) {
		po = new PolygonObject();
		
		final Point[] points = {
				new Point(-h+10,-w+10),
				new Point(-h+10,w-10),
				new Point(h-10,w-10),
				new Point(h-10,-w+10)
		};
		
		final Edge[] edges = {
				new Edge(points[0],points[1]),
				new Edge(points[1],points[2]),
				new Edge(points[2],points[3]),
				new Edge(points[3],points[0]),
		};
		
		for(Edge e: edges) {
			po.addEdge(e);
		}
	}
}
