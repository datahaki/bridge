// code by jph
package ch.alpine.bridge.awt;

import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Objects;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.tensor.ext.Timing;

/** loads and saves window position
 * 
 * uses reflection for storage and recovery
 * 
 * @see ObjectProperties */
@ReflectionMarker
public class WindowBounds {
  /** function is typically called before the given window is made visible
   * 
   * loads previous window bounds from file, and attaches listeners to
   * track the window positioning and resizing. when the window is closed,
   * the last bounds are stored in the given file.
   * 
   * @param window
   * @param file */
  public static void persistent(Window window, File file) {
    WindowBounds windowBounds = ObjectProperties.tryLoad(new WindowBounds(), file);
    windowBounds.private_attach(window, file);
  }

  // ---
  public Rectangle rectangle = new Rectangle(100, 100, 800, 800);

  private WindowBounds() {
    // ---
  }

  public Rectangle getBoundsAllVisible() {
    return ScreenRectangles.create().allVisible(rectangle);
  }

  private final Point shift = new Point();

  private void private_attach(Window window, File file) {
    window.setBounds(getBoundsAllVisible());
    WindowBounds windowBounds = this;
    WindowClosed.runs(window, () -> {
      Rectangle rectangle = window.getBounds();
      rectangle.x -= shift.x;
      rectangle.y -= shift.y;
      windowBounds.rectangle = rectangle;
      ObjectProperties.trySave(windowBounds, file);
    });
    window.addComponentListener(new ComponentAdapter() {
      private final Timing timing = Timing.stopped();
      private Point shown = null;

      @Override
      public void componentShown(ComponentEvent componentEvent) {
        try {
          shown = window.getLocationOnScreen();
        } catch (IllegalComponentStateException illegalComponentStateException) {
          String message = illegalComponentStateException.getMessage();
          if (!message.equals("component must be showing on the screen to determine its location"))
            System.err.println("WindowBounds " + message);
        }
        timing.start();
      }

      @Override
      public void componentMoved(ComponentEvent componentEvent) {
        long nanos = timing.nanoSeconds(); // 45846090
        // System.out.println("ns=" + nanos);
        if (nanos < 300_000_000 && Objects.nonNull(shown)) {
          Point moved = window.getLocationOnScreen();
          // System.out.println("moved location: " + jFrame.getLocationOnScreen());
          shift.x = moved.x - shown.x;
          shift.y = moved.y - shown.y;
          if (shift.x != 0) {
            System.err.println("WindowBounds shift=" + shift + " -> reset");
            shift.x = 0;
            shift.y = 0;
          }
        } else {
          System.err.println("WindowBounds nanos=" + nanos);
        }
        window.removeComponentListener(this);
      }
    });
  }
}
