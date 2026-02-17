// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;
import java.awt.Window;

import ch.alpine.bridge.awt.WindowBounds;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.io.ResourceLocator;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.swing.LookAndFeels;

/** implementing classes are subject to introspection
 * 
 * @see ReflectionMarker */
@FunctionalInterface
public interface ManipulateProvider {
  Container getContainer();

  /** @return
   * @apiNote should not be used for testing */
  default Window run() {
    LookAndFeels.autoDetect();
    ResourceLocator resourceLocator = new ResourceLocator(StaticHelper.of(getClass()));
    resourceLocator.tryLoad(this);
    Window window = Manipulate.asFrame(this, this::getContainer);
    WindowBounds.persistent(window, resourceLocator.properties(WindowBounds.class));
    WindowClosed.runs(window, () -> resourceLocator.trySave(this));
    window.setVisible(true);
    return window;
  }
}
