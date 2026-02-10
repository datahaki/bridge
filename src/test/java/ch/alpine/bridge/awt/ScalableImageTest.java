// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Image;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.img.ImageResize;

class ScalableImageTest {
  @Test
  void test() {
    BufferedImage bufferedImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
    ScalableImage scalableImage = new ScalableImage(bufferedImage);
    Image im1 = scalableImage.getScaledInstance(ImageResize.DEGREE_0, 30, 20);
    Image im2 = scalableImage.getScaledInstance(ImageResize.DEGREE_0, 30, 20);
    assertSame(im1, im2);
    Image im3 = scalableImage.getScaledInstance(ImageResize.DEGREE_0, 30, 21);
    assertNotSame(im1, im3);
    Image im4 = scalableImage.getScaledInstance(ImageResize.DEGREE_3, 200, 100);
    Image im5 = scalableImage.getScaledInstance(ImageResize.DEGREE_3, 200, 100);
    assertSame(im4, im5);
    Image im6 = scalableImage.getScaledInstance(ImageResize.DEGREE_1, 200, 100);
    assertNotSame(im5, im6);
  }
}
