// code by jph
package ch.alpine.bridge.fig.grd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

class ConfLinIncrTest {
  @Test
  void testPixel() {
    ConfLinIncr confIncr = new ConfLinIncr(5, 10, Clips.interval(0, 100));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confIncr.pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confIncr.pixel(RealScalar.of(100))));
  }

  @Test
  void testSync() {
    ConfLinIncr confIncr = new ConfLinIncr(5, 10, Clips.interval(50, 140));
    assertEquals(confIncr.pixel(RealScalar.of(80)), 8.0, 1E-10);
    double pixel = confIncr.pixel(RealScalar.of(80));
    Scalar model = confIncr.model((int) pixel);
    assertEquals(model, RealScalar.of(80));
  }

  @Test
  void testMinMax() {
    ConfLinIncr confIncr = new ConfLinIncr(5, 10, Clips.interval(20, 100));
    assertEquals(confIncr.pixel(RealScalar.of(100)), 14.0, 1E-10);
    assertEquals(confIncr.pixel(RealScalar.of(20)), 5.0, 1E-10);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> new ConfLinIncr(3, 1, Clips.unit()));
    assertThrows(Exception.class, () -> new ConfLinIncr(3, 2, Clips.interval(3, 3)));
  }
}
