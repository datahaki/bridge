// code by jph
package sys.gui;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/** WindowAncestor equips a {@link JComponent} with a {@link MouseListener} that allows the used to
 * drag the window, by dragging on the component. */
public enum WindowAncestor {
  ;
  public static void movesBy(Container container) {
    container.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent mouseEvent) {
        container.setCursor(new Cursor(Cursor.MOVE_CURSOR));
      }

      @Override
      public void mouseReleased(MouseEvent mouseEvent) {
        container.setCursor(Cursor.getDefaultCursor());
      }
    });
    container.addMouseMotionListener(new MouseMotionListener() {
      boolean drag = false;
      int mouse_x;
      int mouse_y;

      @Override
      public void mouseMoved(MouseEvent mouseEvent) {
        drag = false;
      }

      @Override
      public void mouseDragged(MouseEvent mouseEvent) {
        if (!drag) {
          mouse_x = mouseEvent.getXOnScreen();
          mouse_y = mouseEvent.getYOnScreen();
          drag = true;
        } else {
          Window window = SwingUtilities.getWindowAncestor(container);
          Rectangle rectangle = window.getBounds();
          rectangle.x += mouseEvent.getXOnScreen() - mouse_x;
          rectangle.y += mouseEvent.getYOnScreen() - mouse_y;
          mouse_x = mouseEvent.getXOnScreen();
          mouse_y = mouseEvent.getYOnScreen();
          window.setBounds(rectangle);
        }
      }
    });
  }
}
