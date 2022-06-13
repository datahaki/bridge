// code by GRZ Technologies SA, jph
package ch.alpine.bridge.fig;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class ArrayPlotTest {
  @Test
  void testSimple() {
    TestHelper.draw(ArrayPlotDemo.create());
  }

  @Test
  void testResolution(@TempDir File tempDir) throws IOException {
    // the test exists to check whether the image size is constrained by 2^16
    // and finds that the image size may exceed that
    Tensor raw = RandomVariate.of(UniformDistribution.unit(), 2, 70000);
    BufferedImage bufferedImage = ImageFormat.of(raw.map(ColorDataGradients.TEMPERATURE_WEATHER));
    VisualImage visualImage = new VisualImage(bufferedImage);
    JFreeChart jFreeChart = ArrayPlot.of(visualImage);
    ChartUtils.saveChartAsPNG(new File(tempDir, "file.png"), jFreeChart, 1000, 300);
  }
}
