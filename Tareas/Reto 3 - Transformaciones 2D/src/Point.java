/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Keeps the information of a 2D point
 * @author htrefftz
 */
public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
    
    public Vector3 pointToVector() {
    	Vector3 v3 = new Vector3(new PointW(x, y, 1));
    	return v3;
    }
}
