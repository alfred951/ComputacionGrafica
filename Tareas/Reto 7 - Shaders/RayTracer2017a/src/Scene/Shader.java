/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene;

import Math.Point;
import Math.Vector4;
import Math.Ray;

/**
 *
 * @author htrefftz
 */
public class Shader {
    public static Colour computeColor(Point point, Vector4 normal, Material material) {
        normal.normalize();
        // We will add all the colors in acum
        Colour acum = new Colour(0, 0, 0);
        // Compute the Ambient Reflection
        Colour AmbientReflection = Colour.multiply(Colour.multiply(Scene.ambientLight.color, material.color), 
                material.Ka);
        acum = Colour.add(acum, AmbientReflection);
        // Compute the Diffuse Reflection, respect to all point lights
        for(PointLight pl: Scene.pointLights) {
            Vector4 light = new Vector4(point, pl.point);
            Ray shadowRay = new Ray(point, light);
            // Check if the object is in the shadow with respect to this source
            // of life. If it is, do not add diffuse reflection
            if(!Scene.intersectRayForShadow(shadowRay)) {
                light.normalize();
                double scalar = Vector4.dotProduct(normal, light)*material.Kd;
                // If dot product is < 0, the point is not receiving light from
                // this source.
                if(scalar < 0) scalar = 0;
                Colour DiffuseReflection = Colour.multiply(pl.color, material.color);
                DiffuseReflection = Colour.multiply(DiffuseReflection, scalar);
                acum = Colour.add(acum, DiffuseReflection);
            }
        }
        // Compute the Specular Reflection (pending)
        return acum;
    }
}
