package utils;

import math.Vector3;

public class Edge {
    public Point p1;
    public Point p2;

    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public Edge(Vector3 start, Vector3 end) {
    	this.p1 = start.point;
    	this.p2 = end.point;
    }

    @Override
    public String toString() {
        return "Edge{" + "p1=" + p1 + ", p2=" + p2 + '}';
    } 
    
}