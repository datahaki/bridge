// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.ex.NameString;

class FieldOptionsCollectorTest {
  @ReflectionMarker
  public static class Param {
    public NameString nameString;
  }

  @Test
  void testSimple() {
    Param param = new Param();
    List<NameString> list = new LinkedList<>();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(param, () -> {
      list.add(param.nameString);
    });
    fieldsAssignment.forEach();
    assertEquals(list.size(), 3);
    assertEquals(list.stream().distinct().count(), 3);
  }

  @Test
  void test() {
    assertFalse(Modifier.isPublic(FieldOptionsCollector.class.getModifiers()));
  }
}
