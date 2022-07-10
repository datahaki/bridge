// code by jph
package ch.alpine.bridge.awt;

import org.junit.jupiter.api.Test;

class ScreenRectangleTest {
  @Test
  void testSimple() {
    ScreenRectangle screenRectangle = new ScreenRectangle();
    screenRectangle.getScreenRectangle();
    screenRectangle.allVisible(10, 10, 100, 100);
    screenRectangle.toString();
  }
}
