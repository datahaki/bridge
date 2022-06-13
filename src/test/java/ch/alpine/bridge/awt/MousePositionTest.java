// code by jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class MousePositionTest {
  @Test
  void testSimple() {
    Optional<Point> optional = MousePosition.get();
    assertTrue(optional.isPresent());
  }
}
