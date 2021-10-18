// code by jph
package ch.alpine.java.ref.util;

import java.util.List;

import ch.alpine.java.lang.ClassDiscovery;
import ch.alpine.java.lang.ClassPaths;
import junit.framework.TestCase;

public class ClassFieldCheckTest extends TestCase {
  public void testSimple() {
    ClassFieldCheck classFieldCheck = new ClassFieldCheck();
    ClassDiscovery.execute(ClassPaths.getDefault(), classFieldCheck);
    assertTrue(6 < classFieldCheck.getInspected().size());
    assertTrue(classFieldCheck.getFailures().contains(FieldClipCorruptTest.class));
    List<FieldValueContainer> list = classFieldCheck.invalidFields();
    assertFalse(list.isEmpty());
  }
}
