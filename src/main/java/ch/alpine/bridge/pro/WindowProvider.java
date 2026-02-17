// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.swing.LookAndFeels;

@FunctionalInterface
public interface WindowProvider {
  /** Careful: multiple invocations may return the same instance, or a new window
   * 
   * @return */
  Window getWindow();

  default Window run() {
    LookAndFeels.autoDetect();
    Window window = getWindow();
    ResourceLocator resourceLocator = new ResourceLocator(StaticHelper.of(getClass()));
    WindowBounds.persistent(window, resourceLocator.properties(WindowBounds.class));
    window.setVisible(true);
    return window;
  }
}
