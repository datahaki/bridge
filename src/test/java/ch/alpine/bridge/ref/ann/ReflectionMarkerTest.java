// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.lang.ClassDiscovery;
import ch.alpine.bridge.lang.ClassPaths;
import ch.alpine.bridge.ref.util.ClassFieldCheck;
import ch.alpine.bridge.ref.util.FieldValueContainer;
import ch.alpine.tensor.ext.Timing;

class ReflectionMarkerTest {
  @Test
  public void testReflection() {
    Timing timing = Timing.started();
    ClassFieldCheck classFieldCheck = new ClassFieldCheck();
    ClassDiscovery.execute(ClassPaths.getDefault(), classFieldCheck);
    timing.stop();
    {
      List<Class<?>> list = classFieldCheck.getInspected();
      // for (Class<?> cls : list)
      // System.out.println(cls);
      assertTrue(5 < list.size()); // the exact value doesn't matter
    }
    {
      List<Class<?>> list = classFieldCheck.getFailures();
      assertTrue(1 < list.size()); // the exact value doesn't matter
      for (Class<?> cls : list)
        System.err.println(cls);
    }
    {
      List<FieldValueContainer> list = classFieldCheck.invalidFields();
      for (FieldValueContainer fvc : list) {
        fvc.field().toString();
      }
    }
  }
}
