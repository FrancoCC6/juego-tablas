import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EndingScreen extends JPanel implements Escenario {
	private final JLabel BACKGROUND = new JLabel();
	private final Runnable FINAL_ACTION;

	public EndingScreen(Runnable fa) {
		FINAL_ACTION = fa;
		
		setOpaque(false);

		BACKGROUND.setLayout(new BorderLayout());
		BACKGROUND.setIcon(new ImageIcon("Fondos/final.jpg"));
		BACKGROUND.add(this, BorderLayout.CENTER);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				finalAction();
			}
		});

		// Para probar
		addMouseMotionListener(null);
	}

	@Override
	public void finalAction() {
		FINAL_ACTION.run();
	}

	public JLabel getBG() {return BACKGROUND;}

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
}
