import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EndingScreen extends Escenario {

	public EndingScreen(Runnable r) {
		super(r);
		
		BG_IMAGE = new ImageIcon("Fondos/final.jpg");

		MOUSE_LISTENER = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FINAL_ACTION.run();
			}
		};

		MOUSE_MOTION_LISTENER = null	;
	}
/*
	private static void createFrame() {
		JFrame frame = new JFrame();
		frame.setContentPane(new EndingScreen().getBG());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> createFrame());
	}
*/
}
