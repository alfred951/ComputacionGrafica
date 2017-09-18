package game;

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
	
	public Map() {
		po = new PolygonObject();
	}
}
