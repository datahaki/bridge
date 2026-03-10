// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.cgr.ClassDiscovery;
import ch.alpine.bridge.cgr.ClassPaths;
import ch.alpine.bridge.ref.util.ClassFieldCheck;
import ch.alpine.bridge.ref.util.FieldValueRecord;

class ReflectionMarkerTest {
  @Test
  void testReflection() {
    ClassFieldCheck classFieldCheck = new ClassFieldCheck();
    ClassDiscovery.execute(ClassPaths.getDefault(), classFieldCheck);
    {
      List<Class<?>> list = classFieldCheck.getInspected();
      assertTrue(5 < list.size()); // the exact value doesn't matter
    }
    {
      List<Class<?>> list = classFieldCheck.getFailures();
      assertTrue(1 < list.size()); // the exact value doesn't matter
    }
    {
      List<FieldValueRecord> list = classFieldCheck.invalidFields();
      for (FieldValueRecord fvc : list) {
        fvc.field().toString();
      }
    }
  }
}
