// code by jph
package ch.alpine.java.awt;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public abstract class StandardMenu {
  public static <T extends StandardMenu> void bind(JButton jButton, Supplier<T> supplier) {
    jButton.addActionListener(new ActionListener() {
      long tic = System.nanoTime();

      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        long toc = System.nanoTime();
        if (500_000_000L < toc - tic) {
          StandardMenu standardMenu = supplier.get();
          standardMenu.jPopupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
              // System.out.println("popupMenuWillBecomeVisible");
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
              // System.out.println("popupMenuWillBecomeInvisible");
              tic = System.nanoTime();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
              // System.out.println("popupMenuCanceled");
              tic = System.nanoTime();
            }
          });
          standardMenu.south(jButton);
        }
      }
    });
  }

  // ==================================================
  protected abstract void design(JPopupMenu jPopupMenu);

  private final JPopupMenu jPopupMenu = new JPopupMenu();

  protected final JPopupMenu designShow() {
    design(jPopupMenu);
    return jPopupMenu;
  }

  /** non-blocking
   * 
   * @param jComponent */
  public void south(JComponent jComponent) {
    designShow().show(jComponent, 0, jComponent.getSize().height);
  }

  /** placement typically avoids that menu is created over mouse pointer
   * 
   * @param jComponent */
  public void southEast(JComponent jComponent) {
    designShow().show(jComponent, jComponent.getSize().width, jComponent.getSize().height);
  }

  public void showRelative(JComponent jComponent, Rectangle rectangle) {
    designShow().show(jComponent, rectangle.x + rectangle.width, rectangle.y);
  }

  public void atMouse(JComponent jComponent) {
    Point myMouse = MouseLocation.getMouseLocation();
    Point myPoint = jComponent.getLocationOnScreen();
    designShow().show(jComponent, myMouse.x - myPoint.x, myMouse.y - myPoint.y);
  }
}
