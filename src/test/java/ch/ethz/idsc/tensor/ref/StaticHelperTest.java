// code by jph
package ch.ethz.idsc.tensor.ref;

import java.util.List;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testFields() {
    List<FieldType> list = StaticHelper.CACHE.apply(ParamContainerExt.class);
    // List<Entry<Field, FieldType>> list = new ArrayList<>(map.entrySet());
    assertEquals(list.get(0).getField().getName(), "string");
    assertEquals(list.get(6).getField().getName(), "onlyInExt");
  }
}
