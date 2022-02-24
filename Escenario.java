public abstract class Escenario {
  protected final Runnable ENDING_ACTION;
  public final MouseListener MOUSE_LISTENER;
  public final MouseMotionListener MOUSE_MOTION_LISTENER;
  public final ImageIcon BG_IMAGE;
  
  public Escenario(Runnable r) {
    ENDING_ACTION = r;
  }
}
