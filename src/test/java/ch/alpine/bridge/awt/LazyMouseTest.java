// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LazyMouseTest {
  @Test
  void test() {
    assertThrows(Exception.class, () -> new LazyMouse(null));
  }
}
