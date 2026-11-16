// code by gemini
// code by jph
package ch.alpine.bridge.geo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.Tolerance;

class SunpointTest {
  @Test
  void testVernalEquinoxDeclination() {
    // Day 81 offset results in solar = 0 (Vernal Equinox baseline).
    // In 2023 (non-leap year), March 22 corresponds to day-of-year 81.
    LocalDateTime equinoxNoon = LocalDateTime.of(2023, 3, 22, 12, 0, 0);
    Sunpoint sunpoint = Sunpoint.of(equinoxNoon);
    assertNotNull(sunpoint, "Sunpoint object should not be null");
    // At the equinox (angle = 0), Sin(0) = 0, so the axial tilt component should evaluate to zero/scalar zero.
    // (Assuming your Scalar library has a numeric evaluation or zero check, e.g., scalar.number().doubleValue() == 0.0)
    // assertEquals(0.0, sunpoint.declination().number().doubleValue(), 1e-5);
    Tolerance.CHOP.requireZero(sunpoint.lambda_s());
    Tolerance.CHOP.requireZero(sunpoint.delta());
  }
}
