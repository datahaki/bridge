// code by jph
package ch.alpine.java.ref;

import java.util.List;
import java.util.Properties;

import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class ClipParamTest extends TestCase {
  public void testSimple() {
    ClipParam clipParam = new ClipParam();
    List<String> list = ObjectProperties.list(clipParam);
    assertTrue(list.contains("clipReal={2, 3}"));
    assertTrue(list.contains("clipMeter={-1[m], 1[m]}"));
  }

  public void testModify() {
    ClipParam clipParam = new ClipParam();
    clipParam.clipReal = Clips.interval(10, 11);
    List<String> list = ObjectProperties.list(clipParam);
    assertTrue(list.contains("clipReal={10, 11}"));
    assertTrue(list.contains("clipMeter={-1[m], 1[m]}"));
  }

  public void testRecreate() {
    ClipParam clipParam = new ClipParam();
    clipParam.clipReal = Clips.interval(20, 21);
    Properties properties = ObjectProperties.properties(clipParam);
    properties.put("clipMeter", "{9[m], 10[m]}");
    ClipParam clipParam2 = ObjectProperties.set(new ClipParam(), properties);
    assertEquals(clipParam2.clipReal, Clips.interval(20, 21));
    assertEquals(clipParam2.clipMeter, Clips.interval(Quantity.of(9, "m"), Quantity.of(10, "m")));
  }
}