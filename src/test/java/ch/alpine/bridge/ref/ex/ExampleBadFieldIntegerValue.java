// code by jph
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.ann.FieldClipInteger;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.num.Pi;

@ReflectionMarker
public class ExampleBadFieldIntegerValue {
  @FieldClipInteger
  public Scalar current = Pi.VALUE;
}
