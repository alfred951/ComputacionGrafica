package math;
import utils.Point;

public class Matrix3x3 {
	
	public double[][] matrix;
	
	public Matrix3x3(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public Matrix3x3() {
		matrix = new double [4][4];
        for(int i = 0; i < 4; i++) {
        	matrix[i][i] = 1d;
        }
	}

	public Vector3 times(Vector3 n) {
		Point point = new Point(0, 0);
		Vector3 o = new Vector3(point);
		o.point.x = (matrix[0][0] * n.point.x) + (matrix[0][1] * n.point.y) + (matrix[0][2] * n.point.w);
		o.point.y = (matrix[1][0] * n.point.x) + (matrix[1][1] * n.point.y) + (matrix[1][2] * n.point.w);
		o.point.w = (matrix[2][0] * n.point.x) + (matrix[2][1] * n.point.y) + (matrix[2][2] * n.point.w);
		return o;
	}
	
	public static Vector3 times(Matrix3x3 matrix, Vector3 n) {
		Point point = new Point(0,0);
		Vector3 o = new Vector3(point);
		double[][] _matrix = matrix.matrix;
		o.point.x = (_matrix[0][0] * n.point.x) + (_matrix[0][1] * n.point.y) + (_matrix[0][2] * n.point.w);
		o.point.y = (_matrix[1][0] * n.point.x) + (_matrix[1][1] * n.point.y) + (_matrix[1][2] * n.point.w);
		o.point.w = (_matrix[2][0] * n.point.x) + (_matrix[2][1] * n.point.y) + (_matrix[2][2] * n.point.w);
		return o;
	}
	
	public static Matrix3x3 times(Matrix3x3 m, Matrix3x3 n) {
		double[][] zeros = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		Matrix3x3 o = new Matrix3x3(zeros);
		for(int i=0; i<3; i++) {
			for(int j=0; i<3; j++) {
				for(int k=0; k<3; k++) {
					o.matrix[i][j] += m.matrix[i][k] * n.matrix[k][j];
				}
			}
		}
		return o;
	}
	
	public void printMatrix() {
		System.out.println("--------------------");
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				System.out.print(matrix[i][j] + "  ");
			}
			System.out.println("");
		}
	}
	
	public void setMatrix() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if (i == j) {
					this.matrix[i][j] = 1;
				}
				else {
					this.matrix[i][j] = 0;
				}
			}
		}
	}
	
}
