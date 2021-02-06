// code by jph
package ch.ethz.idsc.java.usr;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ext.HomeDirectory;
import ch.ethz.idsc.tensor.ext.Timing;
import ch.ethz.idsc.tensor.fig.ListPlot;
import ch.ethz.idsc.tensor.fig.VisualSet;
import ch.ethz.idsc.tensor.mat.InfluenceMatrix;
import ch.ethz.idsc.tensor.mat.Mahalanobis;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;

public enum InfluenceMatrixDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution distribution = NormalDistribution.standard();
    Tensor t1 = Tensors.empty();
    Tensor t2 = Tensors.empty();
    for (int n = 10; n < 80; ++n) {
      Tensor design = RandomVariate.of(distribution, n, n - 3);
      Tensor vector = RandomVariate.of(distribution, n);
      Tensor c1;
      Tensor c2;
      {
        Timing timing1 = Timing.started();
        InfluenceMatrix influenceMatrix = new Mahalanobis(design);
        // c1 = influenceMatrix.kernel(influenceMatrix.leverages_sqrt());
        // c1 = influenceMatrix.kernel(vector);
        c1 = influenceMatrix.matrix();
        t1.append(Tensors.vector(n, timing1.seconds()));
      }
      {
        Timing timing2 = Timing.started();
        InfluenceMatrix influenceMatrix = InfluenceMatrix.of(design);
        // c2 = influenceMatrix.kernel(influenceMatrix.leverages_sqrt());
        // c2 = influenceMatrix.kernel(vector);
        c2 = influenceMatrix.matrix();
        t2.append(Tensors.vector(n, timing2.seconds()));
      }
      if (!Chop._04.isClose(c1, c2))
        System.err.println("warning");
    }
    VisualSet visualSet = new VisualSet();
    visualSet.add(t1).setLabel("mahs");
    visualSet.add(t2).setLabel("inf");
    JFreeChart jFreeChart = ListPlot.of(visualSet);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.file("matrix_rect2.png"), jFreeChart, 500, 300);
  }
}
