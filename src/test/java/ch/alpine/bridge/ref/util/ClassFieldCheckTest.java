// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.cgr.ClassDiscovery;
import ch.alpine.bridge.cgr.ClassPaths;
import ch.alpine.bridge.ref.data.FieldClipT;

class ClassFieldCheckTest {
  @Test
  void testSimple() {
    ClassFieldCheck classFieldCheck = new ClassFieldCheck();
    ClassDiscovery.execute(ClassPaths.getDefault(), classFieldCheck);
    assertTrue(37 <= classFieldCheck.getInspected().size());
    assertTrue(classFieldCheck.getFailures().contains(FieldClipT.class));
    List<FieldValueContainer> list = classFieldCheck.invalidFields();
    Set<String> set = list.stream() //
        .map(FieldValueContainer::object) //
        .map(Object::getClass) //
        .map(Class::getSimpleName) //
        .distinct().collect(Collectors.toSet());
    assertTrue(set.contains(ExampleBadScalar.class.getSimpleName()));
    assertTrue(set.contains(ExampleBadFile.class.getSimpleName()));
    assertTrue(set.contains(ExampleBadReturn.class.getSimpleName()));
    assertTrue(set.contains(ExampleBadFuse.class.getSimpleName()));
    assertTrue(set.contains(ExampleBadMethod.class.getSimpleName()));
    assertTrue(set.contains(ExampleBadFieldClip.class.getSimpleName()));
    assertTrue(set.contains(ExampleBadDirectory.class.getSimpleName()));
    // List<String> fields = list.stream().map(FieldValueContainer::field).map(Field::toString).collect(Collectors.toList());
    // assertTrue(fields.contains("public java.lang.String ch.alpine.bridge.ref.ex.GuiTrial.optionsFail"));
    // assertTrue(fields.contains("public java.lang.String ch.alpine.bridge.ref.ex.GuiTrial.optionsMiss"));
  }
}
