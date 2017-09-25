package game;

import java.util.ArrayList;

import game.objects.PlayerModel;
import game.objects.Projectile;
import utils.Edge;
import utils.Point;
import utils.PolygonObject;

public class Player {

	public PolygonObject po;
	public double direction;
	public boolean destroyed;
	public boolean invisible;
	public Point cannon;
	public Point max;
	public Point min;
	public Controller controller;
	public ArrayList<Projectile> projectiles;
	public int health;
	public Long cooldown;
	
	public Player() {
		this.po = new PlayerModel().model;
		this.controller = new Controller(this);
		this.health = 100;
		this.direction = 90;
		this.cannon = new Point(0,110);
		this.max = new Point(-9999,-9999);
		this.min = new Point(9999,9999);
		this.projectiles = new ArrayList<>();
		this.destroyed = false;
		this.invisible = false;
		this.cooldown = (long) -1000;
	}
	
	public void fireProjectile() {
		if((System.currentTimeMillis()-cooldown)>1000) {
			Projectile projectile = new Projectile(cannon.x, cannon.y, direction);
			projectiles.add(projectile);
			cooldown = System.currentTimeMillis();
		}
	}
	
	public void takeDamage() {
		this.health -= 20;
		if(health <= 0) {
			destroyed = true;
			max.x = -99999;
			this.po = new PolygonObject();
			this.projectiles = new ArrayList<>();
		}
	}
	
	public void destroyProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}

    public synchronized boolean isHit(PolygonObject projectile) {
    	if(!this.invisible) {
	    	for(Edge e: projectile.edges) {
	            if(e.p1.x < max.x && e.p1.x > min.x) {
	            	if(e.p1.y < max.y && e.p1.y > min.y) {
	                	return true;
	                }
	            }
	            if(e.p2.x < max.x && e.p2.x > min.x) {
	            	if(e.p2.y < max.y && e.p2.y > min.y) {
	                	return true;
	                }
	            }
	        }
    	}
    	return false;
    }
}
