// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ScreenRectanglesTest {
  @Test
  void testSimple() {
    ScreenRectangles screenRectangles = ScreenRectangles.create();
    Rectangle rectangle = new Rectangle(10, 10, 100, 100);
    Rectangle allVisible = screenRectangles.allVisible(rectangle);
    assertEquals(rectangle, allVisible);
  }

  @Test
  void testReal() {
    Rectangle r1 = new Rectangle(0, 0, 2560, 1440);
    Rectangle r2 = new Rectangle(2560, 0, 1920, 1080);
    List<Rectangle> list = Arrays.asList(r1, r2);
    ScreenRectangles screenRectangles = new ScreenRectangles(list);
    assertEquals(new Rectangle(), screenRectangles.allVisible(new Rectangle()));
    assertEquals(r1, screenRectangles.allVisible(r1));
    assertEquals(r2, screenRectangles.allVisible(r2));
    Rectangle r3 = new Rectangle(2560 + 10, 10, 19200, 10800);
    assertEquals(r2, screenRectangles.allVisible(r3));
    Rectangle r4 = new Rectangle(2560 + 10, 10, 200, 300);
    assertEquals(r4, screenRectangles.allVisible(r4));
  }

  @Test
  void testEmpty() {
    ScreenRectangles screenRectangles = new ScreenRectangles(Arrays.asList());
    Rectangle r3 = new Rectangle(2560 + 10, 10, 19200, 10800);
    assertEquals(r3, screenRectangles.allVisible(r3));
  }
}
