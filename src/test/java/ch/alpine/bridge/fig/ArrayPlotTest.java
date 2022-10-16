// code by GRZ Technologies SA, jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.usr.ArrayPlotDemo;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class ArrayPlotTest {
  @Test
  void testSimple() {
    CascadeHelper.draw(ArrayPlotDemo.create0());
  }

  @Test
  void testResolution(@TempDir File tempDir) throws IOException {
    // the test exists to check whether the image size is constrained by 2^16
    // and finds that the image size may exceed that
    Tensor raw = RandomVariate.of(UniformDistribution.unit(), 2, 70000);
    BufferedImage bufferedImage = ImageFormat.of(raw.map(ColorDataGradients.TEMPERATURE_WEATHER));
    VisualImage visualImage = new VisualImage(bufferedImage);
    Showable jFreeChart = ArrayPlot.of(visualImage);
    Show.export(new File(tempDir, "file.png"), jFreeChart, new Dimension(1000, 300));
  }
}
