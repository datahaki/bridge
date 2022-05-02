// code by jph
package ch.alpine.bridge.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class RenderQualityTest {
  @Test
  public void testSimple() {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    RenderQuality.setQuality(graphics);
    RenderQuality.setDefault(graphics);
  }
}
