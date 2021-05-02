// code by jph
package ch.alpine.java.awt;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

class SpinnerMenu<T> extends StandardMenu {
  private final Map<T, JMenuItem> map = new LinkedHashMap<>();
  private final SpinnerLabel<T> spinnerLabel;
  private final boolean hover;

  SpinnerMenu(SpinnerLabel<T> spinnerLabel, boolean hover) {
    this.spinnerLabel = spinnerLabel;
    this.hover = hover;
  }

  @Override
  protected void design(JPopupMenu jPopupMenu) {
    Font font = spinnerLabel.getLabelComponent().getFont();
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

  public void showRight(JLabel jLabel) {
    JPopupMenu jPopupMenu = designShow();
    T type = spinnerLabel.getValue();
    if (Objects.nonNull(type)) {
      int delta = 2;
      map.get(type).setBackground(Colors.ACTIVE_ITEM); // Colors.gold
      for (Entry<T, JMenuItem> entry : map.entrySet()) {
        delta += entry.getValue().getPreferredSize().height;
        if (entry.getKey().equals(type)) {
          delta -= entry.getValue().getPreferredSize().height / 2;
          break;
        }
      }
      Dimension dimension = jLabel.getSize();
      jPopupMenu.show(jLabel, dimension.width, dimension.height / 2 - delta);
    }
  }
}
