// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UnitHubTest {
  @Test
  void test() {
    assertThrows(Exception.class, () -> new UnitHub(null));
  }
}
