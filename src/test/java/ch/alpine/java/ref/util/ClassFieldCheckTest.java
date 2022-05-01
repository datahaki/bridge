// code by jph
package ch.alpine.java.ref.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.java.lang.ClassDiscovery;
import ch.alpine.java.lang.ClassPaths;
import ch.alpine.java.ref.ann.FieldClipT;

class ClassFieldCheckTest {
  @Test
  public void testSimple() {
    ClassFieldCheck classFieldCheck = new ClassFieldCheck();
    ClassDiscovery.execute(ClassPaths.getDefault(), classFieldCheck);
    assertTrue(6 < classFieldCheck.getInspected().size());
    assertTrue(classFieldCheck.getFailures().contains(FieldClipT.class));
    List<FieldValueContainer> list = classFieldCheck.invalidFields();
    assertFalse(list.isEmpty());
    List<String> fields = list.stream().map(FieldValueContainer::getField).map(Field::toString).collect(Collectors.toList());
    assertTrue(fields.contains("public java.lang.String ch.alpine.java.ref.GuiTrial.optionsFail"));
    assertTrue(fields.contains("public java.lang.String ch.alpine.java.ref.GuiTrial.optionsMiss"));
    // fields.forEach(s -> System.out.println(s));
  }
}
