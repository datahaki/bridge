// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

class ConfIncrTest {
  @Test
  void testPixel() {
    ConfIncr confIncr = new ConfIncr(5, 10, Clips.interval(0, 100));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confIncr.pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confIncr.pixel(RealScalar.of(100))));
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> new ConfIncr(3, 1, Clips.unit()));
    assertThrows(Exception.class, () -> new ConfIncr(3, 2, Clips.interval(3, 3)));
  }
}
