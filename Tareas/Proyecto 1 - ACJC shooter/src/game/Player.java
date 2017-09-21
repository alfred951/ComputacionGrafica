package game;

import game.objects.PlayerModel;
import utils.PolygonObject;

public class Player {

	public PolygonObject po;
	public int health;
	
	public Player() {
		this.po = new PlayerModel().model;
		this.health = 100;
	}
	
	public void takeDamage() {
		this.health -= 20;
	}
}
