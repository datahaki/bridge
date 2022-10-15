// code by jph
package ch.alpine.bridge.fig;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import ch.alpine.bridge.usr.SpectrogramDemo;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

public enum CascadeHelper {
  ;
  public static void draw(JFreeChart jFreeChart) {
    BufferedImage bufferedImage = new BufferedImage(400, 200, BufferedImage.TYPE_INT_ARGB);
    jFreeChart.draw(bufferedImage.createGraphics(), new Rectangle(0, 0, 400, 200));
  }

  public static void cascade(File folder, boolean labels) throws IOException {
    folder.mkdirs();
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 15);
    Tensor values3 = RandomVariate.of(UniformDistribution.unit(), 10);
    VisualSet visualSet = new VisualSet(ColorDataLists._250.cyclic());
    VisualRow row0 = visualSet.add(Range.of(0, values1.length()), values1);
    visualSet.add(Range.of(0, values2.length()), values2);
    VisualRow row2 = visualSet.add(Range.of(3, 3 + values3.length()), values3);
    if (labels) {
      row0.setLabel("row 0");
      row2.setLabel("row 2");
      visualSet.getAxisX().setLabel("x axis");
      visualSet.getAxisY().setLabel("y axis");
    }
    {
      visualSet.setPlotLabel(Histogram.class.getSimpleName());
      export(folder, Histogram.of(visualSet));
    }
    {
      visualSet.setPlotLabel(Histogram.class.getSimpleName() + "Function");
      export(folder, Histogram.of(visualSet, false, scalar -> "[" + scalar.toString() + "]"));
    }
    {
      visualSet.setPlotLabel(ListPlot.class.getSimpleName());
      export(folder, ListPlot.of(visualSet.setJoined(true)));
    }
    {
      export(folder, SpectrogramDemo.create());
    }
  }

  private static void export(File folder, JFreeChart jFreeChart) throws IOException {
    // File file = new File(folder, jFreeChart.getTitle().getText() + ".png");
    // ChartUtils.saveChartAsPNG(file, jFreeChart, new Dimension(500, 300));
  }
}
