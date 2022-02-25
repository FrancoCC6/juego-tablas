import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Escenario {
  protected final Runnable ENDING_ACTION;
  protected MouseListener mouse_listener;
  protected MouseMotionListener mouse_motion_listener;
  protected ImageIcon bg_imageicon;
  protected final JPanel PANEL_ASOCIADO;
  
  public Escenario(Runnable r, JPanel p) {
    ENDING_ACTION = r;
    PANEL_ASOCIADO = p;
  }
  
  public MouseListener getMouseListener() {return mouse_listener;}
  public MouseMotionListener getMouseMotionListener() {return mouse_motion_listener;}
  public ImageIcon getBGImageIcon() {return bg_imageicon;}
}
