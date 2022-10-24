// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ShowTest {
  @Test
  void testFailNull() {
    assertThrows(Exception.class, () -> new Show(null));
  }
}
