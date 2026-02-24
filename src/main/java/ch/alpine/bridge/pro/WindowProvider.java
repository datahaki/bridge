// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import javax.imageio.ImageIO;

import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.swing.LookAndFeels;

public non-sealed interface WindowProvider extends RunProvider {
  /** Careful: multiple invocations may return the same instance, or a new window
   * 
   * @return */
  Window getWindow();

  @Override
  default Window runStandalone() {
    ImageIO.setUseCache(false);
    LookAndFeels.autoDetect();
    Window window = getWindow();
    ResourceLocator resourceLocator = ResourceLocator.of(getClass());
    WindowBounds.persistent(window, resourceLocator.properties(WindowBounds.class));
    window.setVisible(true);
    return window;
  }
}
