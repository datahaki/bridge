// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class FieldOptionsCollectorTest {
  @Test
  void test() {
    assertFalse(Modifier.isPublic(FieldOptionsCollector.class.getModifiers()));
  }
}
