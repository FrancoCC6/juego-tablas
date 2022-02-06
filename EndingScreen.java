import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EndingScreen extends JPanel {
	private final JLabel BACKGROUND = new JLabel();

	public EndingScreen() {
		setOpaque(false);

		BACKGROUND.setLayout(new BorderLayout());
		BACKGROUND.setIcon(new ImageIcon("Fondos/final.jpg"));
		BACKGROUND.add(this, BorderLayout.CENTER);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickScript();
			}
		});

		// Para probar
		addMouseMotionListener(null);
	}

	public void clickScript() {
		System.out.println("Pedido pasaje");
	}

	public JLabel getBG() {return BACKGROUND;}

	public static void createFrame() {
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