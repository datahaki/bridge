// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class ExampleBadReturn {
  @FieldSelectionCallback("callback")
  public String text = "abc";

  public String callback() {
    return "not a list";
  }
}
