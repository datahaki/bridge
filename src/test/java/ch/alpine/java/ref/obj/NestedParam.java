// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.num.Pi;

public class NestedParam {
  public Boolean some = true;
  public String text = "grolley";
  public Tensor[] tensors = { Tensors.empty(), Pi.VALUE };
}
