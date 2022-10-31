// code by jph
package demo.tensor;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.EqualizingDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.pdf.d.CategoricalDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum EqualizingDistributionDemo {
  ;
  public static void main(String[] args) {
    Tensor unscaledPDF = RandomVariate.of(UniformDistribution.unit(), 20);
    CategoricalDistribution dist1 = CategoricalDistribution.fromUnscaledPDF(unscaledPDF);
    Distribution dist2 = EqualizingDistribution.fromUnscaledPDF(unscaledPDF);
    Show show1 = new Show();
    show1.setPlotLabel("PDF");
    show1.add(ListPlot.of(dist1::at, Range.of(0, 20))).setLabel("CategoricalDistribution");
    show1.add(Plot.of(PDF.of(dist2)::at, Clips.positive(20))).setLabel("EqualizingDistribution");
    Show show2 = new Show();
    Clip clip = Clips.unit();
    show2.add(Plot.of(InverseCDF.of(dist1)::quantile, clip));
    show2.add(Plot.of(InverseCDF.of(dist2)::quantile, clip));
    ShowDialog.of(show1, show2);
  }
}
