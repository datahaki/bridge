// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.Tensors;
import demo.tensor.pdf.TruncatedDiscreteDemo;

class HistogramTest {
  @Test
  void testEmpty() {
    Show visualSet = new Show();
    CascadeHelper.draw(Histogram.of(visualSet));
  }

  @Test
  void testEmptyRow() {
    Show visualSet = new Show();
    visualSet.add(Tensors.empty());
    CascadeHelper.draw(Histogram.of(visualSet));
  }

  @Test
  void testQuantity(@TempDir File folder) throws IOException {
    Show visualSet = new Show();
    visualSet.add(Tensors.fromString("{{2[m],3[s]}, {4[m],5[s]}, {5[m],1[s]}}"));
    Showable jFreeChart = Histogram.of(visualSet);
    Show.export(new File(folder, "histunit.png"), jFreeChart, new Dimension(640, 480));
    CascadeHelper.draw(jFreeChart);
    // ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("histunit.png"), jFreeChart, 640, 480);
  }

  @Test
  void testTruncated(@TempDir File folder) throws IOException {
    Showable jFreeChart = TruncatedDiscreteDemo.generate();
    Show.export(new File(folder, "truncated.png"), jFreeChart, new Dimension(640, 480));
  }
}
