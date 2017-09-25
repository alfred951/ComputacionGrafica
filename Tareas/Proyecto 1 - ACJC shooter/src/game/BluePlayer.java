package game;

public class BluePlayer extends Player{

	public BluePlayer() {
		System.out.println("Blue Player Health:" + super.health);
		controller.moveRight(500);
		controller.rotateLeft(90);
	}
	
}
