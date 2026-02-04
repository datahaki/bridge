// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class ExampleBadFuse {
  @FieldFuse
  public String text = "abc";
}
