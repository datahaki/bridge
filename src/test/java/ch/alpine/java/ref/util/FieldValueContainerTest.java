// code by jph
package ch.alpine.java.ref.util;

import junit.framework.TestCase;

public class FieldValueContainerTest extends TestCase {
  public void testSimple() {
    FieldValueContainer fvc = new FieldValueContainer("key", null, "object", "value");
    assertEquals(fvc.getKey(), "key");
    assertEquals(fvc.getObject(), "object");
    assertEquals(fvc.getValue(), "value");
  }
}
