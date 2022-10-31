// code by jph
package demo.tensor;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum HistogramDistributionDemo {
  ;
  public static void main(String[] args) {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    Show show1 = new Show();
    Show show2 = new Show();
    {
      Clip clip = Clips.interval(-5, 8);
      show1.add(Plot.of(dist::at, clip)).setLabel("PDF");
      show1.add(Plot.of(dist::p_lessEquals, clip)).setLabel("CDF");
      show1.add(Plot.of(distribution::at, clip)).setLabel("PDF");
      show1.add(Plot.of(distribution::p_lessEquals, clip)).setLabel("CDF");
    }
    {
      Clip clip = Clips.unit();
      show2.setPlotLabel("InverseCDF");
      show2.add(Plot.of(InverseCDF.of(distribution)::quantile, clip));
      show2.add(Plot.of(InverseCDF.of(dist)::quantile, clip));
    }
    ShowDialog.of(show1, show2);
  }
}
