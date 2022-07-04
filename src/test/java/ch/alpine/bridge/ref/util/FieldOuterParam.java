// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.re.Pivots;

@ReflectionMarker
public class FieldOuterParam {
  public static class NestedParam {
    public String text = "abc";
    public Boolean status = true;
    public Pivots pivots = Pivots.FIRST_NON_ZERO;
  }

  public final NestedParam[] nestedParam = new NestedParam[] { new NestedParam(), new NestedParam() };
  public Boolean status = true;
  @FieldInteger
  @FieldClip(min = "3", max = "5")
  public Scalar integer = RealScalar.of(3);
}
