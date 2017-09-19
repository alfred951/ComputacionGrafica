package game;

import utils.PolygonObject;

public class Player {

	public PolygonObject po;
	int health;
	
	public Player(PolygonObject po) {
		super();
		this.po = po;
		this.health = 100;
	}
	
	public void takeDamage() {
		this.health -= 20;
	}
}
