// code by jph
package ch.alpine.java.ref.util;

import ch.alpine.java.ref.ann.FieldInteger;
import ch.alpine.java.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.num.Pi;

@ReflectionMarker
public class FieldIntegerInvalidExample {
  @FieldInteger
  public Scalar current = Pi.VALUE;
}
