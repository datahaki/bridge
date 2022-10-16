// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;

class VisualImageTest {
  @Test
  void testSimple() {
    CoordinateBoundingBox coordinateBoundingBox = CoordinateBoundingBox.of(Clips.unit(), Clips.positive(Quantity.of(3, "m")));
    VisualImage visualImage = new VisualImage(ImageFormat.of(Array.zeros(10, 20, 4)), coordinateBoundingBox);
    visualImage.getAxisX();
  }

  @Test
  void testSparse() {
    VisualImage visualImage = new VisualImage(ImageFormat.of(Array.sparse(10, 20, 4)), Clips.unit(), Clips.unit());
    visualImage.getPlotLabel();
  }

  @Test
  void testImage() {
    BufferedImage bufferedImage = ResourceData.bufferedImage("/ch/alpine/bridge/io/image/album_au_gray.jpg");
    VisualImage visualImage = new VisualImage(bufferedImage);
    assertEquals(visualImage.getAxisX().getOptionalClip().orElseThrow(), Clips.positive(bufferedImage.getWidth()));
    assertEquals(visualImage.getAxisY().getOptionalClip().orElseThrow(), Clips.positive(bufferedImage.getHeight()));
    Showable jFreeChart = ArrayPlot.of(visualImage);
    jFreeChart.draw(bufferedImage.createGraphics(), new Rectangle(0, 0, 200, 200));
  }
}
