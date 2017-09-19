package game;

import utils.PolygonObject;

public class RedPlayer extends Player {

	public RedPlayer(PolygonObject po) {
		super(po);
		System.out.println("Health" + super.health);
	}

}
