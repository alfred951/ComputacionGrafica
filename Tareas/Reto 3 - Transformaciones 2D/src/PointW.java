public class PointW {

	double x;
	double y;
	double w;
	
	public PointW(double x, double y, double w) {
		this.x = x;
		this.y = y;
		this.w = w;
	}
	
	public double getW() {
		return w;
	}
	
	public void setW(double w) {
		this.w = w;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Point pointWToPoint() {
		double nx = x / w;
		double ny = y / w;
		Point p = new Point( (int) nx, (int) ny);
		return p;
	}
	
}
