package math;

public class Escalation extends Matrix3x3{

	public Escalation(double sx, double sy) {
		super();
		double[][] transformMatrix = {{sx, 0, 0},
									  {0, sy, 0},
									  {0, 0, 1}};
		matrix = transformMatrix;
	}
}
