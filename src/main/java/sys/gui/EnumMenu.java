// code by jph
package sys.gui;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ch.alpine.bridge.swing.SpinnerMenu;

public abstract class EnumMenu<T extends Comparable<T>> extends StandardMenu {
  private final NavigableMap<T, JMenuItem> map = new TreeMap<>();
  protected boolean hover = false;

  @Override
  protected final void design(JPopupMenu jPopupMenu) {
    for (T type : getList()) {
      JMenuItem jMenuItem = new JMenuItem(getString(type), getImageIcon(type));
      jMenuItem.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
          if (hover)
            action(type);
        }
      });
      jMenuItem.addActionListener(_ -> action(type));
      map.put(type, jMenuItem);
      jPopupMenu.add(jMenuItem);
    }
  }

  protected abstract List<T> getList();

  /** override if description other than verbatim myType is required
   * 
   * @param type
   * @return myType, or description of myType */
  protected String getString(T type) {
    return type.toString();
  }

  protected abstract ImageIcon getImageIcon(T type);

  /** method to override
   * 
   * @param type */
  public abstract void action(T type);

  public final void showRight(JComponent jComponent, Rectangle rectangle, T type) {
    JPopupMenu jPopupMenu = designShow();
    int delta = 2;
    SpinnerMenu.setBackgroundHighlight(map.get(type));
    for (Entry<T, JMenuItem> entry : map.headMap(type, true).entrySet()) {
      delta += entry.getValue().getPreferredSize().height;
      if (entry.getKey().equals(type)) {
        delta -= entry.getValue().getPreferredSize().height / 2;
        // break;
      }
    }
    jPopupMenu.show(jComponent, rectangle.x + rectangle.width, rectangle.y + rectangle.height / 2 - delta);
  }
}
