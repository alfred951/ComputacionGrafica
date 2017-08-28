public class Matrix3x3 {
	
	public double[][] matrix;
	
	public Matrix3x3(double[][] matrix) {
		this.matrix = matrix;
	}

	public static Vector3 times(Matrix3x3 m, Vector3 n) {
		PointW point = new PointW(0, 0, 0);
		Vector3 o = new Vector3(point);
		o.point.x = (m.matrix[1][1] * n.point.x) + (m.matrix[1][2] * n.point.y) + (m.matrix[1][3] * n.point.w);
		o.point.y = (m.matrix[2][1] * n.point.x) + (m.matrix[2][2] * n.point.y) + (m.matrix[2][3] * n.point.w);
		o.point.w = (m.matrix[3][1] * n.point.x) + (m.matrix[3][2] * n.point.y) + (m.matrix[3][3] * n.point.w);
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
	
}
