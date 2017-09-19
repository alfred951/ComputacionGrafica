package game;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        Painter painter = new Painter();
        JFrame frame = new JFrame("Shooter Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.add(painter);
    }
}
