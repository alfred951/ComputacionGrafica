package game;

import game.objects.PlayerModel;
import utils.PolygonObject;

public class Player {

	public PolygonObject po;
	int health;
	
	public Player() {
		super();
		this.po = new PlayerModel().model;
		this.health = 100;
	}
	
	public void takeDamage() {
		this.health -= 20;
	}
}
