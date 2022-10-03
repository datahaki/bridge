// code by jph
package demo.tensor.prc;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.prc.PoissonProcess;
import ch.alpine.tensor.prc.RandomFunction;
import ch.alpine.tensor.prc.RandomProcess;
import ch.alpine.tensor.prc.WienerProcess;

/* package */ enum ProcessGalleryDemo {
  ;
  private static void export(RandomProcess randomProcess) throws IOException {
    RandomFunction randomFunction = RandomFunction.of(randomProcess);
    randomFunction.apply(RealScalar.of(20));
    VisualSet visualSet = new VisualSet();
    visualSet.add(randomFunction.path());
    JFreeChart jFreeChart = ListPlot.of(visualSet);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG( //
        HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + ".png"), //
        jFreeChart, 640, 480);
  }

  private static void exportChaotic() throws IOException {
    RandomProcess randomProcess = WienerProcess.of(0, 1);
    RandomFunction randomFunction = RandomFunction.of(randomProcess);
    RandomVariate.of(UniformDistribution.of(0, 10), 100).stream() //
        .map(Scalar.class::cast) //
        .forEach(randomFunction::apply);
    VisualSet visualSet = new VisualSet();
    visualSet.add(randomFunction.path());
    JFreeChart jFreeChart = ListPlot.of(visualSet);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG( //
        HomeDirectory.Pictures(randomProcess.getClass().getSimpleName() + ".png"), //
        jFreeChart, 640, 480);
  }

  public static void main(String[] args) throws IOException {
    // export(BinomialProcess.of(0.3));
    // export(BernoulliProcess.of(0.3));
    export(PoissonProcess.of(10));
    exportChaotic();
  }
}
