package ch.alpine.bridge.lang;

import java.awt.Window;

@FunctionalInterface
public interface WindowProvider {
  /** Careful: multiple invocations may return the same instance, or a new window
   * 
   * @return */
  Window getWindow();

  default Window run() {
    Window window = getWindow();
    window.setVisible(true);
    return window;
  }
}
