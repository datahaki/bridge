package ch.alpine.bridge.fig.tic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.chq.ExactScalarQ;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.LogNormalDistribution;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Sign;

class TicksDecTest {
  @Test
  void test() {
    Tensor tensor = RandomVariate.of(LogNormalDistribution.standard(), 100);
    Tensor uppers = tensor.maps(TicksDec.DENSE::enhancedStep);
    uppers.subtract(tensor).maps(Sign::requirePositiveOrZero);
  }

  @Test
  void testSpecific() {
    Scalar step = TicksDec.DENSE.enhancedStep(Quantity.of(.123, "m"));
    ExactScalarQ.require(step);
    assertEquals(step, Scalars.fromString("1/5[m]"));
  }

  @Test
  void testSpecific2() {
    Scalar step = TicksDec.DENSE.enhancedStep(Quantity.of(1.123, "m"));
    ExactScalarQ.require(step);
    // assertEquals(step, Scalars.fromString("1/5[m]"));
    IO.println(step);
  }
}
