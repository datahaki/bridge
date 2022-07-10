// code by jph
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class ExampleBadMethod {
  @FieldSelectionCallback("asd")
  public String text = "abc";
}
