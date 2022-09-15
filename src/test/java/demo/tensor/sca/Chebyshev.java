// code by jph
package demo.tensor.sca;

import java.util.ArrayList;
import java.util.List;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.num.Polynomial;

public enum Chebyshev {
  ;
  private static final List<Polynomial> MEMO = new ArrayList<>();
  static {
    MEMO.add(Polynomial.of(Tensors.vector(1)));
    MEMO.add(Polynomial.of(Tensors.vector(0, 1)));
  }

  public static Polynomial of(int index) {
    if (MEMO.size() <= Integers.requirePositiveOrZero(index))
      synchronized (MEMO) {
        while (MEMO.size() <= index) {
          Polynomial pnm1 = MEMO.get(MEMO.size() - 1);
          Polynomial pnm2 = MEMO.get(MEMO.size() - 2);
          Polynomial x3 = pnm1.times(Polynomial.of(Tensors.vector(0, 2))).minus(pnm2);
          MEMO.add(x3);
        }
      }
    return MEMO.get(index);
  }
}
