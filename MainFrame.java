import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {
  
  private final Escenario ESCENARIOS[] = {
    new SeleccionNivel(this::transicionar, MAIN_PANEL),
    new Juego(false, this::transicionar, MAIN_PANEL),
    new Juego(true, this::transicionar, MAIN_PANEL),
    new EndingScreen(this::transicionar, MAIN_PANEL);
  }
  
  private int iterador_escenario = -1; // Cuando se llama a transicionar en el constructor, para cargar la primera pantalla este número se convierte en 0
  
  private final JLabel BACKGROUND = new JLabel();
  
  private final JPanel MAIN_PANEL = new JPanel() {
    public void paintComponent(Graphics g) {
      super(g);
      ESCENARIOS[iterador_escenario].dibujar(g);
    }
  };
  
  public MainFrame() {
    BACKGROUND.setLayout(new BorderLayout());
    setContentPane(BACKGROUND);
    add(MAIN_PANEL);
    
    transicionar(); // Inicializa el juego en la primer pantalla
    
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Aprendiendo las tablas!");
    setResizable(false);
    setVisible(true);
  }
  
  
  private void transicionar() {
    // Limpiar listeners actuales
    MAIN_PANEL.removeMouseListener(MAIN_PANEL.getMouseListeners()[0]);
    MAIN_PANEL.removeMouseMotionListener(MAIN_PANEL.getMouseMotionListeners()[0]);
    
    // Poner listeners nuevos
    // ++iterador_escenario %= ESCENARIOS.length; // Sería hermoso si funcionase
    iterador_escenario = (iterador_escenario + 1) % ESCENARIOS.length;// Señala al siguiente escenario en la lista
    
    Escenario escenario_actual = ESCENARIOS[iterador_escenario];
    
    MAIN_PANEL.addMouseListener       (escenario_actual.getMouseListener());
    MAIN_PANEL.addMouseMotionListener (escenario_actual.getMouseMotionListener());
    BACKGROUND.setIcon                (escenario_actual.getBGImageIcon());
    
    // Inicializar
    escenario_actual.inicializar();
  }
  
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainFrame());
  }
} 
