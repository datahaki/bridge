// code by jph
package ch.alpine.bridge.fig.grd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

class ConfLinDecrTest {
  @Test
  void testPixel() {
    ConfLinDecr confDecr = new ConfLinDecr(5, 10, Clips.interval(0, 100));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confDecr.pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confDecr.pixel(RealScalar.of(100))));
  }

  @Test
  void testSync() {
    ConfLinDecr confDecr = new ConfLinDecr(5, 10, Clips.interval(50, 140));
    assertEquals(confDecr.pixel(RealScalar.of(80)), 11.0, 1E-10);
    double pixel = confDecr.pixel(RealScalar.of(80));
    Scalar model = confDecr.model((int) pixel);
    assertEquals(model, RealScalar.of(80));
  }

  @Test
  void testMinMax() {
    ConfLinDecr confDecr = new ConfLinDecr(5, 10, Clips.interval(20, 100));
    assertEquals(confDecr.pixel(RealScalar.of(100)), 5.0, 1E-10);
    assertEquals(confDecr.pixel(RealScalar.of(20)), 14.0, 1E-10);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> new ConfLinDecr(3, 1, Clips.unit()));
    assertThrows(Exception.class, () -> new ConfLinDecr(3, 2, Clips.interval(3, 3)));
  }
}
