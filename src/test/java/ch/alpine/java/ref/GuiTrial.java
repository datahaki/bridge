// code by jph
package ch.alpine.java.ref;

import java.util.Arrays;
import java.util.List;

import ch.alpine.java.ref.ann.FieldSelectionArray;
import ch.alpine.java.ref.ann.FieldSelectionCallback;
import ch.alpine.java.ref.ann.ReflectionMarker;

@ReflectionMarker
public class GuiTrial {
  public String string = "abc";
  @FieldSelectionCallback(method = "getStrings")
  public String options = "options";
  @FieldSelectionCallback(method = "getStringsThrows")
  public String optionsFail = "options fail";
  @FieldSelectionCallback(method = "doesnotexist")
  public String optionsMiss = "options miss";
  @FieldSelectionArray(values = { "/dev/ttyS0", "/dev/ttyS1", "/dev/ttyS2", "/dev/ttyS3", "/dev/ttyUSB0", "/dev/ttyUSB1" })
  public String selectable = "/dev/ttyS0";

  public List<String> getStrings() {
    return Arrays.asList("a", "b", string, selectable);
  }

  public List<String> getStringsThrows() {
    throw new RuntimeException();
  }
}
