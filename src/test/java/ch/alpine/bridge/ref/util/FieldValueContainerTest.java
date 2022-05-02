// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FieldValueContainerTest {
  @Test
  public void testSimple() {
    FieldValueContainer fvc = new FieldValueContainer("key", null, "object", "value");
    assertEquals(fvc.getKey(), "key");
    assertEquals(fvc.getObject(), "object");
    assertEquals(fvc.getValue(), "value");
    assertEquals(fvc.getFieldWrap(), null);
  }
}
