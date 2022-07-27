// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.lang.ClassDiscovery;
import ch.alpine.bridge.lang.ClassPaths;
import ch.alpine.bridge.ref.ex.FieldClipT;

class ClassFieldCheckTest {
  @Test
  void testSimple() {
    ClassFieldCheck classFieldCheck = new ClassFieldCheck();
    ClassDiscovery.execute(ClassPaths.getDefault(), classFieldCheck);
    // System.out.println(classFieldCheck.getInspected().size());
    assertTrue(36 <= classFieldCheck.getInspected().size());
    assertTrue(classFieldCheck.getFailures().contains(FieldClipT.class));
    List<FieldValueContainer> list = classFieldCheck.invalidFields();
    assertFalse(list.isEmpty());
    List<String> fields = list.stream().map(FieldValueContainer::field).map(Field::toString).collect(Collectors.toList());
    assertTrue(fields.contains("public java.lang.String ch.alpine.bridge.ref.ex.GuiTrial.optionsFail"));
    assertTrue(fields.contains("public java.lang.String ch.alpine.bridge.ref.ex.GuiTrial.optionsMiss"));
  }
}
