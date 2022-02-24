import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {
  
  private final JLabel BACKGROUND = new JLabel();
  
  private final JPanel MAIN_PANEL = new JPanel(); 
  // NECESARIO dado que si se aplican los listeners sobre el JFrame se computa la barra de acción como parte de la pantalla de juego
  
  private final Escenario ESCENARIOS[] = {
    new SeleccionNivel(this::transicionar),
    new Juego(false, this::transicionar),
    new Juego(true, this::transicionar),
    new EndingScreen(this::transicionar);
  }
  
  private int iterador_escenario = -1; // Cuando se llama a transicionar en el constructor, para cargar la primera pantalla este número se convierte en 0
  
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
    MAIN_PANEL.removeMouseListener(getMouseListeners()[0]);
    MAIN_PANEL.removeMouseMotionListener(getMouseMotionListeners()[0]);
    
    // Poner listeners nuevos
    ++iterador_escenario %= ESCENARIOS.length; // Señala al siguiente escenario en la lista
    
    MAIN_PANEL.addMouseListener       (ESCENARIOS[iterador_escenario]. MOUSE_LISTENER);
    MAIN_PANEL.addMouseMotionListener (ESCENARIOS[iterador_escenario]. MOUSE_MOTION_LISTENER);
    BACKGROUND.setIcon                (ESCENARIOS[iterador_escenario]. BG_IMAGE);
    
    // Inicializar
    ESCENARIOS[iterador_escenario].inicializar();
  }
  
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainFrame());
  }
} 
