// code by jph
package ch.alpine.java.awt;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface LazyMouseListener {
  void lazyClicked(MouseEvent mouseEvent);

  default void lazyDragged(MouseEvent mouseEvent) {
    // empty by default
  }
}
