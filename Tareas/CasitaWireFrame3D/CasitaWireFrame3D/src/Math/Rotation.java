package Math;

public class Rotation extends Matrix4x4{

    public Rotation() {
        super();
    }
    
    public Rotation(char axis, double theta) {
        super();
        switch (axis) {
        	case 'x':
        		matrix[1][1] = Math.cos(theta);
        		matrix[1][2] = -Math.sin(theta);
        		matrix[2][1] = Math.sin(theta);
        		matrix[2][2] = Math.cos(theta);
        		break;
        	case 'y':
        		matrix[0][0] = Math.cos(theta);
        		matrix[2][0] = -Math.sin(theta);
        		matrix[0][2] = Math.sin(theta);
        		matrix[2][2] = Math.cos(theta);
        		break;
        	case 'z':
        		matrix[0][0] = Math.cos(theta);
        		matrix[0][1] = -Math.sin(theta);
        		matrix[1][0] = Math.sin(theta);
        		matrix[1][1] = Math.cos(theta);
        		break;        		
        }
    }
}
