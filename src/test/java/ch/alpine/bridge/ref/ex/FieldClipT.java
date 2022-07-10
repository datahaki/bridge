// code by jph
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;

@ReflectionMarker
public class FieldClipT {
  @FieldClip(min = "0[A]", max = "3[W]")
  public Scalar current = Quantity.of(4, "A");
}
