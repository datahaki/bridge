// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SpecialCharsTest {
  @ParameterizedTest
  @EnumSource
  void test(SpecialChars specialChars) {
    String string = specialChars.string();
    assertTrue(0 < string.length());
    IO.println(string + " " + string.length());
    assertTrue(string.length() <= 3);
    assertEquals(string, string.trim());
  }
}
