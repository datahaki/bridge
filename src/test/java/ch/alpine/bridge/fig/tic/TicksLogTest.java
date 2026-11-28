package ch.alpine.bridge.fig.tic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.chq.ExactScalarQ;
import ch.alpine.tensor.qty.Quantity;

class TicksLogTest {
  @Test
  void test() {
    Scalar step = TicksLog.INSTANCE.enhancedStep(Quantity.of(.00123, "m"));
    ExactScalarQ.require(step);
    assertEquals(step, Quantity.of(1, "m"));
  }

  @Test
  void test2() {
    Scalar step = TicksLog.INSTANCE.enhancedStep(Quantity.of(100.00123, "m"));
    ExactScalarQ.require(step);
    assertEquals(step, Quantity.of(1000, "m"));
  }
}
