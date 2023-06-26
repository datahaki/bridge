// code by jph
package ch.alpine.bridge.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JTextField;

import ch.alpine.bridge.awt.LazyMouse;
import ch.alpine.bridge.awt.LazyMouseListener;
import ch.alpine.bridge.util.CopyOnWriteLinkedSet;
import ch.alpine.tensor.ext.Integers;

/** selector in gui for easy scrolling through a list with mouse-wheel
 * and menu to the side upon mouse-click
 * extends from a non-editable text field therefore the name "label" */
public abstract class SpinnerLabel<T> extends JTextField {
  /** JToggleButton background when selected is 184 207 229 selection color
   * subtracts 24 from each RGB value */
  private static final Color SELECTION = new Color(160, 183, 205);
  private static final int BORDER_WIDTH_MIN = 9;
  private static final int BORDER_WIDTH_MAX = 16;

  /** @param supplier
   * @return */
  public static <T> SpinnerLabel<T> of(Supplier<List<T>> supplier) {
    return new SpinnerLabel<>() {
      @Override
      public List<T> getList() {
        return supplier.get();
      }
    };
  }

  /** Careful: any outside modification of given list is reflected in gui
   * 
   * @param list
   * @return */
  public static <T> SpinnerLabel<T> of(List<T> list) {
    return of(() -> list);
  }

  /** @param values
   * @return */
  @SafeVarargs
  public static <T> SpinnerLabel<T> of(T... values) {
    return of(List.of(values));
  }

  public static <T extends Enum<T>> SpinnerLabel<T> of(Class<T> cls) {
    return of(cls.getEnumConstants());
  }

  // ---
  private final Set<SpinnerListener<T>> spinnerListeners = new CopyOnWriteLinkedSet<>();
  private T value;
  private boolean mouseInside = false;
  private Point lastMouse = new Point();
  private int border_width = 0;
  private boolean isMenuEnabled = true;
  private boolean isMenuHover = false;
  private boolean cyclic = false;

  @Override
  protected void paintComponent(Graphics _graphics) {
    // override background of non-editable textfield with background of editable textfield
    setBackground(UIManagerColor.TextField_background.get());
    super.paintComponent(_graphics);
    // ---
    final boolean enabled = isEnabled();
    final boolean insideActive = mouseInside && enabled;
    Graphics2D graphics = (Graphics2D) _graphics;
    Dimension dimension = getSize();
    border_width = Integers.clip(BORDER_WIDTH_MIN, BORDER_WIDTH_MAX).applyAsInt(BORDER_WIDTH_MIN - 2 + dimension.width / 10);
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

  private SpinnerLabel() {
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
    // sign of difference
    //
    LazyMouseListener lazyMouseListener = mouseEvent -> {
      if (mouseEvent.getButton() == MouseEvent.BUTTON1 && isEnabled()) {
        Dimension dimension = getSize();
        Point point = mouseEvent.getPoint();
        if (isOverArrows(point))
          increment(point.y < dimension.height / 2 ? -1 : 1); // sign of difference
        else //
        if (isMenuEnabled) {
          SpinnerMenu<T> spinnerMenu = new SpinnerMenu<>(getList(), getValue(), Object::toString, isMenuHover);
          spinnerMenu.setFont(getFont());
          spinnerMenu.addSpinnerListener(type -> {
            setValue(type);
            reportToAll();
          });
          spinnerMenu.showRight(this);
        }
      }
    };
    new LazyMouse(lazyMouseListener).addListenersTo(this);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_LEFT: {
          increment(-1);
          break;
        }
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_RIGHT: {
          increment(+1);
          break;
        }
        default:
          break;
        }
      }
    });
  }

  public void addSpinnerListener(SpinnerListener<T> spinnerListener) {
    spinnerListeners.add(spinnerListener);
  }

  public void setMenuEnabled(boolean isMenuEnabled) {
    this.isMenuEnabled = isMenuEnabled;
  }

  public void setMenuHover(boolean hover) {
    isMenuHover = hover;
  }

  public boolean isOverArrows(Point point) {
    Dimension dimension = getSize();
    return mouseInside && dimension.width - border_width < point.x;
  }

  public void setCyclic(boolean cyclic) {
    this.cyclic = cyclic;
  }

  public boolean getCyclic() {
    return cyclic;
  }

  private void increment(int delta) {
    List<T> list = getList();
    int prev = list.indexOf(value);
    if (prev < 0)
      return;
    int index = cyclic //
        ? Math.floorMod(prev + delta, list.size())
        : Integers.clip(0, list.size() - 1).applyAsInt(prev + delta);
    if (index != prev) {
      setValue(list.get(index));
      reportToAll();
    }
  }

  public void reportToAll() {
    T type = getValue();
    spinnerListeners.forEach(spinnerListener -> spinnerListener.spun(type));
  }

  public T getValue() {
    return value;
  }

  public abstract List<T> getList();

  /** does not invoke callbacks
   * 
   * @param type non-null */
  public void setValue(T type) {
    value = Objects.requireNonNull(type);
    setText(String.valueOf(getValue()));
  }

  public void addToComponent(JComponent jComponent, String toolTip) {
    setToolTipText(Objects.isNull(toolTip) || toolTip.isEmpty() ? null : toolTip);
    updatePreferredSize();
    jComponent.add(this);
  }

  public Dimension updatePreferredSize() {
    FontMetrics metrics = getFontMetrics(getFont());
    int max = getList().stream() //
        .map(Object::toString) //
        .mapToInt(metrics::stringWidth) //
        .max().orElse(0);
    Dimension dimension = getPreferredSize();
    dimension.width = max + 2 * metrics.charWidth('\u3000');
    setPreferredSize(dimension);
    return dimension;
  }
}
