package game;

public class RedPlayer extends Player {

	public RedPlayer() {		
		System.out.println("Red Player Health:" + super.health);
		controller.moveLeft(500);
		controller.rotateRight(90);
	}

}
