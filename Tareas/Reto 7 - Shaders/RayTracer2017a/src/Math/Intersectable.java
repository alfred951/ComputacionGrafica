/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

import Scene.Material;
import Scene.Colour;

/**
 *
 * @author htrefftz
 */
public interface Intersectable {
    public Solutions intersect(Ray ray);
    public Material getMaterial();
    public Colour callShader(Ray ray, double minT);
    public Vector4 computeNormal(Point p);
}
