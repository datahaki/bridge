// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SpecialCharsTest {
  @ParameterizedTest
  @EnumSource
  void test(SpecialChars specialChars) {
    String string = specialChars.string();
    assertTrue(0 < string.length());
    assertEquals(GraphemeCount.of(string), 1);
    assertEquals(string, string.trim());
  }

  @Test
  void testUnique() {
    Set<String> set = new HashSet<String>();
    for (SpecialChars specialChars : SpecialChars.values()) {
      boolean added = set.add(specialChars.string());
      if (!added)
        System.err.println("duplicate: " + specialChars);
    }
    long count = Arrays.stream(SpecialChars.values()).map(SpecialChars::string).distinct().count();
    assertEquals(count, SpecialChars.values().length);
  }
}
