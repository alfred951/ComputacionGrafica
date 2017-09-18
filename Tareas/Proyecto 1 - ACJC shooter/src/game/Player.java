package game;

import utils.PolygonObject;

public class Player {

	public PolygonObject po;
	int Health;
	
	public Player(PolygonObject po) {
		super();
		this.po = po;
		this.Health = 100;
	}
}
