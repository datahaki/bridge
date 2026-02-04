package ch.alpine.bridge.util;

import java.awt.Window;

@FunctionalInterface
public interface WindowSupplier {
  Window createWindow();

  default Window run() {
    Window window = createWindow();
    window.setVisible(true);
    return window;
  }
}
