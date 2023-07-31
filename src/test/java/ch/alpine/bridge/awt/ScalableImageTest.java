package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Image;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class ScalableImageTest {
  @Test
  void test() {
    BufferedImage bufferedImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
    ScalableImage scalableImage = new ScalableImage(bufferedImage, Image.SCALE_SMOOTH);
    Image im1 = scalableImage.getScaledInstance(30, 20);
    Image im2 = scalableImage.getScaledInstance(30, 20);
    assertSame(im1, im2);
    Image im3 = scalableImage.getScaledInstance(30, 21);
    assertNotSame(im1, im3);
    Image im4 = scalableImage.getScaledInstance(200, 100);
    assertSame(bufferedImage, im4);
  }
}
