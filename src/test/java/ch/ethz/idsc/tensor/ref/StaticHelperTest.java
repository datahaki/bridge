// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testFields() {
    Map<Field, FieldType> map = StaticHelper.CACHE.apply(ParamContainerExt.class);
    List<Entry<Field, FieldType>> list = new ArrayList<>(map.entrySet());
    assertEquals(list.get(0).getKey().getName(), "string");
    assertEquals(list.get(6).getKey().getName(), "onlyInExt");
  }
}
