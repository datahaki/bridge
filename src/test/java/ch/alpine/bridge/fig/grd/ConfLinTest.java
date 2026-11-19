// code by jph
package ch.alpine.bridge.fig.grd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

class ConfLinTest {
  @Test
  void testPixel() {
    ConfBase confDecr = ConfLin.create(5, 10, Clips.interval(0, 100), AxisMaps.LIN, false);
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confDecr.pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confDecr.pixel(RealScalar.of(100))));
  }

  @Test
  void testSync() {
    ConfBase confDecr = ConfLin.create(5, 10, Clips.interval(50, 140), AxisMaps.LIN, false);
    assertEquals(confDecr.pixel(RealScalar.of(80)), 11.0, 1E-10);
    double pixel = confDecr.pixel(RealScalar.of(80));
    Scalar model = confDecr.model((int) pixel);
    assertEquals(model, RealScalar.of(80));
  }

  @Test
  void testMinMax() {
    ConfBase confDecr = ConfLin.create(5, 10, Clips.interval(20, 100), AxisMaps.LIN, false);
    assertEquals(confDecr.pixel(RealScalar.of(100)), 5.0, 1E-10);
    assertEquals(confDecr.pixel(RealScalar.of(20)), 14.0, 1E-10);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> ConfLin.create(3, 1, Clips.unit(), AxisMaps.LIN, false));
    assertThrows(Exception.class, () -> ConfLin.create(3, 2, Clips.interval(3, 3), AxisMaps.LIN, false));
  }

  @Test
  void testPixelIncr() {
    ConfBase confIncr = ConfLin.create(5, 10, Clips.interval(0, 100), AxisMaps.LIN, true);
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confIncr.pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confIncr.pixel(RealScalar.of(100))));
  }

  @Test
  void testSyncIncr() {
    ConfBase confIncr = ConfLin.create(5, 10, Clips.interval(50, 140), AxisMaps.LIN, true);
    assertEquals(confIncr.pixel(RealScalar.of(80)), 8.0, 1E-10);
    double pixel = confIncr.pixel(RealScalar.of(80));
    Scalar model = confIncr.model((int) pixel);
    assertEquals(model, RealScalar.of(80));
  }

  @Test
  void testMinMaxIncr() {
    ConfBase confIncr = ConfLin.create(5, 10, Clips.interval(20, 100), AxisMaps.LIN, true);
    assertEquals(confIncr.pixel(RealScalar.of(100)), 14.0, 1E-10);
    assertEquals(confIncr.pixel(RealScalar.of(20)), 5.0, 1E-10);
  }

  @Test
  void testFailIncr() {
    assertThrows(Exception.class, () -> ConfLin.create(3, 1, Clips.unit(), AxisMaps.LIN, true));
    assertThrows(Exception.class, () -> ConfLin.create(3, 2, Clips.interval(3, 3), AxisMaps.LIN, true));
  }
}
