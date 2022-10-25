// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

/* package */ enum ListPlotInfDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor valuesX = Range.of(0, 10);
    Tensor valuesY = RandomVariate.of(UniformDistribution.unit(), 10);
    valuesY.set(DoubleScalar.POSITIVE_INFINITY, 3);
    Show show = new Show();
    show.setPlotLabel("List Plot Demo");
    Showable visualRow1 = show.add(ListLinePlot.of(valuesX, valuesY));
    visualRow1.setLabel("first");
    {
      File file = HomeDirectory.Pictures(ListPlotInfDemo.class.getSimpleName() + ".png");
      show.export(file, new Dimension(500, 300));
    }
  }
}
