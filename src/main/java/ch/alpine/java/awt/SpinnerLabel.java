// code by jph
package ch.alpine.java.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JComponent;
import javax.swing.JTextField;

/** selector in gui for easy scrolling through a list with mouse-wheel but no pull-down menu */
public class SpinnerLabel<T> extends JTextField {
  /** JToggleButton background when selected is 184 207 229 selection color
   * subtracts 24 from each RGB value */
  private static final Color SELECTION = new Color(160, 183, 205);
  private static final int BORDER_WIDTH_MIN = 9;
  private static final int BORDER_WIDTH_MAX = 16;

  /** @param values
   * @return */
  public static <T> SpinnerLabel<T> of(@SuppressWarnings("unchecked") T... values) {
    SpinnerLabel<T> spinnerLabel = new SpinnerLabel<>();
    spinnerLabel.setArray(values);
    spinnerLabel.setIndex(0);
    return spinnerLabel;
  }

  // ==================================================
  private boolean mouseInside = false;
  private Point lastMouse = new Point();
  private int border_width = 0;
  private final List<SpinnerListener<T>> spinnerListeners = new LinkedList<>();

  @Override
  protected void paintComponent(Graphics _graphics) {
    // override non-editable background
    setBackground(new JTextField().getBackground());
    super.paintComponent(_graphics);
    // ---
    final boolean enabled = isEnabled();
    final boolean insideActive = mouseInside && enabled;
    Graphics2D graphics = (Graphics2D) _graphics;
    Dimension dimension = getSize(); // myJLabel.
    border_width = Math.min(Math.max(BORDER_WIDTH_MIN, BORDER_WIDTH_MIN - 2 + dimension.width / 10), BORDER_WIDTH_MAX);
    // ---
    if (isOverArrows(lastMouse) && enabled) {
      graphics.setColor(Color.WHITE);
      graphics.fillRect(dimension.width - border_width, 0, border_width, dimension.height);
    }
    // ---
    graphics.setColor(insideActive ? SELECTION : StaticHelper.alpha064(Color.LIGHT_GRAY));
    final int w = 3;
    final int r = dimension.width - 2 * w - 1;
    final int h = dimension.height - w - 1;
    {
      Path2D path2D = new Path2D.Double();
      path2D.moveTo(r, 1 + w);
      path2D.lineTo(r + 2 * w - 1, 1 + w);
      path2D.lineTo(r + w, 1);
      path2D.closePath();
      graphics.fill(path2D);
    }
    {
      Path2D path2D = new Path2D.Double();
      path2D.moveTo(r, h);
      path2D.lineTo(r + w, h + w);
      path2D.lineTo(r + 2 * w - 1, h);
      path2D.closePath();
      graphics.fill(path2D);
    }
  }

  private boolean isMenuEnabled = true;
  private boolean isMenuHover = false;
  private final LazyMouseListener lazyMouseListener = mouseEvent -> {
    if (mouseEvent.getButton() == MouseEvent.BUTTON1 && isEnabled()) {
      Dimension dimension = getSize();
      Point point = mouseEvent.getPoint();
      if (isOverArrows(point))
        increment(point.y < dimension.height / 2 ? -1 : 1); // sign of difference
      else //
      if (isMenuEnabled) {
        SpinnerMenu<T> spinnerMenu = new SpinnerMenu<>(this, isMenuHover);
        spinnerMenu.showRight(this);
      }
    }
  };

  public void setMenuEnabled(boolean isMenuEnabled) {
    this.isMenuEnabled = isMenuEnabled;
  }

  public void setMenuHover(boolean hover) {
    this.isMenuHover = hover;
  }

  // private int value = 0;
  private boolean cyclic = false;
  private int index = -1;
  List<T> list;

  public boolean isOverArrows(Point myPoint) {
    Dimension dimension = getSize();
    return mouseInside && dimension.width - border_width < myPoint.x;
  }

  public SpinnerLabel() {
    setEditable(false);
    addMouseWheelListener(mouseWheelEvent -> {
      if (isEnabled())
        increment(mouseWheelEvent.getWheelRotation());
    });
    MouseAdapter mouseAdapter = new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent mouseEvent) {
        mouseInside = true;
        lastMouse = mouseEvent.getPoint();
        repaint();
      }

      @Override
      public void mouseExited(MouseEvent mouseEvent) {
        mouseInside = false;
        repaint();
      }

      @Override
      public void mouseMoved(MouseEvent mouseEvent) {
        lastMouse = mouseEvent.getPoint();
        repaint(); // not very efficient
      }
    };
    addMouseListener(mouseAdapter);
    addMouseMotionListener(mouseAdapter);
    new LazyMouse(lazyMouseListener).addListenersTo(this);
  }

  public SpinnerLabel(SpinnerListener<T> spinnerListener) {
    this();
    addSpinnerListener(spinnerListener);
  }

  public void addSpinnerListener(SpinnerListener<T> spinnerListener) {
    spinnerListeners.add(spinnerListener);
  }

  public void setCyclic(boolean cyclic) {
    this.cyclic = cyclic;
  }

  public boolean getCyclic() {
    return cyclic;
  }

  private void increment(int delta) {
    int prev = index;
    index = cyclic //
        ? Math.floorMod(index + delta, numel())
        : Math.min(Math.max(0, index + delta), numel() - 1);
    if (index != prev) {
      updateLabel();
      reportToAll();
    }
  }

  public void reportToAll() {
    T type = getValue();
    spinnerListeners.forEach(spinnerListener -> spinnerListener.actionPerformed(type));
  }

  /** @param list
   * is used by reference. Any modification to myList is discouraged
   * and (eventually) reflected in the {@link SpinnerLabel}. */
  public void setList(List<T> list) {
    this.list = list;
  }

  public void setStream(Stream<T> stream) {
    setList(stream.collect(Collectors.toList()));
  }

  public void setArray(@SuppressWarnings("unchecked") T... values) {
    setList(Arrays.asList(values));
  }

  public T getValue() {
    return 0 <= index && index < numel() //
        ? list.get(index)
        : null;
  }

  public int getIndex() {
    return index;
  }

  private int numel() {
    return list == null ? 0 : list.size();
  }

  public List<T> getList() {
    return Collections.unmodifiableList(list);
  }

  /** does not invoke call backs
   * 
   * @param type */
  public void setValue(T type) {
    index = list.indexOf(type);
    updateLabel();
  }

  public void setValueSafe(T type) {
    try {
      setValue(type);
    } catch (Exception exception) {
      exception.printStackTrace();
      if (!list.isEmpty())
        setValue(list.get(0));
    }
  }

  public void setIndex(int index) {
    this.index = index;
    updateLabel();
  }

  private void updateLabel() {
    setText(String.valueOf(getValue()));
  }

  public void addToComponent(JComponent jComponent, Dimension dimension, String toolTip) {
    addToComponentReduced(jComponent, dimension, toolTip);
  }

  public void addToComponentReduced(JComponent jComponent, Dimension dimension, String toolTip) {
    setToolTipText(toolTip == null || toolTip.isEmpty() ? null : toolTip);
    setPreferredSize(dimension);
    jComponent.add(this);
  }
}
