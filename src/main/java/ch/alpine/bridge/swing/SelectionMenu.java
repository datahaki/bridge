// code by jph
package ch.alpine.bridge.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public abstract class SelectionMenu<T> extends StandardMenu {
  /** background for items in menus that are selected; not Java official
   * the color is yellowish/golden */
  private static final Color ACTIVE_ITEM = new Color(243, 239, 124);
  // ---
  private final LinkedHashMap<T, JMenuItem> map = new LinkedHashMap<>();
  private final List<T> list;
  private final T value;
  private final Font font;
  private final boolean hover;

  /** @param list
   * @param value
   * @param font
   * @param hover */
  public SelectionMenu(List<T> list, T value, Font font, boolean hover) {
    this.list = list;
    this.value = value;
    this.font = font;
    this.hover = hover;
  }

  @Override
  protected void design(JPopupMenu jPopupMenu) {
    for (T type : list) {
      JMenuItem jMenuItem = new JMenuItem(type.toString());
      jMenuItem.setFont(font);
      if (hover)
        jMenuItem.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent mouseEvent) {
            update(type);
          }
        });
      jMenuItem.addActionListener(actionEvent -> {
        if (!type.equals(value)) // invoke only when different
          update(type);
      });
      map.put(type, jMenuItem);
      jPopupMenu.add(jMenuItem);
    }
  }

  public abstract void update(T type);

  public void showRight(JComponent jComponent) {
    JPopupMenu jPopupMenu = designShow();
    T type = value;
    int delta = 2; // TODO BRIDGE ALG is this a magic constant that depends on l&f ?
    if (Objects.nonNull(type)) {
      JMenuItem jMenuItem = map.get(type);
      if (Objects.nonNull(jMenuItem)) {
        jMenuItem.setBackground(ACTIVE_ITEM);
        jMenuItem.setOpaque(true); // several l&f require opaque, otherwise background will not be drawn
        for (Entry<T, JMenuItem> entry : map.entrySet()) {
          delta += entry.getValue().getPreferredSize().height;
          if (entry.getKey().equals(type)) {
            delta -= entry.getValue().getPreferredSize().height / 2;
            break;
          }
        }
      }
    }
    Dimension dimension = jComponent.getSize();
    jPopupMenu.show(jComponent, dimension.width, dimension.height / 2 - delta);
  }
}
