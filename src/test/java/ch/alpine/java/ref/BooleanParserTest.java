// code by jph
package ch.alpine.java.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ch.alpine.java.util.AssertFail;

public class BooleanParserTest {
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
    AssertFail.of(() -> BooleanParser.orNull(null));
  }
}
