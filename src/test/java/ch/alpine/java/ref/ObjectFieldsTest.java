// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import ch.alpine.java.util.AssertFail;
import junit.framework.TestCase;

public class ObjectFieldsTest extends TestCase {
  public void testFields() {
    List<Field> list = ObjectFields.list(SimpleParam.class);
    List<String> collect = list.stream().map(f -> f.getName()).collect(Collectors.toList());
    // System.out.println(collect);
    assertEquals(collect.get(0), "basic");
    assertEquals(collect.get(1), "lookAndFeels");
    assertEquals(collect.get(2), "ignore_int");
  }

  public void testSimple() {
    AssertFail.of(() -> ObjectFields.of(null, null));
  }
}
