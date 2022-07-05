// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class VisibilityPredicateTest {
  @Test
  void test() {
    assertThrows(Exception.class, () -> VisibilityPredicate.of(3, 1));
    assertThrows(Exception.class, () -> VisibilityPredicate.of(1, 3));
  }
}
