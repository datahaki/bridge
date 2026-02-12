// code by jph
package ch.alpine.bridge.pro;

import java.awt.Window;

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
    window.setVisible(true);
    return window;
  }
}
