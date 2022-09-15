// code by jph
package demo.tensor.sca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensors;

class ChebyshevTest {
  @Test
  void test() {
    assertEquals(Chebyshev.of(3).coeffs(), Tensors.vector(0, -3, 0, 4));
    assertEquals(Chebyshev.of(6).coeffs(), Tensors.vector(-1, 0, 18, 0, -48, 0, 32));
  }
}
