// code by jph
package ch.alpine.java.ref.util;

import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;

@ReflectionMarker
public class FieldClipInvalidExample {
  @FieldClip(min = "0[A]", max = "3[A]")
  public Scalar current = Quantity.of(4, "A");
}
