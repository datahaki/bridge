// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import org.junit.jupiter.api.Test;

class ShowGridComponentTest {
  @Test
  void test() {
    List<Rectangle> list = GraphicsGrid.rectangles(5, new Dimension(200, 300));
    IO.println(list);
  }
}
