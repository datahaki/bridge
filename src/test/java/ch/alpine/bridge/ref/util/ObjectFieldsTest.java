// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.SimpleParam;

class ObjectFieldsTest {
  @Test
  void testFields() {
    List<Field> list = ObjectFields.list(SimpleParam.class);
    List<String> collect = list.stream().map(f -> f.getName()).collect(Collectors.toList());
    // System.out.println(collect);
    assertEquals(collect.get(0), "basic");
    assertEquals(collect.get(2), "lookAndFeels");
    assertEquals(collect.get(3), "ignore_int");
  }

  @Test
  void testSimple() {
    assertThrows(Exception.class, () -> ObjectFields.of(null, null));
  }

  @Test
  void testDeepEquals() {
    SimpleParam sp1 = new SimpleParam();
    SimpleParam sp2 = new SimpleParam();
    assertTrue(ObjectFields.deepEquals(sp1, sp2));
    sp1.basic ^= true;
    assertFalse(ObjectFields.deepEquals(sp1, sp2));
    assertFalse(ObjectFields.deepEquals(sp1, false));
    assertFalse(ObjectFields.deepEquals(false, sp2));
  }

  @Test
  void testFallthrough() {
    switch (0) {
    case 0 -> {
      // ---
    }
    case 1 -> {
      fail();
    }
    default -> {
      // ---
    }
    }
  }
}
