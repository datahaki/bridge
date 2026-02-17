// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowWindow;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.swing.LookAndFeels;

@FunctionalInterface
public interface ShowProvider {
  Show getShow();

  default Window run() {
    LookAndFeels.autoDetect();
    Window window = ShowWindow.asFrame(getShow());
    ResourceLocator resourceLocator = new ResourceLocator(StaticHelper.of(getClass()));
    WindowBounds.persistent(window, resourceLocator.properties(WindowBounds.class));
    window.setVisible(true);
    return window;
  }
}
