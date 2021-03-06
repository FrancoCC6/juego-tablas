import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class TestSelectionScreen extends JPanel {
	private final int
		COORD_N_2_X 	= 196,
		COORD_N_2_Y 	= 177,
		COORD_N_3_X 	= 516,
		COORD_N_3_Y 	= 245,
		COORD_FL_2_X	= 118,
		COORD_FL_2_Y	= 278,
		COORD_FL_3_X	= 574,
		COORD_FL_3_Y	= 342,
		BASE_NUMERO 	= 53,
		ALTURA_NUMERO 	= 99,
		BASE_FLECHA 	= 63,
		ALTURA_FLECHA 	= 64;

	private boolean 
		dos_senyalado, 
		tres_senyalado;

	private final JLabel BACKGROUND = new JLabel();

	private final boolean MUSIC_ENABLED = false;

	private File archivo;
	private AudioInputStream stream;
	private Clip clip;


	public TestSelectionScreen() {
		setOpaque(false);

		BACKGROUND.setLayout(new BorderLayout());
		BACKGROUND.setIcon(new ImageIcon("Fondos/levelSelection.png"));
		BACKGROUND.add(this, BorderLayout.CENTER);

		if (MUSIC_ENABLED)
			try {
				archivo = new File("bg_music_1.wav");

				if (archivo.exists()) {
					stream = AudioSystem.getAudioInputStream(archivo);
					clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				else {
					System.err.println("bg_music_1.wav movido de carpeta");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				dos_senyalado 	= 	isBetween(e.getX(), COORD_N_2_X, COORD_N_2_X + BASE_NUMERO) 
								&& 	isBetween(e.getY(), COORD_N_2_Y, COORD_N_2_Y + ALTURA_NUMERO);
				tres_senyalado 	= 	isBetween(e.getX(), COORD_N_3_X, COORD_N_3_X + BASE_NUMERO)
								&& 	isBetween(e.getY(), COORD_N_3_Y, COORD_N_3_Y + ALTURA_NUMERO);

				repaint(); // QUEREMOS EVITAR ESTO
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (dos_senyalado)
					System.out.println("Tabla del 2 requerida");
				else if (tres_senyalado)
					System.out.println("Tabla del 3 requerida");
			}
		});
	}

	public JLabel getBG() {return BACKGROUND;}

	private boolean isBetween(Comparable x, Comparable a, Comparable b) {
		return x.compareTo(a) >= 0 && x.compareTo(b) <= 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);

		if (!dos_senyalado) 	g.fillRect(COORD_FL_2_X, COORD_FL_2_Y, BASE_FLECHA, ALTURA_FLECHA);
		if (!tres_senyalado) 	g.fillRect(COORD_FL_3_X, COORD_FL_3_Y, BASE_FLECHA, ALTURA_FLECHA);
	}

	public static void createFrame() {
		JFrame frame = new JFrame();
		frame.setContentPane(new TestSelectionScreen().getBG());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> createFrame());
	}
}