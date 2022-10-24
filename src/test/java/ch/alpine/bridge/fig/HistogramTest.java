// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.Tensors;

class HistogramTest {
  @Test
  void testEmpty() {
    Show visualSet = new Show();
    CascadeHelper.draw(Histogram.of(visualSet));
  }

  @Test
  void testEmptyRow() {
    Show show = new Show();
    show.add(ListPlot.of(Tensors.empty()));
    CascadeHelper.draw(Histogram.of(show));
  }

  @Test
  void testQuantity(@TempDir File folder) throws IOException {
    Show show = new Show();
    show.add(ListPlot.of(Tensors.fromString("{{2[m],3[s]}, {4[m],5[s]}, {5[m],1[s]}}")));
    Showable jFreeChart = Histogram.of(show);
    show.export(new File(folder, "histunit.png"), new Dimension(640, 480));
    CascadeHelper.draw(jFreeChart);
    // ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("histunit.png"), jFreeChart, 640, 480);
  }

  @Test
  void testTruncated(@TempDir File folder) throws IOException {
    // Showable jFreeChart = TruncatedDiscreteDemo.generate();
    // Show.export(new File(folder, "truncated.png"), jFreeChart, new Dimension(640, 480));
  }
}
