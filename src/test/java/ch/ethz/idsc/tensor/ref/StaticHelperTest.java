// code by jph
package ch.ethz.idsc.tensor.ref;

import java.util.List;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testFields() {
    List<FieldWrap> list = StaticHelper.CACHE.apply(ParamContainerExt.class);
    assertEquals(list.get(0).getField().getName(), "string");
    assertEquals(list.get(6).getField().getName(), "onlyInExt");
  }
}
