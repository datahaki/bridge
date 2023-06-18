// code by jph
package ch.alpine.bridge.awt;

import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

class LazyMouseListenerTest {
  @Test
  void test() {
    LazyMouseListener lazyMouseListener = new LazyMouseListener() {
      @Override
      public void lazyClicked(MouseEvent mouseEvent) {
        // ---
      }

      @Override
      public void lazyDragged(MouseEvent mouseEvent) {
        LazyMouseListener.super.lazyDragged(mouseEvent);
      }
    };
    lazyMouseListener.lazyDragged(null);
  }
}
