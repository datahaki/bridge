// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class ExampleBadClip {
  @FieldClip(min = "3[A]", max = "4[V]")
  public Scalar text = RealScalar.of(3);
}
