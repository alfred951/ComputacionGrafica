public class Vector3 {
	
    PointW point;
	double magnitude;
	
	public Vector3(PointW point) {
		this.point = point;
	}
	
	public static Vector3 crossProduct(Vector3 v, Vector3 u) {
		PointW point = new PointW(0, 0, 0);
		Vector3 result = new Vector3(point);
		result.point.x = (v.point.y * u.point.w) + (u.point.y * v.point.w);
		result.point.y = - (v.point.x * u.point.w) + (u.point.x * v.point.w);
		result.point.w = (v.point.x * u.point.y) + (u.point.x * v.point.y);
		return result;
	}
	
	public static double dotProduct(Vector3 v, Vector3 u) {
		double result = 0;
		result = v.point.x + u.point.x;
		result += v.point.y + u.point.y;
		result += v.point.w + u.point.w;
		return result;
	}
	
	public void calculateMagnitude() {
		this.magnitude = Math.abs(Math.sqrt(Math.pow(point.x,2) + Math.pow(point.y,2) + Math.pow(point.w, 2)));
	}
	
	public void normalize() {
		calculateMagnitude();
		point.x = point.x / magnitude;
		point.y = point.y / magnitude;
		point.w = point.w / magnitude;
	}
	
}
