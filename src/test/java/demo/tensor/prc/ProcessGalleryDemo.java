// code by jph
package demo.tensor.prc;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualRow;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.prc.PoissonProcess;
import ch.alpine.tensor.prc.RandomFunction;
import ch.alpine.tensor.prc.RandomProcess;
import ch.alpine.tensor.prc.WhiteNoiseProcess;
import ch.alpine.tensor.prc.WienerProcess;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.tmp.TimeSeries;
import ch.alpine.tensor.tmp.TimeSeriesIntegrate;

/* package */ enum ProcessGalleryDemo {
  ;
  private static void export(RandomProcess randomProcess) throws IOException {
    RandomFunction randomFunction = RandomFunction.of(randomProcess);
    randomFunction.evaluate(RealScalar.of(20));
    VisualSet visualSet = new VisualSet();
    visualSet.add(randomFunction.timeSeries());
    JFreeChart jFreeChart = ListPlot.of(visualSet);
    ChartUtils.saveChartAsPNG( //
        HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + ".png"), //
        jFreeChart, new Dimension(640, 480));
  }

  private static void exportChaotic() throws IOException {
    Scalar mu = Quantity.of(1, "m*s^-1");
    Scalar sigma = Quantity.of(2, "m*s^-1/2");
    RandomProcess randomProcess = WienerProcess.of(mu, sigma);
    RandomFunction randomFunction = RandomFunction.of(randomProcess);
    RandomVariate.of(UniformDistribution.of(Clips.positive(Quantity.of(10, "s"))), 1000).stream() //
        .map(Scalar.class::cast) //
        .forEach(randomFunction::evaluate);
    {
      VisualSet visualSet = new VisualSet();
      VisualRow visualRow = visualSet.add(randomFunction.timeSeries());
      visualRow.setLabel("wiener process");
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG( //
          HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + ".png"), //
          jFreeChart, new Dimension(640, 480));
    }
    {
      TimeSeries integral = TimeSeriesIntegrate.of(randomFunction.timeSeries());
      VisualSet visualSet = new VisualSet();
      VisualRow visualRow = visualSet.add(integral);
      visualRow.setLabel("wiener process");
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG( //
          HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + "_i.png"), //
          jFreeChart, new Dimension(640, 480));
    }
  }

  public static void main(String[] args) throws IOException {
    // export(BinomialProcess.of(0.3));
    // export(BernoulliProcess.of(0.3));
    export(PoissonProcess.of(10));
    export(WhiteNoiseProcess.instance());
    exportChaotic();
  }
}
