// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class UnicodeHelperTest {
  @Test
  void testPackageVisibility() {
    assertFalse(Modifier.isPublic(UnicodeUnit.class.getModifiers()));
  }
}
