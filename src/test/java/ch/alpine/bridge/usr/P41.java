package ch.alpine.bridge.usr;

import java.util.HashSet;
import java.util.Set;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.sca.Clips;

public class P41 {
  final Set<Tensor> set = new HashSet<>();
  private final Tensor upper;

  public P41(int... limits) {
    upper = Tensors.vectorInt(limits);
  }

  void rec(Tensor seed) {
    for (int i = 0; i < upper.length(); ++i)
      Clips.positive(upper.Get(i)).requireInside(seed.Get(i));
    // ---
    if (set.add(seed)) {
      { // empty
        for (int i = 0; i < upper.length(); ++i) {
          Tensor copy = seed.copy();
          copy.set(Scalar::zero, i);
          rec(copy);
        }
      }
      { // fill
        for (int i = 0; i < upper.length(); ++i) {
          Tensor copy = seed.copy();
          copy.set(upper.get(i), i);
          rec(copy);
        }
      }
      { // fill i -> j
        for (int i = 0; i < upper.length(); ++i)
          for (int j = 0; j < upper.length(); ++j)
            if (i != j) {
              Scalar vacant = upper.Get(j).subtract(seed.Get(j));
              Scalar amount = seed.Get(i);
              Tensor copy = seed.copy();
              if (Scalars.lessEquals(amount, vacant)) {
                copy.set(amount::add, j);
                copy.set(amount.negate()::add, i);
                rec(copy);
              } else {
                copy.set(vacant::add, j);
                copy.set(vacant.negate()::add, i);
                rec(copy);
              }
            }
      }
    }
  }

  public static void main(String[] args) {
    P41 p41 = new P41(11, 7);
    p41.rec(Tensors.vector(0, 0));
    System.out.println(p41.set);
  }
}
