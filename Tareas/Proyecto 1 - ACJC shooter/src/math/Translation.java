package math;

public class Translation extends Matrix3x3 {

	public Translation(double dx, double dy) {
		super();
		
		double[][] transformMatrix = {{1, 0, dx},
				  					  {0, 1, dy},
				  					  {0, 0, 1}};
		matrix = transformMatrix;
	}

}
