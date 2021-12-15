// code by jph
package ch.alpine.java.ref;

import java.util.Arrays;
import java.util.List;

import ch.alpine.java.ref.ann.FieldLabel;
import ch.alpine.java.ref.ann.FieldSelectionCallback;
import ch.alpine.java.ref.ann.ReflectionMarker;

@ReflectionMarker
public class BaseParam {
  @FieldLabel(text = "\u3000EXTRA")
  public Boolean basic = true;
  @FieldSelectionCallback(method = "here")
  public String more = "asd";

  public List<String> here() {
    return Arrays.asList("asdf", more, more + "123");
  }
}
