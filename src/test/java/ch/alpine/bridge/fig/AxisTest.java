// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class AxisTest {
  @Test
  void testDefaultFontOfGraphics() {
    BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2d = bi.createGraphics();
    Font font = graphics2d.getFont();
    assertEquals(font.getFamily(), Font.DIALOG);
    assertEquals(font.getStyle(), Font.PLAIN);
    assertEquals(font.getSize(), 12);
  }
}
