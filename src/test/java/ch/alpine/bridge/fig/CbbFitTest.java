package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

class CbbFitTest {
  @Test
  void testRatio1() {
    Tensor a = Tensors.fromString("{3[m],4[m]}");
    Tensor b = Tensors.vector(400, 400);
    Tensor r1 = CbbFit.inside(a, b).orElseThrow();
    assertEquals(r1, Tensors.vector(300, 400));
    Tensor r2 = CbbFit.outside(a, b).orElseThrow();
    // System.out.println(r2);
    // System.out.println(r2.map(N.DOUBLE));
    assertEquals(r2, Tensors.fromString("{400, 1600/3}"));
  }

  @Test
  void testRatio2() {
    Tensor a = Tensors.fromString("{3[m],4[m]}");
    Tensor b = Tensors.vector(200, 400);
    Tensor r1 = CbbFit.inside(a, b).orElseThrow();
    assertEquals(r1, Tensors.of(RealScalar.of(200), RationalScalar.of(800, 3)));
    Tensor r2 = CbbFit.outside(a, b).orElseThrow();
    assertEquals(r2, Tensors.fromString("{300, 400}"));
  }

  @Test
  void testRatio3() {
    Tensor a = Tensors.fromString("{3[m],5[m],4[m]}");
    Tensor b = Tensors.vector(500, 500, 500);
    Tensor r1 = CbbFit.inside(a, b).orElseThrow();
    assertEquals(r1, Tensors.vector(300, 500, 400));
    Tensor r2 = CbbFit.outside(a, b).orElseThrow();
    assertEquals(r2, Tensors.fromString("{500, 2500/3, 2000/3}"));
  }
}
