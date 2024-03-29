// code by jph
package demo.tensor;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
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
        c1 = influenceMatrix.matrix();
        t1.append(Tensors.vector(n, timing1.seconds()));
      }
      {
        Timing timing2 = Timing.started();
        InfluenceMatrix influenceMatrix = InfluenceMatrix.of(design);
        c2 = influenceMatrix.matrix();
        t2.append(Tensors.vector(n, timing2.seconds()));
      }
      if (!Chop._04.isClose(c1, c2))
        System.err.println("warning");
    }
    Show show = new Show();
    show.add(ListLinePlot.of(t1)).setLabel("mahs");
    show.add(ListLinePlot.of(t2)).setLabel("inf");
    show.export(HomeDirectory.file("matrix_rect2.png"), new Dimension(500, 300));
    ShowDialog.of(show);
  }
}
