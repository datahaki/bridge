// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Image;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class ScalableImageTest {
  @Test
  void test() {
    BufferedImage bufferedImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
    ScalableImage scalableImage = new ScalableImage(bufferedImage);
    Image im1 = scalableImage.getScaledInstance(30, 20, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    Image im2 = scalableImage.getScaledInstance(30, 20, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    assertSame(im1, im2);
    Image im3 = scalableImage.getScaledInstance(30, 21, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    assertNotSame(im1, im3);
    Image im4 = scalableImage.getScaledInstance(200, 100, AffineTransformOp.TYPE_BICUBIC);
    Image im5 = scalableImage.getScaledInstance(200, 100, AffineTransformOp.TYPE_BICUBIC);
    assertSame(im4, im5);
    Image im6 = scalableImage.getScaledInstance(200, 100, AffineTransformOp.TYPE_BILINEAR);
    assertNotSame(im5, im6);
  }
}
