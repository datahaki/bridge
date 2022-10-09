// code by jph
package ch.alpine.bridge.usr;

import java.io.IOException;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Reverse;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.Export;
import ch.alpine.tensor.itp.BSplineInterpolation;
import ch.alpine.tensor.itp.Interpolation;
import ch.alpine.tensor.sca.tri.Sin;

public enum ListDensityPlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    // data = Table[Sin[j^2 + i], {i, 0, Pi, Pi/5}, {j, 0, Pi, Pi/5}];
    Tensor d = Subdivide.of(0, Math.PI, 5);
    Tensor matrix = Tensors.matrix((i, j) -> Sin.FUNCTION.apply(d.Get(j).multiply(d.Get(j)).add(d.Get(i))), 6, 6);
    System.out.println(Dimensions.of(matrix));
    for (int degree = 0; degree < 5; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, matrix);
      Tensor x = Subdivide.of(0, 5, 50);
      Tensor y = Reverse.of(x);
      Tensor eval = Tensors.matrix((i, j) -> interpolation.get( //
          Tensors.of(y.Get(i), x.Get(j))), x.length(), x.length());
      Tensor tensor = Rescale.of(eval).map(ColorDataGradients.CLASSIC);
      Export.of(HomeDirectory.Pictures(ListDensityPlotDemo.class.getSimpleName() + degree + ".png"), tensor);
    }
  }
}
