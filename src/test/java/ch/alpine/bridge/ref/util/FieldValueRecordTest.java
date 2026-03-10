// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FieldValueRecordTest {
  @Test
  void testSimple() {
    FieldValueRecord fvc = new FieldValueRecord("key", null, "object", "value");
    assertEquals(fvc.key(), "key");
    assertEquals(fvc.object(), "object");
    assertEquals(fvc.value(), "value");
    assertEquals(fvc.fieldWrap(), null);
  }
}
