package Math;

public class Escalation extends Matrix4x4 {

    public Escalation() {
        super();
    }
    
    public Escalation(double sx, double sy, double sz) {
        super();
        matrix[0][0] = sx;
        matrix[1][1] = sy;
        matrix[2][2] = sz;
        matrix[3][3] = 1;
    }
}
