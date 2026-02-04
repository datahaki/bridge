// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

class RenderQualityTest {
  private static ThreadLocal<Deque<Integer>> mathContextStack = new ThreadLocal<>() {
    protected Deque<Integer> initialValue() {
      return new ArrayDeque<>();
    }
  };

  @Test
  void testSimple() {
    mathContextStack.get().push(3);
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    RenderQuality.setQuality(graphics);
    RenderQuality.setDefault(graphics);
    graphics.dispose();
    assertEquals(mathContextStack.get().pop(), 3);
  }
}
