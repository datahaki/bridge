// code by jph
package ch.alpine.java.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.java.util.AssertFail;

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
    AssertFail.of(() -> ObjectFields.of(null, null));
  }
}
