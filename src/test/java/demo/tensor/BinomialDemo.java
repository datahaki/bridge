// code by jph
package demo.tensor;

import java.io.IOException;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.io.Export;
import ch.alpine.tensor.num.GaussScalar;

public enum BinomialDemo {
  ;
  public static Tensor row(Scalar n) {
    Tensor row = Tensors.of(n.one());
    Scalar top = n;
    for (Scalar j = n.one(); true; j = j.add(n.one())) {
      Scalar x = Last.of(row);
      row.append(x.multiply(top).divide(j));
      if (j.equals(n))
        return row;
      top = top.subtract(n.one());
    }
  }

  public static void main(String[] args) throws IOException {
    int prime = 251;
    Tensor tensor = Array.zeros(prime + 1, prime + 1);
    for (int i = 1; i < prime; ++i) {
      Scalar n = GaussScalar.of(i, prime);
      Tensor row = row(n);
      for (int k = 0; k < row.length(); ++k)
        tensor.set(RealScalar.of(row.Get(k).number()), i, k);
    }
    // Tensor rgba = tensor.map(ColorDataGradients.GRAYSCALE);
    Export.of(HomeDirectory.Pictures("gauss.png"), tensor);
  }
}
