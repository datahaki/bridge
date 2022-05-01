// code by jph
package ch.alpine.java.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BooleanParserTest {
  @Test
  public void testCase() {
    assertNull(BooleanParser.orNull("False"));
  }

  @Test
  public void testBooleanToString() {
    assertEquals(Boolean.TRUE.toString(), "true");
    assertEquals(Boolean.FALSE.toString(), "false");
  }

  @Test
  public void testNullFail() {
    assertThrows(Exception.class, () -> BooleanParser.orNull(null));
  }
}
