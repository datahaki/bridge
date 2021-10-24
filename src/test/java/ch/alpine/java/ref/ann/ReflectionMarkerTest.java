package ch.alpine.java.ref.ann;

import java.util.List;

import ch.alpine.java.lang.ClassDiscovery;
import ch.alpine.java.lang.ClassPaths;
import ch.alpine.java.ref.util.ClassFieldCheck;
import ch.alpine.java.ref.util.FieldValueContainer;
import ch.alpine.tensor.ext.Timing;
import junit.framework.TestCase;

public class ReflectionMarkerTest extends TestCase {
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
        fvc.getField().toString();
      }
    }
  }
}
