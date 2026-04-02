// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.fig.Rasterize;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.col.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clips;

class ImagePlotTest {
  @TempDir
  Path tempDir;

  @Test
  void testResolution() throws IOException {
    // the test exists to check whether the image size is constrained by 2^15-1
    // and finds that the image size may exceed that
    Tensor raw = RandomVariate.of(UniformDistribution.unit(), 2, Short.MAX_VALUE);
    BufferedImage bufferedImage = ImageFormat.of(raw.maps(ColorDataGradients.TEMPERATURE_WEATHER));
    Show show = new Show();
    show.add(ImagePlot.of(bufferedImage, CoordinateBoundingBox.of(Clips.unit(), Clips.unit())));
    new Rasterize(show, new Dimension(1000, 300)).export(tempDir.resolve("file.png"));
  }
}
