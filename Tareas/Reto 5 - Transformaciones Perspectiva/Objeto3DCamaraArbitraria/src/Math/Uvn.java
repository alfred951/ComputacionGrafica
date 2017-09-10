/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 *
 * @author htrefftz
 */
public class Uvn extends Matrix4x4  {
    Vector4 from;
    Vector4 lookAt;
    Vector4 up;

    public Uvn() {
        super();
    }
    
    public Uvn(Vector4 from, Vector4 lookAt, Vector4 up) {
        super();
        
        Vector4 n = Vector4.subtract(lookAt, from);
        n = Vector4.minus(n);
        n.normalize();
        Vector4 mn = Vector4.minus(n);
        
        Vector4 u = Vector4.crossProduct(up, n);
        u.normalize();
        Vector4 mu = Vector4.minus(u);
        
        Vector4 v = Vector4.crossProduct(n, u);
        Vector4 mv = Vector4.minus(v);
        
        matrix[0][0] = u.getX();
        matrix[0][1] = u.getY();
        matrix[0][2] = u.getZ();
        matrix[0][3] = Vector4.dotProduct(mu, from);
        matrix[1][0] = v.getX();
        matrix[1][1] = v.getY();
        matrix[1][2] = v.getZ();
        matrix[1][3] = Vector4.dotProduct(mv, from);
        matrix[2][0] = n.getX();
        matrix[2][1] = n.getY();
        matrix[2][2] = n.getZ();
        matrix[2][3] = Vector4.dotProduct(mn, from);
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
    }
    
    
}
