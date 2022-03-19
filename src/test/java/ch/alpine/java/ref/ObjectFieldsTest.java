// code by jph
package ch.alpine.java.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ObjectFieldsTest {
  @Test
  public void testFields() {
    List<Field> list = ObjectFields.list(SimpleParam.class);
    List<String> collect = list.stream().map(f -> f.getName()).collect(Collectors.toList());
    // System.out.println(collect);
    assertEquals(collect.get(0), "basic");
    assertEquals(collect.get(2), "lookAndFeels");
    assertEquals(collect.get(3), "ignore_int");
  }

  @Test
  public void testSimple() {
    assertThrows(Exception.class, () -> ObjectFields.of(null, null));
  }
}
