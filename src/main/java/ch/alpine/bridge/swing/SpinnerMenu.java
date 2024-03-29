// code by jph
package ch.alpine.bridge.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
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

import ch.alpine.bridge.ref.FieldsEditorParam;
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

  /** @param jMenuItem_getForeground
   * @return */
  public static Color getBackgroundHighlight(Color jMenuItem_getForeground) {
    Scalar mean = Mean.ofVector(ColorFormat.toVector(jMenuItem_getForeground).extract(0, 3));
    return Scalars.lessThan(RealScalar.of(128), mean) //
        ? ACTIVE_ITEM_DARK
        : ACTIVE_ITEM_LIGHT;
  }

  public static void setBackgroundHighlight(JMenuItem jMenuItem) {
    jMenuItem.setBackground(getBackgroundHighlight(jMenuItem.getForeground()));
  }

  // ---
  private final List<SpinnerListener<T>> spinnerListeners = new LinkedList<>();
  private final JPopupMenu jPopupMenu = new JPopupMenu();
  private final LinkedHashMap<T, JMenuItem> map = new LinkedHashMap<>();
  private final T selectedValue;

  /** @param list
   * @param selectedValue may be null
   * @param function that determines what text each value is represented on a menu item
   * @param font
   * @param hover */
  public SpinnerMenu(List<T> list, T selectedValue, Function<T, String> function, Font font, boolean hover) {
    this.selectedValue = selectedValue;
    // ---
    for (T value : list) {
      JMenuItem jMenuItem = new JMenuItem(function.apply(value));
      if (Objects.nonNull(font))
        jMenuItem.setFont(font);
      if (hover)
        jMenuItem.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent mouseEvent) {
            spinnerListeners_spun(value);
          }
        });
      if (value.equals(selectedValue)) {
        setBackgroundHighlight(jMenuItem);
        jMenuItem.setOpaque(true); // several l&f require opaque, otherwise background will not be drawn
      } else
        jMenuItem.addActionListener(actionEvent -> spinnerListeners_spun(value));
      FieldsEditorParam.GLOBAL.minSize(jMenuItem);
      jPopupMenu.add(jMenuItem);
      map.put(value, jMenuItem);
    }
  }

  @PackageTestAccess
  void spinnerListeners_spun(T value) {
    spinnerListeners.forEach(spinnerListener -> spinnerListener.spun(value));
  }

  public void addSpinnerListener(SpinnerListener<T> spinnerListener) {
    spinnerListeners.add(spinnerListener);
  }

  public void showRight(JComponent jComponent) {
    showRight(jComponent, new Rectangle(jComponent.getSize()));
  }

  public void showRight(JComponent jComponent, Rectangle rectangle) {
    // computation below is necessary to compute position on display
    int delta = 2; // constant was found to work well, but could depend on l&f
    if (Objects.nonNull(selectedValue) && map.containsKey(selectedValue))
      for (Entry<T, JMenuItem> entry : map.entrySet()) {
        delta += entry.getValue().getPreferredSize().height;
        if (entry.getKey().equals(selectedValue)) {
          delta -= entry.getValue().getPreferredSize().height / 2;
          break;
        }
      }
    jPopupMenu.show(jComponent, rectangle.x + rectangle.width, rectangle.y + rectangle.height / 2 - delta);
  }

  public void showSouth(JComponent jComponent) {
    jPopupMenu.show(jComponent, 0, jComponent.getSize().height);
  }
}
