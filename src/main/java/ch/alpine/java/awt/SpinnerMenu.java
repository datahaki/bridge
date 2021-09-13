// code by jph
package ch.alpine.java.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/* package */ class SpinnerMenu<T> extends StandardMenu {
  /** background for items in menus that are selected; not Java official */
  private static final Color ACTIVE_ITEM = new Color(243, 239, 124);
  // ---
  private final LinkedHashMap<T, JMenuItem> map = new LinkedHashMap<>();
  private final SpinnerLabel<T> spinnerLabel;
  private final boolean hover;

  SpinnerMenu(SpinnerLabel<T> spinnerLabel, boolean hover) {
    this.spinnerLabel = spinnerLabel;
    this.hover = hover;
  }

  @Override
  protected void design(JPopupMenu jPopupMenu) {
    Font font = spinnerLabel.getFont();
    for (T type : spinnerLabel.list) {
      JMenuItem jMenuItem = new JMenuItem(type.toString());
      jMenuItem.setFont(font);
      if (hover)
        jMenuItem.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent mouseEvent) {
            setValue(type);
          }
        });
      jMenuItem.addActionListener(actionEvent -> {
        if (!type.equals(spinnerLabel.getValue())) // invoke only when different
          setValue(type);
      });
      map.put(type, jMenuItem);
      jPopupMenu.add(jMenuItem);
    }
  }

  private void setValue(T type) {
    spinnerLabel.setValueSafe(type);
    spinnerLabel.reportToAll();
  }

  public void showRight(JComponent jComponent) {
    JPopupMenu jPopupMenu = designShow();
    T type = spinnerLabel.getValue();
    if (Objects.nonNull(type)) {
      int delta = 2;
      map.get(type).setBackground(ACTIVE_ITEM); // Colors.gold
      for (Entry<T, JMenuItem> entry : map.entrySet()) {
        delta += entry.getValue().getPreferredSize().height;
        if (entry.getKey().equals(type)) {
          delta -= entry.getValue().getPreferredSize().height / 2;
          break;
        }
      }
      Dimension dimension = jComponent.getSize();
      jPopupMenu.show(jComponent, dimension.width, dimension.height / 2 - delta);
    }
  }
}
