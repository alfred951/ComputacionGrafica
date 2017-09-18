package utils;

import java.util.ArrayList;

import math.Matrix3x3;
import math.Vector3;

public class PolygonObject {
    public ArrayList<Edge> edges;
    
    public PolygonObject() {
        edges = new ArrayList<>();
    }
    
    public void addEdge(Edge edge) {
        edges.add(edge);
    }
 
    public void transformObject(Matrix3x3 transformation) {
        for(Edge e: edges) {
            e.p1 = Matrix3x3.times(transformation, e.p1.pointToVector()).point;
            e.p2 = Matrix3x3.times(transformation, e.p2.pointToVector()).point;
        }
    }
    
    public static PolygonObject transformObject(PolygonObject po, 
            Matrix3x3 transformation) {
        
        PolygonObject newObject = new PolygonObject();
        for(Edge e: po.edges) {
            Vector3 newStart = Matrix3x3.times(transformation, e.p1.pointToVector());
            Vector3 newEnd = Matrix3x3.times(transformation, e.p2.pointToVector());
            Edge newEdge = new Edge(newStart, newEnd);
            newObject.addEdge(newEdge);
        }
        return newObject;
    }
}
