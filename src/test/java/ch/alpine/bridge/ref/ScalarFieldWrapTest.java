// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class ScalarFieldWrapTest {
  @Test
  void testPackageVisibility() {
    assertFalse(Modifier.isPublic(ScalarFieldWrap.class.getModifiers()));
  }
}
