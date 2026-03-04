// code by jph
package ch.alpine.bridge.gfx;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;

class IntervalClockTest {
  @Test
  void testHertz() {
    IntervalClock intervalClock = new IntervalClock();
    Scalar hertz = intervalClock.hertz();
    assertTrue(Scalars.lessThan(Quantity.of(100, "Hz"), hertz));
  }

  @Test
  void testSeconds() {
    IntervalClock intervalClock = new IntervalClock();
    Scalar seconds = intervalClock.seconds();
    assertTrue(Scalars.lessThan(seconds, Quantity.of(0.01, "s")));
  }
}
