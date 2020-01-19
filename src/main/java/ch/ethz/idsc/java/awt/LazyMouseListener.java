// code by jph
package ch.ethz.idsc.java.awt;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface LazyMouseListener {
  void lazyClicked(MouseEvent mouseEvent);

  default void lazyDragged(MouseEvent mouseEvent) {
    // empty by default
  }
}
