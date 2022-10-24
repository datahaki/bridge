// code by jph
package ch.alpine.bridge.fig;

import java.io.File;
import java.io.IOException;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

public enum CascadeHelper {
  ;
  public static void draw(Showable jFreeChart) {
    // BufferedImage bufferedImage = new BufferedImage(400, 200, BufferedImage.TYPE_INT_ARGB);
    // jFreeChart.draw(bufferedImage.createGraphics(), new Rectangle(0, 0, 400, 200));
  }

  public static void cascade(File folder, boolean labels) throws IOException {
    folder.mkdirs();
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 15);
    Tensor values3 = RandomVariate.of(UniformDistribution.unit(), 10);
    Show show = new Show(ColorDataLists._250.cyclic());
    Showable row0 = show.add(ListPlot.of(Range.of(0, values1.length()), values1));
    show.add(ListPlot.of(Range.of(0, values2.length()), values2));
    Showable row2 = show.add(ListPlot.of(Range.of(3, 3 + values3.length()), values3));
    if (labels) {
      row0.setLabel("row 0");
      row2.setLabel("row 2");
      // show.getAxisX().setLabel("x axis");
      // show.getAxisY().setLabel("y axis");
    }
    {
      show.setPlotLabel(Histogram.class.getSimpleName());
      export(folder, Histogram.of(show));
    }
    {
      show.setPlotLabel(Histogram.class.getSimpleName() + "Function");
      export(folder, Histogram.of(show, false, scalar -> "[" + scalar.toString() + "]"));
    }
    {
      show.setPlotLabel(ListPlot.class.getSimpleName());
      // export(folder, ListPlot.of(show.setJoined(true)));
    }
    // {
    // ShowDemos.SPECTROGRAM0.create();
    // export(folder, SpectrogramDemo.create());
    // }
  }

  private static void export(File folder, Showable jFreeChart) throws IOException {
    // File file = new File(folder, jFreeChart.getTitle().getText() + ".png");
    // ChartUtils.saveChartAsPNG(file, jFreeChart, new Dimension(500, 300));
  }
}
