// code by jph
package demo.tensor;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.ext.Timing;
import ch.alpine.tensor.mat.gr.InfluenceMatrix;
import ch.alpine.tensor.mat.gr.Mahalanobis;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.sca.Chop;

public enum InfluenceMatrixDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution distribution = NormalDistribution.standard();
    Tensor t1 = Tensors.empty();
    Tensor t2 = Tensors.empty();
    for (int n = 10; n < 80; ++n) {
      Tensor design = RandomVariate.of(distribution, n, n - 3);
      // Tensor vector = RandomVariate.of(distribution, n);
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
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.file("matrix_rect2.png"), jFreeChart, 500, 300);
  }
}
