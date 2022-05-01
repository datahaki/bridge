// code by jph
package ch.alpine.java.ref.ann;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class FieldLabelsT {
  @FieldLabel("nested %a")
  public final Nested[] nested1 = { new Nested(), new Nested() };
  @FieldLabel("nested %d")
  public final Nested[] nested2 = { new Nested(), new Nested() };

  public static class Nested {
    public Scalar value = RealScalar.ONE;
  }
}
