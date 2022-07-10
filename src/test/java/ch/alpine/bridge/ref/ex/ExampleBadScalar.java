// code by jph
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.io.StringScalar;

@ReflectionMarker
public class ExampleBadScalar {
  public Scalar current = StringScalar.of("text");
}
