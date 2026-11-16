// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.exp.Exp;

class ConfLogDecrTest {
  @Test
  void test() {
    Scalar min = Exp.FUNCTION.apply(RealScalar.of(0));
    Scalar max = Exp.FUNCTION.apply(RealScalar.of(1));
    ConfLogDecr confLogDecr = ConfLogDecr.exp(5, 10, Clips.interval(min, max));
    assertEquals(confLogDecr.clip(), Clips.unit());
    {
      Scalar value = min;
      double pixel = confLogDecr.pixel(value);
      assertEquals(pixel, 14.0, 1e-10);
      Scalar model = confLogDecr.model((int) Math.round(pixel));
      Tolerance.CHOP.requireClose(value, model);
    }
    {
      Scalar value = max;
      double pixel = confLogDecr.pixel(value);
      assertEquals(pixel, 5.0, 1e-10);
      Scalar model = confLogDecr.model((int) Math.round(pixel));
      Tolerance.CHOP.requireClose(value, model);
    }
  }

  @Test
  void test2() {
    Scalar min = Exp.FUNCTION.apply(RealScalar.of(2));
    Scalar max = Exp.FUNCTION.apply(RealScalar.of(3));
    ConfLogDecr confLogDecr = ConfLogDecr.exp(5, 10, Clips.interval(min, max));
    assertEquals(confLogDecr.clip(), Clips.interval(2, 3));
    {
      Scalar value = min;
      double pixel = confLogDecr.pixel(value);
      assertEquals(pixel, 14.0, 1e-10);
      Scalar model = confLogDecr.model((int) Math.round(pixel));
      Tolerance.CHOP.requireClose(value, model);
    }
    {
      Scalar value = max;
      double pixel = confLogDecr.pixel(value);
      assertEquals(pixel, 5.0, 1e-10);
      Scalar model = confLogDecr.model((int) Math.round(pixel));
      Tolerance.CHOP.requireClose(value, model);
    }
  }
}
