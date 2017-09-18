package utils;
import math.Vector3;

public class Point {
    public double x;
    public double y;
    public double w;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.w = 1;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + ", w=" + w + '}';
    }
    
    public Vector3 pointToVector() {
    	Vector3 v3 = new Vector3(this);
    	return v3;
    }
}
