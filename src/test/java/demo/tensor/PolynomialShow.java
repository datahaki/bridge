// code by jph
package demo.tensor;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.ply.Polynomial;

/* package */ enum PolynomialShow {
  ;
  public static void main(String[] args) throws IOException {
    for (int degree = 0; degree <= 4; ++degree) {
      Tensor x = Tensors.fromString("{100[K], 110.0[K], 120[K], 133[K], 140[K], 150[K]}");
      Tensor y = Tensors.fromString("{10[bar], 20[bar], 22[bar], 23[bar], 25[bar], 26.0[bar]}");
      ScalarUnaryOperator x_to_y = Polynomial.fit(x, y, degree);
      ScalarUnaryOperator y_to_x = Polynomial.fit(y, x, degree);
      Clip domain_x = Clips.interval(Quantity.of(100, "K"), Quantity.of(150, "K"));
      Tensor samples_x = Subdivide.of(Quantity.of(100, "K"), Quantity.of(150, "K"), 50);
      Tensor samples_y = Subdivide.of(Quantity.of(10, "bar"), Quantity.of(26, "bar"), 50);
      samples_x.map(x_to_y);
      samples_y.map(y_to_x);
      Show show = new Show();
      show.add(Plot.of(x_to_y, domain_x));
      show.add(ListLinePlot.of(samples_y.map(y_to_x), samples_y));
      show.export(HomeDirectory.Pictures("here" + degree + ".png"), new Dimension(400, 300));
    }
  }
}
