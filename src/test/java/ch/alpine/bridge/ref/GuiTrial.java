// code by jph
package ch.alpine.bridge.ref;

import java.util.Arrays;
import java.util.List;

import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class GuiTrial {
  public String string = "abc";
  @FieldSelectionCallback("getStrings")
  public String options = "options";
  @FieldSelectionCallback("getStringsThrows")
  public String optionsFail = "options fail";
  @FieldSelectionCallback("doesnotexist")
  public String optionsMiss = "options miss";
  @FieldSelectionArray({ "/dev/ttyS0", "/dev/ttyS1", "/dev/ttyS2", "/dev/ttyS3", "/dev/ttyUSB0", "/dev/ttyUSB1" })
  public String selectable = "/dev/ttyS0";

  @ReflectionMarker
  public List<String> getStrings() {
    return Arrays.asList("a", "b", string, selectable);
  }

  @ReflectionMarker
  public List<String> getStringsThrows() {
    throw new RuntimeException();
  }
}
