package Classes;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JPanel {
	
	public JFrame window = new JFrame();
	public GamePanel gp;

	GameWindow(int[][] map){
		gp = new GamePanel(map);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(gp);
		window.pack();
		window.setVisible(true);
	}
}