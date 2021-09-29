// code by jph
package ch.alpine.java.ref;

import java.util.Arrays;
import java.util.List;

import ch.alpine.java.ref.ann.FieldFuse;
import ch.alpine.java.ref.ann.FieldLabel;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.num.Pi;

public class SimpleLaram {
  @FieldLabel(text = "Nested %a")
  public final List<NestedParam> nestedParams = Arrays.asList( //
      new NestedParam(), //
      new NestedParam());

  public static class NestedParam extends BaseParam {
    @FieldFuse(text = "fuse")
    public Boolean some = true;
    public Scalar scalar = Pi.VALUE;
    public String text = "grolley";
  }
}
