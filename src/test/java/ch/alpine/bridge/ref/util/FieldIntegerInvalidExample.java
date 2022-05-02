// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.num.Pi;

@ReflectionMarker
public class FieldIntegerInvalidExample {
  @FieldInteger
  public Scalar current = Pi.VALUE;
}
