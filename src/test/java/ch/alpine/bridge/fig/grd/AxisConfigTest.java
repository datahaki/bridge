// code by jph
package ch.alpine.bridge.fig.grd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class AxisConfigTest {
  static AxisConfig linear(int pixel_ofs, int pixel_len, Clip model_clip, boolean incr) {
    return AxisConfig.create(pixel_ofs, pixel_len, AxisMaps.LIN, model_clip, incr);
  }

  @Test
  void testPixel() {
    AxisConfig confDecr = linear(5, 10, Clips.interval(0, 100), false);
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confDecr.model2pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confDecr.model2pixel(RealScalar.of(100))));
  }

  @Test
  void testSync() {
    AxisConfig confDecr = linear(5, 10, Clips.interval(50, 140), false);
    assertEquals(confDecr.model2pixel(RealScalar.of(80)), 11.0, 1E-10);
    double pixel = confDecr.model2pixel(RealScalar.of(80));
    Scalar model = confDecr.pixel2model((int) pixel);
    assertEquals(model, RealScalar.of(80));
  }

  @Test
  void testMinMax() {
    AxisConfig confDecr = linear(5, 10, Clips.interval(20, 100), false);
    assertEquals(confDecr.model2pixel(RealScalar.of(100)), 5.0, 1E-10);
    assertEquals(confDecr.model2pixel(RealScalar.of(20)), 14.0, 1E-10);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> linear(3, 1, Clips.unit(), false));
    assertThrows(Exception.class, () -> linear(3, 2, Clips.interval(3, 3), false));
  }

  @Test
  void testPixelIncr() {
    AxisConfig confIncr = linear(5, 10, Clips.interval(0, 100), true);
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 0), RealScalar.of(confIncr.model2pixel(RealScalar.of(0))));
    Tolerance.CHOP.requireClose(RealScalar.of(5 + 9), RealScalar.of(confIncr.model2pixel(RealScalar.of(100))));
  }

  @Test
  void testSyncIncr() {
    AxisConfig confIncr = linear(5, 10, Clips.interval(50, 140), true);
    assertEquals(confIncr.model2pixel(RealScalar.of(80)), 8.0, 1E-10);
    double pixel = confIncr.model2pixel(RealScalar.of(80));
    Scalar model = confIncr.pixel2model((int) pixel);
    assertEquals(model, RealScalar.of(80));
  }

  @Test
  void testMinMaxIncr() {
    AxisConfig confIncr = linear(5, 10, Clips.interval(20, 100), true);
    assertEquals(confIncr.model2pixel(RealScalar.of(100)), 14.0, 1E-10);
    assertEquals(confIncr.model2pixel(RealScalar.of(20)), 5.0, 1E-10);
  }

  @Test
  void testFailIncr() {
    assertThrows(Exception.class, () -> linear(3, 1, Clips.unit(), true));
    assertThrows(Exception.class, () -> linear(3, 2, Clips.interval(3, 3), true));
  }
}
