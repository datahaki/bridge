// code by jph
package ch.alpine.bridge.swing.rs;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class PaintThumbTest {
  @Test
  void test() {
    BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    PaintThumb.using(graphics, new Rectangle(10, 20, 30, 40));
    graphics.dispose();
  }
}
