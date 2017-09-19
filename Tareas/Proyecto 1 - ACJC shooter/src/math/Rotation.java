package math;

public class Rotation extends Matrix3x3{

	public Rotation(double grade) {
		super();
		
		double rads = Math.toRadians(grade);
		double[][] transformMatrix = {{Math.cos(rads), -Math.sin(rads), 0}, 
									  {Math.sin(rads), Math.cos(rads), 0},
									  {0, 0, 1}};
		matrix = transformMatrix;
	}

}
