// code by jph
package ch.alpine.bridge.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.ext.PackageTestAccess;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.red.Mean;

public class SpinnerMenu<T> {
  /** background for items in menus that are selected; not Java official
   * the color is yellowish/golden */
  private static final Color ACTIVE_ITEM_LIGHT = new Color(243, 239, 124);
  private static final Color ACTIVE_ITEM_DARK = new Color(95, 95, 0);
  // ---
  private final List<SpinnerListener<T>> spinnerListeners = new LinkedList<>();
  private final JPopupMenu jPopupMenu = new JPopupMenu();
  private final LinkedHashMap<T, JMenuItem> map = new LinkedHashMap<>();
  private final T selectedValue;

  /** @param list
   * @param selectedValue may be null
   * @param hover */
  public SpinnerMenu(List<T> list, T selectedValue, boolean hover) {
    this(list, selectedValue, Object::toString, hover);
  }

  /** @param list
   * @param selectedValue may be null
   * @param function that determines what text each value is represented on a menu item
   * @param hover */
  public SpinnerMenu(List<T> list, T selectedValue, Function<T, String> function, boolean hover) {
    this.selectedValue = selectedValue;
    // ---
    for (T value : list) {
      JMenuItem jMenuItem = new JMenuItem(function.apply(value));
      if (hover)
        jMenuItem.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent mouseEvent) {
            spinnerListeners_spun(value);
          }
        });
      if (value.equals(selectedValue)) {
        Scalar mean = Mean.ofVector(ColorFormat.toVector(jMenuItem.getForeground()).extract(0, 3));
        jMenuItem.setBackground(Scalars.lessThan(RealScalar.of(128), mean) //
            ? ACTIVE_ITEM_DARK
            : ACTIVE_ITEM_LIGHT);
        jMenuItem.setOpaque(true); // several l&f require opaque, otherwise background will not be drawn
      } else
        jMenuItem.addActionListener(actionEvent -> spinnerListeners_spun(value));
      jPopupMenu.add(jMenuItem);
      map.put(value, jMenuItem);
    }
  }

  @PackageTestAccess
  void spinnerListeners_spun(T value) {
    spinnerListeners.forEach(spinnerListener -> spinnerListener.spun(value));
  }

  public void setFont(Font font) {
    map.values().forEach(jMenuItem -> jMenuItem.setFont(font));
  }

  public void addSpinnerListener(SpinnerListener<T> spinnerListener) {
    spinnerListeners.add(spinnerListener);
  }

  public void showRight(JComponent jComponent) {
    // computation below is necessary to compute position on display
    int delta = 2; // TODO BRIDGE ALG is this a magic constant that depends on l&f ?
    if (Objects.nonNull(selectedValue) && map.containsKey(selectedValue))
      for (Entry<T, JMenuItem> entry : map.entrySet()) {
        delta += entry.getValue().getPreferredSize().height;
        if (entry.getKey().equals(selectedValue)) {
          delta -= entry.getValue().getPreferredSize().height / 2;
          break;
        }
      }
    Dimension dimension = jComponent.getSize();
    jPopupMenu.show(jComponent, dimension.width, dimension.height / 2 - delta);
  }

  public void showSouth(JComponent jComponent) {
    jPopupMenu.show(jComponent, 0, jComponent.getSize().height);
  }
}
