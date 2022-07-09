// code by jph
package ch.alpine.bridge.ref;

import java.util.List;

import ch.alpine.bridge.ref.ann.FieldLabel;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class BaseParam {
  @FieldLabel("\u3000EXTRA")
  public Boolean basic = true;
  @FieldSelectionCallback("here")
  public String more = "asd";

  @ReflectionMarker
  public List<String> here() {
    return List.of("asdf", more, more + "123");
  }
}
