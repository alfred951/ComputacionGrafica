package game;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
        
    public static void main(String[] args) {
        Painter painter = new Painter();
        painter.readObjectDescription("objeto.txt");
      
        JFrame frame = new JFrame("Shooter Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(painter);
        frame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
